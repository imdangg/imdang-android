package info.imdang.imdang.ui.insight

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.TerminalSeparatorType
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.usecase.auth.GetMemberIdUseCase
import info.imdang.domain.usecase.coupon.GetCouponUseCase
import info.imdang.domain.usecase.exchange.AcceptExchangeUseCase
import info.imdang.domain.usecase.exchange.RejectExchangeUseCase
import info.imdang.domain.usecase.exchange.RequestExchangeParams
import info.imdang.domain.usecase.exchange.RequestExchangeUseCase
import info.imdang.domain.usecase.exchange.ResponseExchangeParams
import info.imdang.domain.usecase.insight.GetInsightDetailUseCase
import info.imdang.domain.usecase.insight.RecommendInsightParams
import info.imdang.domain.usecase.insight.RecommendInsightUseCase
import info.imdang.domain.usecase.insight.ReportInsightParams
import info.imdang.domain.usecase.insight.ReportInsightUseCase
import info.imdang.domain.usecase.myinsight.GetMyInsightsUseCase
import info.imdang.domain.usecase.myinsight.GetMyInsightsWithPagingUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.coupon.CouponVo
import info.imdang.imdang.model.coupon.mapper
import info.imdang.imdang.model.insight.ExchangeItem
import info.imdang.imdang.model.insight.InsightDetailItem
import info.imdang.imdang.model.insight.InsightDetailStatus
import info.imdang.imdang.model.insight.InsightDetailVo
import info.imdang.imdang.model.insight.mapper
import info.imdang.imdang.ui.insight.InsightDetailActivity.Companion.INSIGHT_ID
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getMemberIdUseCase: GetMemberIdUseCase,
    private val getInsightDetailUseCase: GetInsightDetailUseCase,
    private val getMyInsightsUseCase: GetMyInsightsUseCase,
    private val getMyInsightsWithPagingUseCase: GetMyInsightsWithPagingUseCase,
    private val getCouponUseCase: GetCouponUseCase,
    private val requestExchangeUseCase: RequestExchangeUseCase,
    private val acceptExchangeUseCase: AcceptExchangeUseCase,
    private val rejectExchangeUseCase: RejectExchangeUseCase,
    private val recommendInsightUseCase: RecommendInsightUseCase,
    private val reportInsightUseCase: ReportInsightUseCase
) : BaseViewModel() {

    val insightId = savedStateHandle[INSIGHT_ID] ?: ""

    private val memberId = getMemberIdUseCase()

    private val _event = MutableSharedFlow<InsightDetailEvent>()
    val event = _event.asSharedFlow()

    private val _insight = MutableStateFlow(InsightDetailVo.init())
    val insight = _insight.asStateFlow()

    private val _insightDetails = MutableStateFlow<List<InsightDetailItem>>(emptyList())
    val insightDetails = _insightDetails.asStateFlow()

    private val _exchangeCoupon = MutableStateFlow(CouponVo.init())
    private val exchangeCoupon = _exchangeCoupon.asStateFlow()

    private val _myInsightsCount = MutableStateFlow(0)
    private val myInsightsCount = _myInsightsCount.asStateFlow()

    private val _exchangeItems = MutableStateFlow<PagingData<ExchangeItem>>(PagingData.empty())
    val exchangeItems = _exchangeItems.asStateFlow()

    private val _selectedExchangeItem = MutableStateFlow<ExchangeItem?>(null)
    private val selectedExchangeItem = _selectedExchangeItem.asStateFlow()

    private val _isScrolling = MutableStateFlow(false)
    val isScrolling = _isScrolling.asStateFlow()

    init {
        fetchInsightDetail()
        fetchExchangeItems()
    }

    private fun fetchInsightDetail() {
        viewModelScope.launch {
            _insight.value = getInsightDetailUseCase(insightId)?.mapper(memberId) ?: return@launch
            _insightDetails.value =
                if (insight.value.insightDetailStatus == InsightDetailStatus.EXCHANGE_COMPLETE ||
                    insight.value.insightDetailStatus == InsightDetailStatus.MY_INSIGHT
                ) {
                    val infra = insight.value.infra
                    val complexEnvironment = insight.value.complexEnvironment
                    val complexFacility = insight.value.complexFacility
                    val goodNews = insight.value.goodNews
                    if (infra != null &&
                        complexEnvironment != null &&
                        complexFacility != null &&
                        goodNews != null
                    ) {
                        listOf(
                            insight.value.toBasicInfo(),
                            InsightDetailItem.Infra(infra),
                            InsightDetailItem.AptEnvironment(complexEnvironment),
                            InsightDetailItem.AptFacility(complexFacility),
                            InsightDetailItem.GoodNews(goodNews, insight.value.insightDetailStatus)
                        )
                    } else {
                        listOf(
                            insight.value.toBasicInfo(),
                            InsightDetailItem.Invisible(insight.value.insightDetailStatus)
                        )
                    }
                } else {
                    listOf(
                        insight.value.toBasicInfo(),
                        InsightDetailItem.Invisible(insight.value.insightDetailStatus)
                    )
                }
        }
    }

    private fun fetchExchangeItems() {
        viewModelScope.launch {
            val couponDeferred = async { getCouponUseCase(Unit) }
            val myInsightsDeferred = async { getMyInsightsUseCase(PagingParams()) }
            _exchangeCoupon.value = couponDeferred.await()?.mapper() ?: CouponVo.init()
            _myInsightsCount.value = myInsightsDeferred.await()?.totalElements ?: 0
        }
    }

    fun fetchExchangeItemsWithPaging() {
        viewModelScope.launch {
            getMyInsightsWithPagingUseCase(PagingParams(size = 10))
                ?.map { pagingData ->
                    val exchangeItem: PagingData<ExchangeItem> = pagingData.map {
                        ExchangeItem.Insight(it.mapper())
                    }
                    exchangeItem
                }
                ?.cachedIn(this)
                ?.collect {
                    _exchangeItems.value = if (exchangeCoupon.value.couponCount > 0) {
                        it.insertHeaderItem(
                            TerminalSeparatorType.FULLY_COMPLETE,
                            ExchangeItem.Pass(exchangeCoupon.value.couponCount)
                        )
                    } else {
                        it
                    }
                }
        }
    }

    fun isEnableTabMove() =
        insight.value.insightDetailStatus == InsightDetailStatus.EXCHANGE_COMPLETE ||
            insight.value.insightDetailStatus == InsightDetailStatus.MY_INSIGHT

    fun onClickTab() {
        _isScrolling.value = true
        viewModelScope.launch {
            delay(500)
            _isScrolling.value = false
        }
    }

    fun onClickRecommend() {
        if (insight.value.isRecommended) return

        if (insight.value.insightDetailStatus == InsightDetailStatus.EXCHANGE_COMPLETE) {
            viewModelScope.launch {
                recommendInsightUseCase(
                    RecommendInsightParams(
                        insightId = insight.value.insightId,
                        memberId = memberId
                    )
                ) ?: return@launch
                _insight.value = insight.value.copy(
                    isRecommended = !insight.value.isRecommended,
                    recommendedCount = insight.value.recommendedCount + 1
                )
            }
        } else {
            viewModelScope.launch {
                _event.emit(
                    InsightDetailEvent.ShowCommonDialog(InsightDetailDialogType.RECOMMEND_INFO)
                )
            }
        }
    }

    fun onClickExchangeRequestButton() {
        viewModelScope.launch {
            if (exchangeCoupon.value.couponCount > 0 || myInsightsCount.value > 0) {
                _selectedExchangeItem.value = null
                _exchangeItems.value = PagingData.empty()
                _event.emit(InsightDetailEvent.ShowMyInsightsBottomSheet)
            } else {
                _event.emit(
                    InsightDetailEvent.ShowCommonDialog(InsightDetailDialogType.EXCHANGE_INFO)
                )
            }
        }
    }

    fun onClickRejectButton() {
        viewModelScope.launch {
            rejectExchangeUseCase(
                ResponseExchangeParams(
                    exchangeRequestId = insight.value.exchangeRequestId ?: return@launch,
                    memberId = memberId
                )
            )?.let {
                _insight.value = insight.value.copy(
                    insightDetailStatus = InsightDetailStatus.EXCHANGE_REQUEST
                )
                _event.emit(
                    InsightDetailEvent.ShowCommonDialog(InsightDetailDialogType.EXCHANGE_REJECT)
                )
            }
        }
    }

    fun onClickAcceptButton() {
        viewModelScope.launch {
            acceptExchangeUseCase(
                ResponseExchangeParams(
                    exchangeRequestId = insight.value.exchangeRequestId ?: return@launch,
                    memberId = memberId
                )
            )?.let {
                fetchInsightDetail()
                _event.emit(
                    InsightDetailEvent.ShowCommonDialog(
                        dialogType = InsightDetailDialogType.EXCHANGE_ACCEPT,
                        onClickSubButton = {
                            viewModelScope.launch {
                                _event.emit(InsightDetailEvent.MoveStorage)
                            }
                        }
                    )
                )
            }
        }
    }

    fun requestExchange() {
        viewModelScope.launch {
            val selectedExchangeItem = selectedExchangeItem.value
            requestExchangeUseCase(
                RequestExchangeParams(
                    insightId = insight.value.insightId,
                    memberId = memberId,
                    myInsightId = if (selectedExchangeItem is ExchangeItem.Insight) {
                        selectedExchangeItem.insightVo.insightId
                    } else {
                        null
                    },
                    couponId = if (selectedExchangeItem is ExchangeItem.Pass) {
                        exchangeCoupon.value.couponId
                    } else {
                        null
                    }
                )
            )?.let {
                _insight.value = insight.value.copy(
                    insightDetailStatus = InsightDetailStatus.EXCHANGE_WAITING
                )
                _insightDetails.value = insightDetails.value.map {
                    if (it is InsightDetailItem.Invisible) {
                        it.copy(insight.value.insightDetailStatus)
                    } else {
                        it
                    }
                }

                _event.emit(
                    InsightDetailEvent.ShowCommonDialog(
                        dialogType = InsightDetailDialogType.EXCHANGE_REQUEST,
                        onClickSubButton = {
                            viewModelScope.launch {
                                _event.emit(InsightDetailEvent.MoveHomeExchange)
                            }
                        }
                    )
                )
            }
        }
    }

    fun onClickExchangeItem(exchangeItem: ExchangeItem) {
        _selectedExchangeItem.value = exchangeItem
        _exchangeItems.value = if (exchangeCoupon.value.couponCount > 0) {
            exchangeItems.value.insertHeaderItem(
                TerminalSeparatorType.FULLY_COMPLETE,
                ExchangeItem.Pass(exchangeCoupon.value.couponCount)
            )
        } else {
            exchangeItems.value
        }.map {
            when (exchangeItem) {
                is ExchangeItem.Pass -> {
                    when (it) {
                        is ExchangeItem.Pass -> it.copy(isSelected = true)
                        is ExchangeItem.Insight -> it.copy(isSelected = false)
                    }
                }

                is ExchangeItem.Insight -> {
                    when (it) {
                        is ExchangeItem.Pass -> it.copy(isSelected = false)
                        is ExchangeItem.Insight -> {
                            it.copy(
                                isSelected = exchangeItem.insightVo.insightId
                                    == it.insightVo.insightId
                            )
                        }
                    }
                }
            }
        }
    }

    fun reportInsight() {
        viewModelScope.launch {
            reportInsightUseCase(
                ReportInsightParams(
                    insightId = insight.value.insightId,
                    memberId = memberId
                )
            ) ?: return@launch
            _insight.value = insight.value.copy(
                isReported = true,
                accusedCount = insight.value.accusedCount + 1
            )
        }
    }
}

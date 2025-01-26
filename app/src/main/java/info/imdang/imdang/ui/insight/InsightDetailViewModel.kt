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
import info.imdang.domain.usecase.coupon.GetCouponCountUseCase
import info.imdang.domain.usecase.exchange.RequestExchangeParams
import info.imdang.domain.usecase.exchange.RequestExchangeUseCase
import info.imdang.domain.usecase.insight.GetInsightDetailUseCase
import info.imdang.domain.usecase.myinsight.GetMyInsightsUseCase
import info.imdang.domain.usecase.myinsight.GetMyInsightsWithPagingUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.insight.ExchangeItem
import info.imdang.imdang.model.insight.InsightDetailItem
import info.imdang.imdang.model.insight.InsightDetailStatus
import info.imdang.imdang.model.insight.InsightDetailVo
import info.imdang.imdang.model.insight.mapper
import info.imdang.imdang.model.insight.toInsightDetailStatus
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
    private val getCouponCountUseCase: GetCouponCountUseCase,
    private val requestExchangeUseCase: RequestExchangeUseCase
) : BaseViewModel() {

    private val insightId = savedStateHandle.getStateFlow(INSIGHT_ID, "")

    private val memberId = getMemberIdUseCase()

    private val _event = MutableSharedFlow<InsightDetailEvent>()
    val event = _event.asSharedFlow()

    private val _insight = MutableStateFlow(InsightDetailVo.getSample())
    val insight = _insight.asStateFlow()

    private val _insightDetails = MutableStateFlow<List<InsightDetailItem>>(emptyList())
    val insightDetails = _insightDetails.asStateFlow()

    private val _couponCount = MutableStateFlow(0)
    private val couponCount = _couponCount.asStateFlow()

    private val _myInsightsCount = MutableStateFlow(0)
    private val myInsightsCount = _myInsightsCount.asStateFlow()

    private val _exchangeItems = MutableStateFlow<PagingData<ExchangeItem>>(PagingData.empty())
    val exchangeItems = _exchangeItems.asStateFlow()

    private val _selectedExchangeItem = MutableStateFlow<ExchangeItem?>(null)
    val selectedExchangeItem = _selectedExchangeItem.asStateFlow()

    private val _insightDetailStatus = MutableStateFlow(InsightDetailStatus.EXCHANGE_REQUEST)
    val insightDetailStatus = _insightDetailStatus.asStateFlow()

    private val _isScrolling = MutableStateFlow(false)
    val isScrolling = _isScrolling.asStateFlow()

    init {
        fetchInsightDetail()
        fetchExchangeItems()
    }

    private fun fetchInsightDetail() {
        viewModelScope.launch {
            _insight.value = getInsightDetailUseCase(insightId.value)?.mapper() ?: return@launch
            _insightDetailStatus.value = insight.value.exchangeRequestStatus.toInsightDetailStatus(
                isMyInsight = insight.value.memberId == memberId
            )
            _insightDetails.value =
                if (insightDetailStatus.value == InsightDetailStatus.EXCHANGE_COMPLETE ||
                    insightDetailStatus.value == InsightDetailStatus.MY_INSIGHT
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
                            InsightDetailItem.GoodNews(goodNews)
                        )
                    } else {
                        listOf(
                            insight.value.toBasicInfo(),
                            InsightDetailItem.Invisible(insightDetailStatus.value)
                        )
                    }
                } else {
                    listOf(
                        insight.value.toBasicInfo(),
                        InsightDetailItem.Invisible(insightDetailStatus.value)
                    )
                }
        }
    }

    private fun fetchExchangeItems() {
        viewModelScope.launch {
            val couponDeferred = async { getCouponCountUseCase(Unit) }
            val myInsightsDeferred = async { getMyInsightsUseCase(PagingParams()) }
            _couponCount.value = couponDeferred.await() ?: 0
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
                    _exchangeItems.value = if (couponCount.value > 0) {
                        it.insertHeaderItem(
                            TerminalSeparatorType.FULLY_COMPLETE,
                            ExchangeItem.Pass(couponCount.value)
                        )
                    } else {
                        it
                    }
                }
        }
    }

    fun isEnableTabMove() = insightDetailStatus.value == InsightDetailStatus.EXCHANGE_COMPLETE ||
        insightDetailStatus.value == InsightDetailStatus.MY_INSIGHT

    fun onClickTab() {
        _isScrolling.value = true
        viewModelScope.launch {
            delay(500)
            _isScrolling.value = false
        }
    }

    fun onClickRecommend() {
        if (insightDetailStatus.value == InsightDetailStatus.EXCHANGE_COMPLETE) {
            _insight.value = insight.value.copy(isRecommended = !insight.value.isRecommended)
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
            if (couponCount.value > 0 || myInsightsCount.value > 0) {
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
            _event.emit(
                InsightDetailEvent.ShowCommonDialog(InsightDetailDialogType.EXCHANGE_REJECT)
            )
        }
    }

    fun onClickAcceptButton() {
        viewModelScope.launch {
            _insightDetailStatus.value = InsightDetailStatus.EXCHANGE_COMPLETE
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

    fun requestExchange() {
        viewModelScope.launch {
            val selectedExchangeItem = selectedExchangeItem.value
            val selectedInsight = if (selectedExchangeItem is ExchangeItem.Insight) {
                selectedExchangeItem.insightVo
            } else {
                null
            }

            requestExchangeUseCase(
                RequestExchangeParams(
                    insightId = insight.value.insightId,
                    memberId = memberId,
                    myInsightId = selectedInsight?.insightId,
                    couponId = null
                )
            )?.let {
                _insightDetailStatus.value = InsightDetailStatus.EXCHANGE_WAITING
                _insightDetails.value = insightDetails.value.map {
                    if (it is InsightDetailItem.Invisible) {
                        it.copy(insightDetailStatus.value)
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
        _exchangeItems.value = if (couponCount.value > 0) {
            exchangeItems.value.insertHeaderItem(
                TerminalSeparatorType.FULLY_COMPLETE,
                ExchangeItem.Pass(couponCount.value)
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
        if (!insight.value.isReported) {
            viewModelScope.launch {
                // todo : 인사이트 신고
            }
        }
    }
}

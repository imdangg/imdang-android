package info.imdang.imdang.ui.insight

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.insight.ExchangeItem
import info.imdang.imdang.model.insight.InsightDetailItem
import info.imdang.imdang.model.insight.InsightDetailState
import info.imdang.imdang.model.insight.InsightDetailVo
import info.imdang.imdang.model.insight.InsightVo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightDetailViewModel @Inject constructor() : BaseViewModel() {

    private val _event = MutableSharedFlow<InsightDetailEvent>()
    val event = _event.asSharedFlow()

    private val _insight = MutableStateFlow(InsightDetailVo.getSample())
    val insight = _insight.asStateFlow()

    private val _insightDetails = MutableStateFlow<List<InsightDetailItem>>(emptyList())
    val insightDetails = _insightDetails.asStateFlow()

    private val _myInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    private val myInsights = _myInsights.asStateFlow()

    private val _exchangePassCount = MutableStateFlow(0)
    private val exchangePassCount = _exchangePassCount.asStateFlow()

    private val _exchangeItems = MutableStateFlow<List<ExchangeItem>>(emptyList())
    val exchangeItems = _exchangeItems.asStateFlow()

    private val _insightDetailState = MutableStateFlow(InsightDetailState.ExchangeRequest)
    val insightDetailState = _insightDetailState.asStateFlow()

    private val _isScrolling = MutableStateFlow(false)
    val isScrolling = _isScrolling.asStateFlow()

    init {
        _insightDetails.value =
            if (insightDetailState.value == InsightDetailState.ExchangeComplete) {
                listOf(
                    InsightDetailItem.BasicInfo(insight.value.basicInfo),
                    InsightDetailItem.Infra(insight.value.infra),
                    InsightDetailItem.AptEnvironment(insight.value.aptEnvironment),
                    InsightDetailItem.AptFacility(insight.value.aptFacility),
                    InsightDetailItem.GoodNews(insight.value.goodNews)
                )
            } else {
                listOf(
                    InsightDetailItem.BasicInfo(insight.value.basicInfo),
                    InsightDetailItem.Invisible(insightDetailState.value)
                )
            }
        _myInsights.value = InsightVo.getSamples(10)
        _exchangePassCount.value = 3
        _exchangeItems.value = mutableListOf<ExchangeItem>().apply {
            if (exchangePassCount.value > 0) add(ExchangeItem.Pass(exchangePassCount.value))
            if (myInsights.value.isNotEmpty()) {
                addAll(myInsights.value.map { ExchangeItem.Insight(it) })
            }
        }
    }

    fun isEnableTabMove() = insightDetailState.value == InsightDetailState.ExchangeComplete

    fun onClickTab() {
        _isScrolling.value = true
        viewModelScope.launch {
            delay(500)
            _isScrolling.value = false
        }
    }

    fun onClickRecommend() {
        if (insightDetailState.value == InsightDetailState.ExchangeComplete) {
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
            if (myInsights.value.isNotEmpty() || exchangePassCount.value > 0) {
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
            _insightDetailState.value = InsightDetailState.ExchangeComplete
            _insightDetails.value = listOf(
                InsightDetailItem.BasicInfo(insight.value.basicInfo),
                InsightDetailItem.Infra(insight.value.infra),
                InsightDetailItem.AptEnvironment(insight.value.aptEnvironment),
                InsightDetailItem.AptFacility(insight.value.aptFacility),
                InsightDetailItem.GoodNews(insight.value.goodNews)
            )
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
            val selectedExchangeItem = exchangeItems.value.firstOrNull {
                (it is ExchangeItem.Pass && it.isSelected) ||
                    (it is ExchangeItem.Insight && it.isSelected)
            } ?: return@launch
            _insightDetailState.value = InsightDetailState.ExchangeWaiting
            _insightDetails.value = insightDetails.value.map {
                if (it is InsightDetailItem.Invisible) it.copy(insightDetailState.value) else it
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

    fun onClickExchangeItem(exchangeItem: ExchangeItem) {
        _exchangeItems.value = exchangeItems.value.map {
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
}

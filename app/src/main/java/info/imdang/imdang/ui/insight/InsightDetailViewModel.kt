package info.imdang.imdang.ui.insight

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
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
    val myInsights = _myInsights.asStateFlow()

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
        _insight.value = insight.value.copy(isRecommended = !insight.value.isRecommended)
    }

    fun onClickExchangeRequestButton() {
        viewModelScope.launch {
            _event.emit(InsightDetailEvent.ShowMyInsightsBottomSheet)
        }
    }

    fun onClickRejectButton() {
        viewModelScope.launch {
            _event.emit(
                InsightDetailEvent.ShowExchangeDialog(message = "교환을 거절했어요.")
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
                InsightDetailEvent.ShowExchangeDialog(
                    message = "교환 요청을 수락했어요.\n보관함에서 확인해보세요.",
                    checkButtonText = "보관함 확인하기"
                )
            )
        }
    }

    fun requestExchange() {
        viewModelScope.launch {
            _insightDetailState.value = InsightDetailState.ExchangeWaiting
            _insightDetails.value = insightDetails.value.map {
                if (it is InsightDetailItem.Invisible) it.copy(insightDetailState.value) else it
            }
            _event.emit(
                InsightDetailEvent.ShowExchangeDialog(
                    message = "교환 요청을 완료했어요.\n교환 내역은 교환소에서 확인해보세요.",
                    checkButtonText = "교환소 확인하기"
                )
            )
        }
    }

    fun onClickMyInsight(myInsight: InsightVo) {
        _myInsights.value = myInsights.value.map {
            it.copy(isSelected = it.insightId == myInsight.insightId)
        }
    }
}

package info.imdang.imdang.ui.insight

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.insight.InsightDetailItem
import info.imdang.imdang.model.insight.InsightDetailState
import info.imdang.imdang.model.insight.InsightDetailVo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightDetailViewModel @Inject constructor() : BaseViewModel() {

    private val _insight = MutableStateFlow(InsightDetailVo.getSample())
    val insight = _insight.asStateFlow()

    private val _insightDetails = MutableStateFlow<List<InsightDetailItem>>(emptyList())
    val insightDetails = _insightDetails.asStateFlow()

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
    }

    fun isEnableTabMove() = insightDetailState.value == InsightDetailState.ExchangeComplete

    fun onClickTab() {
        _isScrolling.value = true
        viewModelScope.launch {
            delay(500)
            _isScrolling.value = false
        }
    }

    fun onClickExchangeRequestButton() {
        // todo : 교환 요청, 아래 코드는 테스트용
        _insightDetailState.value = InsightDetailState.ExchangeRequested
        _insightDetails.value = insightDetails.value.map {
            if (it is InsightDetailItem.Invisible) it.copy(insightDetailState.value) else it
        }
    }

    fun onClickRejectButton() {
        // todo : 교환 거절, 아래 코드는 테스트용
        _insightDetailState.value = InsightDetailState.ExchangeWaiting
        _insightDetails.value = insightDetails.value.map {
            if (it is InsightDetailItem.Invisible) it.copy(insightDetailState.value) else it
        }
    }

    fun onClickAcceptButton() {
        // todo : 교환 수락, 아래 코드는 테스트용
        _insightDetailState.value = InsightDetailState.ExchangeComplete
        _insightDetails.value = listOf(
            InsightDetailItem.BasicInfo(insight.value.basicInfo),
            InsightDetailItem.Infra(insight.value.infra),
            InsightDetailItem.AptEnvironment(insight.value.aptEnvironment),
            InsightDetailItem.AptFacility(insight.value.aptFacility),
            InsightDetailItem.GoodNews(insight.value.goodNews)
        )
    }
}

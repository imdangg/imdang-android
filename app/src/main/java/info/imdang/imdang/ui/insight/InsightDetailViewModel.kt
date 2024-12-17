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

    private val _insightDetailState = MutableStateFlow(InsightDetailState.ExchangeComplete)
    val insightDetailState = _insightDetailState.asStateFlow()

    private val _isScrolling = MutableStateFlow(false)
    val isScrolling = _isScrolling.asStateFlow()

    init {
        _insightDetails.value =
            if (insightDetailState.value == InsightDetailState.ExchangeComplete ||
                insightDetailState.value == InsightDetailState.ExchangeCompleteAfter7Days
            ) {
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
                    InsightDetailItem.Invisible
                )
            }
    }

    fun isEnableTabMove() =
        insightDetailState.value == InsightDetailState.ExchangeComplete ||
            insightDetailState.value == InsightDetailState.ExchangeCompleteAfter7Days

    fun onClickTab() {
        _isScrolling.value = true
        viewModelScope.launch {
            delay(500)
            _isScrolling.value = false
        }
    }

    fun onClickStateButton() {
        if (insightDetailState.value == InsightDetailState.ExchangeRequest) {
            // todo : 교환 요청, 아래 코드는 테스트용
            _insightDetailState.value = InsightDetailState.ExchangeRequested
        }
    }

    fun onClickRejectButton() {
        // todo : 교환 거절, 아래 코드는 테스트용
        _insightDetailState.value = InsightDetailState.ExchangeWaiting
    }

    fun onClickAcceptButton() {
        // todo : 교환 수락, 아래 코드는 테스트용
        _insightDetailState.value = InsightDetailState.ExchangeComplete
    }
}

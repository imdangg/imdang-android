package info.imdang.imdang.ui.insight

import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.insight.InsightDetailItem
import info.imdang.imdang.model.insight.InsightDetailVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InsightDetailViewModel @Inject constructor() : BaseViewModel() {

    private val _insight = MutableStateFlow(InsightDetailVo.getSample())
    val insight = _insight.asStateFlow()

    private val _insightDetails = MutableStateFlow<List<InsightDetailItem>>(emptyList())
    val insightDetails = _insightDetails.asStateFlow()

    init {
        _insightDetails.value = listOf(
            InsightDetailItem.BasicInfo(insight.value.basicInfo),
            InsightDetailItem.Infra(insight.value.infra),
            InsightDetailItem.AptEnvironment(insight.value.aptEnvironment),
            InsightDetailItem.AptFacility(insight.value.aptFacility),
            InsightDetailItem.GoodNews(insight.value.goodNews)
        )
    }
}

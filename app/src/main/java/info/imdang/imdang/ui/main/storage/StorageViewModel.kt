package info.imdang.imdang.ui.main.storage

import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.insight.InsightAptVo
import info.imdang.imdang.model.insight.InsightRegionVo
import info.imdang.imdang.model.insight.InsightVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor() : BaseViewModel() {

    private val _selectedInsightRegionPage = MutableStateFlow(-1)
    val selectedInsightRegionPage = _selectedInsightRegionPage.asStateFlow()

    private val _insightRegions = MutableStateFlow<List<InsightRegionVo>>(emptyList())
    val insightRegions = _insightRegions.asStateFlow()

    private val _insights = MutableStateFlow<List<InsightVo>>(emptyList())
    val insights = _insights.asStateFlow()

    private val _isSeeJustMyInsight = MutableStateFlow(false)
    val isSeeJustMyInsight = _isSeeJustMyInsight.asStateFlow()

    private val _insightApts = MutableStateFlow<List<InsightAptVo>>(emptyList())
    val insightApts = _insightApts.asStateFlow()

    init {
        _insightRegions.value = InsightRegionVo.getSamples(10)
        _insights.value = InsightVo.getSamples(10)
        _insightApts.value = InsightAptVo.getSamples(10)
    }

    fun selectInsightRegionPage(page: Int) {
        _selectedInsightRegionPage.value = page
        _insightRegions.value = insightRegions.value.mapIndexed { index, insightRegionVo ->
            insightRegionVo.copy(isSelected = index == page)
        }
    }

    fun toggleMyInsightOnly() {
        _isSeeJustMyInsight.value = !isSeeJustMyInsight.value
    }
}

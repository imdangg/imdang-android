package info.imdang.imdang.ui.main.storage

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.model.insight.InsightRegionVo
import info.imdang.imdang.model.insight.InsightVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor() : ViewModel() {

    private val _selectedInsightRegionPage = MutableStateFlow(-1)
    val selectedInsightRegionPage = _selectedInsightRegionPage.asStateFlow()

    private val _insightRegions = MutableStateFlow<List<InsightRegionVo>>(emptyList())
    val insightRegions = _insightRegions.asStateFlow()

    private val _insights = MutableStateFlow<List<InsightVo>>(emptyList())
    val insights = _insights.asStateFlow()

    private val _isSeeJustMyInsight = MutableStateFlow(false)
    val isSeeJustMyInsight = _isSeeJustMyInsight.asStateFlow()

    init {
        _insightRegions.value = InsightRegionVo.getSamples(10)
        _insights.value = InsightVo.getSamples(10)
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

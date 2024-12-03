package info.imdang.imdang.ui.main.storage.region

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.model.insight.InsightRegionVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class InsightRegionViewModel @Inject constructor() : ViewModel() {

    private val _insightRegions = MutableStateFlow<List<InsightRegionVo>>(emptyList())
    val insightRegions = _insightRegions.asStateFlow()

    init {
        _insightRegions.value = InsightRegionVo.getSamples(10)
    }

    fun onClickInsight(insightRegionVo: InsightRegionVo) {
        _insightRegions.value = insightRegions.value.map {
            it.copy(isSelected = it.regionId == insightRegionVo.regionId)
        }
    }
}

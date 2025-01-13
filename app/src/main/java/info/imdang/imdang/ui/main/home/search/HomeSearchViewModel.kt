package info.imdang.imdang.ui.main.home.search

import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.insight.InsightAptVo
import info.imdang.imdang.model.insight.InsightVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeSearchViewModel @Inject constructor() : BaseViewModel() {

    private val _visitComplexInsightApts = MutableStateFlow<List<InsightAptVo>>(emptyList())
    val visitComplexInsightApts = _visitComplexInsightApts.asStateFlow()

    private val _visitComplexInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val visitComplexInsights = _visitComplexInsights.asStateFlow()

    private val _newInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val newInsights = _newInsights.asStateFlow()

    private val _recommendInsights = MutableStateFlow<List<List<InsightVo>>>(emptyList())
    val recommendInsights = _recommendInsights.asStateFlow()

    private val _selectedRecommendInsightPage = MutableStateFlow(1)
    val selectedRecommendInsightPage = _selectedRecommendInsightPage.asStateFlow()

    init {
        _visitComplexInsightApts.value = InsightAptVo.getSamples(3)
        _visitComplexInsights.value = InsightVo.getSamples(size = 3)
        _newInsights.value = InsightVo.getSamples(size = 6)
        _recommendInsights.value = listOf(
            InsightVo.getSamples(3),
            InsightVo.getSamples(3),
            InsightVo.getSamples(3),
            InsightVo.getSamples(1)
        )
    }

    fun selectRecommendInsightPage(page: Int) {
        _selectedRecommendInsightPage.value = page + 1
    }
}

package info.imdang.imdang.ui.main.home.search.visitcomplex

import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.insight.InsightAptVo
import info.imdang.imdang.model.insight.InsightVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class VisitComplexInsightListViewModel @Inject constructor() : BaseViewModel() {

    private val _visitComplexInsightApts = MutableStateFlow<List<InsightAptVo>>(emptyList())
    val visitComplexInsightApts = _visitComplexInsightApts.asStateFlow()

    private val _visitComplexInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val visitComplexInsights = _visitComplexInsights.asStateFlow()

    init {
        _visitComplexInsightApts.value = InsightAptVo.getSamples(5)
        _visitComplexInsights.value = InsightVo.getSamples(33)
    }
}

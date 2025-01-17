package info.imdang.imdang.ui.main.home.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.usecase.aptcomplex.GetVisitedAptComplexesUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.aptcomplex.VisitedAptComplexVo
import info.imdang.imdang.model.aptcomplex.mapper
import info.imdang.imdang.model.insight.InsightVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeSearchViewModel @Inject constructor(
    private val getVisitedAptComplexesUseCase: GetVisitedAptComplexesUseCase
) : BaseViewModel() {

    private val _visitedAptComplexes = MutableStateFlow<List<VisitedAptComplexVo>>(emptyList())
    val visitedAptComplexes = _visitedAptComplexes.asStateFlow()

    private val _visitedAptComplexInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val visitedAptComplexInsights = _visitedAptComplexInsights.asStateFlow()

    private val _newInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val newInsights = _newInsights.asStateFlow()

    private val _recommendInsights = MutableStateFlow<List<List<InsightVo>>>(emptyList())
    val recommendInsights = _recommendInsights.asStateFlow()

    private val _selectedRecommendInsightPage = MutableStateFlow(1)
    val selectedRecommendInsightPage = _selectedRecommendInsightPage.asStateFlow()

    init {
        _newInsights.value = InsightVo.getSamples(size = 6)
        _recommendInsights.value = listOf(
            InsightVo.getSamples(3),
            InsightVo.getSamples(3),
            InsightVo.getSamples(3),
            InsightVo.getSamples(1)
        )

        viewModelScope.launch {
            _visitedAptComplexes.value =
                getVisitedAptComplexesUseCase(Unit)?.mapIndexed { index, visitedAptComplexDto ->
                    visitedAptComplexDto.mapper(isSelected = index == 0)
                } ?: emptyList()
            _visitedAptComplexInsights.value = InsightVo.getSamples(size = 3)
        }
    }

    fun selectRecommendInsightPage(page: Int) {
        _selectedRecommendInsightPage.value = page + 1
    }
}

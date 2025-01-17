package info.imdang.imdang.ui.main.home.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.usecase.aptcomplex.GetVisitedAptComplexesUseCase
import info.imdang.domain.usecase.insight.GetInsightsByAptComplexUseCase
import info.imdang.domain.usecase.insight.GetInsightsUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.aptcomplex.VisitedAptComplexVo
import info.imdang.imdang.model.aptcomplex.mapper
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.model.insight.mapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeSearchViewModel @Inject constructor(
    private val getVisitedAptComplexesUseCase: GetVisitedAptComplexesUseCase,
    private val getInsightsByAptComplexUseCase: GetInsightsByAptComplexUseCase,
    private val getInsightsUseCase: GetInsightsUseCase
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
        fetchVisitedAptComplexes()
        fetchInsights()

        _recommendInsights.value = listOf(
            InsightVo.getSamples(3),
            InsightVo.getSamples(3),
            InsightVo.getSamples(3),
            InsightVo.getSamples(1)
        )
    }

    private fun fetchVisitedAptComplexes() {
        viewModelScope.launch {
            _visitedAptComplexes.value =
                getVisitedAptComplexesUseCase(Unit)?.mapIndexed { index, visitedAptComplexDto ->
                    visitedAptComplexDto.mapper(isSelected = index == 0)
                } ?: emptyList()
            fetchInsightsByAptComplex()
        }
    }

    private fun fetchInsightsByAptComplex() {
        viewModelScope.launch {
            val aptComplex = visitedAptComplexes.value.firstOrNull {
                it.isSelected
            }?.name ?: return@launch
            _visitedAptComplexInsights.value = getInsightsByAptComplexUseCase(
                PagingParams(
                    page = 1,
                    size = 3,
                    additionalParams = aptComplex
                )
            )?.content?.map {
                it.mapper()
            } ?: emptyList()
        }
    }

    private fun fetchInsights() {
        viewModelScope.launch {
            _newInsights.value = getInsightsUseCase(PagingParams())?.content?.map {
                it.mapper()
            } ?: emptyList()
        }
    }

    fun onClickVisitedAptComplex(visitedAptComplexVo: VisitedAptComplexVo) {
        _visitedAptComplexes.value = visitedAptComplexes.value.map {
            it.copy(isSelected = it.name == visitedAptComplexVo.name)
        }
        fetchInsightsByAptComplex()
    }

    fun selectRecommendInsightPage(page: Int) {
        _selectedRecommendInsightPage.value = page + 1
    }
}

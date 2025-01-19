package info.imdang.imdang.ui.main.home.search

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.usecase.aptcomplex.GetVisitedAptComplexesUseCase
import info.imdang.domain.usecase.insight.GetInsightsByAptComplexParams
import info.imdang.domain.usecase.insight.GetInsightsByAptComplexUseCase
import info.imdang.domain.usecase.insight.GetInsightsUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.common.ext.snakeToCamelCase
import info.imdang.imdang.model.aptcomplex.VisitAptComplexVo
import info.imdang.imdang.model.aptcomplex.mapper
import info.imdang.imdang.model.common.PagingDirection
import info.imdang.imdang.model.common.PagingProperty
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

    private val _visitedAptComplexes = MutableStateFlow<List<VisitAptComplexVo>>(emptyList())
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
        fetchRecommendInsights()
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
            }?.aptComplexName ?: return@launch
            _visitedAptComplexInsights.value = getInsightsByAptComplexUseCase(
                GetInsightsByAptComplexParams(
                    aptComplex = aptComplex,
                    pagingParams = PagingParams(
                        page = 1,
                        size = 3
                    )
                )
            )?.content?.map(InsightDto::mapper) ?: emptyList()
        }
    }

    private fun fetchInsights() {
        viewModelScope.launch {
            _newInsights.value = getInsightsUseCase(
                PagingParams()
            )?.content?.map(InsightDto::mapper) ?: emptyList()
        }
    }

    private fun fetchRecommendInsights() {
        viewModelScope.launch {
            val recommendInsights = getInsightsUseCase(
                PagingParams(
                    size = 10,
                    direction = PagingDirection.DESC.name,
                    properties = listOf(PagingProperty.RECOMMENDED_COUNT.name.snakeToCamelCase())
                )
            )?.content?.map(InsightDto::mapper) ?: emptyList()
            _recommendInsights.value = recommendInsights.chunked(3)
        }
    }

    fun onClickVisitedAptComplex(aptComplexVo: VisitAptComplexVo) {
        _visitedAptComplexes.value = visitedAptComplexes.value.map {
            it.copy(isSelected = it.aptComplexName == aptComplexVo.aptComplexName)
        }
        fetchInsightsByAptComplex()
    }

    fun selectRecommendInsightPage(page: Int) {
        _selectedRecommendInsightPage.value = page + 1
    }
}

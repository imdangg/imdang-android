package info.imdang.imdang.ui.main.home.search.visitcomplex

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.usecase.aptcomplex.GetVisitedAptComplexesUseCase
import info.imdang.domain.usecase.insight.GetInsightsByAptComplexParams
import info.imdang.domain.usecase.insight.GetInsightsByAptComplexUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.aptcomplex.AptComplexVo
import info.imdang.imdang.model.aptcomplex.mapper
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.model.insight.mapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisitComplexInsightListViewModel @Inject constructor(
    private val getVisitedAptComplexesUseCase: GetVisitedAptComplexesUseCase,
    private val getInsightsByAptComplexUseCase: GetInsightsByAptComplexUseCase
) : BaseViewModel() {

    private val _visitedAptComplexes = MutableStateFlow<List<AptComplexVo>>(emptyList())
    val visitedAptComplexes = _visitedAptComplexes.asStateFlow()

    private val _visitedAptComplexInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val visitedAptComplexInsights = _visitedAptComplexInsights.asStateFlow()

    init {
        fetchVisitedAptComplexes()
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
                        size = 20
                    )
                )
            )?.content?.map {
                it.mapper()
            } ?: emptyList()
        }
    }

    fun onClickVisitedAptComplex(aptComplexVo: AptComplexVo) {
        _visitedAptComplexes.value = visitedAptComplexes.value.map {
            it.copy(isSelected = it.aptComplexName == aptComplexVo.aptComplexName)
        }
        fetchInsightsByAptComplex()
    }
}

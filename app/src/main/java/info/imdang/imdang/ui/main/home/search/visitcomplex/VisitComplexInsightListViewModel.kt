package info.imdang.imdang.ui.main.home.search.visitcomplex

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.usecase.aptcomplex.GetVisitedAptComplexesUseCase
import info.imdang.domain.usecase.insight.GetInsightsByAptComplexParams
import info.imdang.domain.usecase.insight.GetInsightsByAptComplexWithPagingUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.aptcomplex.VisitAptComplexVo
import info.imdang.imdang.model.aptcomplex.mapper
import info.imdang.imdang.model.common.PagingState
import info.imdang.imdang.model.insight.mapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisitComplexInsightListViewModel @Inject constructor(
    private val getVisitedAptComplexesUseCase: GetVisitedAptComplexesUseCase,
    private val getInsightsByAptComplexWithPagingUseCase: GetInsightsByAptComplexWithPagingUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<VisitComplexInsightListEvent>()
    val event = _event.asSharedFlow()

    private val _visitedAptComplexes = MutableStateFlow<List<VisitAptComplexVo>>(emptyList())
    val visitedAptComplexes = _visitedAptComplexes.asStateFlow()

    private val _pagingState = MutableStateFlow(PagingState())
    val pagingState = _pagingState.asStateFlow()

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
            getInsightsByAptComplexWithPagingUseCase(
                GetInsightsByAptComplexParams(
                    aptComplex = aptComplex,
                    pagingParams = PagingParams()
                )
            )
                ?.cachedIn(this)
                ?.collect {
                    _event.emit(
                        VisitComplexInsightListEvent.UpdateInsights(it.map(InsightDto::mapper))
                    )
                }
        }
    }

    fun updatePagingState(
        isLoading: Boolean? = null,
        itemCount: Int? = null,
        error: String? = null
    ) {
        _pagingState.value = pagingState.value.copy(
            isLoading = isLoading ?: pagingState.value.isLoading,
            itemCount = itemCount ?: pagingState.value.itemCount,
            error = error ?: pagingState.value.error
        )
    }

    fun onClickVisitedAptComplex(aptComplexVo: VisitAptComplexVo) {
        _visitedAptComplexes.value = visitedAptComplexes.value.map {
            it.copy(isSelected = it.aptComplexName == aptComplexVo.aptComplexName)
        }
        fetchInsightsByAptComplex()
    }
}

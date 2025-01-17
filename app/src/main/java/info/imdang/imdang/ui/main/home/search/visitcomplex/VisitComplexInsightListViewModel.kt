package info.imdang.imdang.ui.main.home.search.visitcomplex

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
class VisitComplexInsightListViewModel @Inject constructor(
    private val getVisitedAptComplexesUseCase: GetVisitedAptComplexesUseCase
) : BaseViewModel() {

    private val _visitedAptComplexes = MutableStateFlow<List<VisitedAptComplexVo>>(emptyList())
    val visitedAptComplexes = _visitedAptComplexes.asStateFlow()

    private val _visitedAptComplexInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val visitedAptComplexInsights = _visitedAptComplexInsights.asStateFlow()

    init {
        viewModelScope.launch {
            _visitedAptComplexes.value =
                getVisitedAptComplexesUseCase(Unit)?.mapIndexed { index, visitedAptComplexDto ->
                    visitedAptComplexDto.mapper(isSelected = index == 0)
                } ?: emptyList()
            _visitedAptComplexInsights.value = InsightVo.getSamples(33)
        }
    }
}

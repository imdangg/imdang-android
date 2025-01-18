package info.imdang.imdang.ui.main.home.search.newinsight

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.usecase.insight.GetInsightsUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.model.insight.mapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewInsightViewModel @Inject constructor(
    private val getInsightsUseCase: GetInsightsUseCase
) : BaseViewModel() {

    private val _newInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val newInsights = _newInsights.asStateFlow()

    init {
        fetchInsights()
    }

    private fun fetchInsights() {
        viewModelScope.launch {
            _newInsights.value = getInsightsUseCase(
                PagingParams(page = 1, size = 20)
            )?.content?.map {
                it.mapper()
            } ?: emptyList()
        }
    }
}

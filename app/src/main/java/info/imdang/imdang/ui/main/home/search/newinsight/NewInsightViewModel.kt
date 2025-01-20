package info.imdang.imdang.ui.main.home.search.newinsight

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.usecase.insight.GetInsightsWithPagingUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.common.PagingState
import info.imdang.imdang.model.insight.mapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewInsightViewModel @Inject constructor(
    private val getInsightsWithPagingUseCase: GetInsightsWithPagingUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<NewInsightListEvent>()
    val event = _event.asSharedFlow()

    private val _pagingState = MutableStateFlow(PagingState())
    val pagingState = _pagingState.asStateFlow()

    init {
        fetchInsights()
    }

    private fun fetchInsights() {
        viewModelScope.launch {
            getInsightsWithPagingUseCase(PagingParams())
                ?.cachedIn(this)
                ?.collect {
                    _event.emit(NewInsightListEvent.UpdateInsights(it.map(InsightDto::mapper)))
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
}

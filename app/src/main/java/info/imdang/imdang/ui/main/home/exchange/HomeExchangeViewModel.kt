package info.imdang.imdang.ui.main.home.exchange

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.insight.InsightVo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeExchangeViewModel @Inject constructor() : BaseViewModel() {

    private val _event = MutableSharedFlow<HomeExchangeEvent>()
    val event = _event.asSharedFlow()

    private val _selectedChipId = MutableStateFlow(1)
    val selectedChipId = _selectedChipId.asStateFlow()

    private val _chipDescription = MutableStateFlow<List<String>>(emptyList())
    val chipDescription = _selectedChipId.map { chipId ->
        _chipDescription.value.getOrNull(chipId - 1) ?: ""
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    private val _requestedInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val requestedInsights = _requestedInsights.asStateFlow()

    init {
        updateInsightsForChip(1)
    }

    override fun <T> onClickItem(item: T) {
        if (item is InsightVo) {
            viewModelScope.launch {
                _event.emit(HomeExchangeEvent.OnClickInsight(item))
            }
        }
    }

    fun setChipDescriptions(descriptions: List<String>) {
        _chipDescription.value = descriptions
    }

    fun onChipClicked(chipId: Int) {
        _selectedChipId.value = chipId
        updateInsightsForChip(chipId)
    }

    private fun updateInsightsForChip(chipId: Int) {
        _requestedInsights.value = when (chipId) {
            1 -> InsightVo.getSamples(size = 5)
            2 -> InsightVo.getSamples(size = 2)
            3 -> InsightVo.getSamples(size = 1)
            else -> emptyList()
        }
    }
}

package info.imdang.imdang.ui.main.home.exchange

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.imdang.model.insight.InsightVo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeExchangeViewModel @Inject constructor(
    application: Application
) : ViewModel() {

    private val resources = application.resources

    private val _selectedChipId = MutableStateFlow(1)
    val selectedChipId = _selectedChipId.asStateFlow()

    val chipDescription = _selectedChipId.map { chipId ->
        when (chipId) {
            1 -> resources.getString(info.imdang.component.R.string.waiting_details_existence)
            2 -> resources.getString(info.imdang.component.R.string.refusal_details_existence)
            3 -> resources.getString(info.imdang.component.R.string.exchange_details_existence)
            else -> ""
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    private val _requestedInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val requestedInsights = _requestedInsights.asStateFlow()

    init {
        updateInsightsForChip(1)
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

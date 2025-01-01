package info.imdang.imdang.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _isShowTooltip = MutableStateFlow(false)
    val isShowTooltip = _isShowTooltip.asStateFlow()

    fun showTooltip() {
        _isShowTooltip.value = true
    }

    fun hideTooltip() {
        _isShowTooltip.value = false
    }
}

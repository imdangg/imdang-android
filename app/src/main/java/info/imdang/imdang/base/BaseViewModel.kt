package info.imdang.imdang.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
open class BaseViewModel @Inject constructor() : ViewModel() {

    fun <T> StateFlow<T?>.isCheckVisible() = map {
        if (it is Set<*>) it.isNotEmpty() else it != null
    }.toStateFlow(false)

    fun StateFlow<String>.isValid() = map { it.isNotBlank() }.toStateFlow(false)

    fun <T> Flow<T>.toStateFlow(initialValue: T) = stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        initialValue
    )
}

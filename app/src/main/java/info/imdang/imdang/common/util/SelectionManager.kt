package info.imdang.imdang.common.util

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SelectionManager(private val isSingleSelection: Boolean = false) {

    private val _selectedItems = MutableStateFlow<Set<String>>(emptySet())
    val selectedItems: StateFlow<Set<String>> = _selectedItems

    private val _showResetDialog = MutableStateFlow(false)
    val showResetDialog: StateFlow<Boolean> = _showResetDialog

    fun toggleSelection(item: String, resetItem: String = "해당 없음") {
        val currentSelection = _selectedItems.value
        when (item) {
            resetItem -> {
                if (currentSelection.isNotEmpty() && !currentSelection.contains(resetItem)) {
                    _showResetDialog.value = true
                } else {
                    if (isSingleSelection && currentSelection.contains(item)) {
                        _selectedItems.value = setOf(item)
                    } else {
                        _selectedItems.value = if (currentSelection.contains(item)) {
                            emptySet()
                        } else {
                            setOf(item)
                        }
                    }
                }
            }
            else -> {
                _selectedItems.value = if (isSingleSelection) {
                    setOf(item)
                } else {
                    if (currentSelection.contains(resetItem)) {
                        setOf(item)
                    } else {
                        if (currentSelection.contains(item)) {
                            currentSelection - item
                        } else {
                            currentSelection + item
                        }
                    }
                }
            }
        }
    }

    fun confirmReset(resetItem: String = "해당 없음") {
        _selectedItems.value = setOf(resetItem)
        _showResetDialog.value = false
    }

    fun cancelReset() {
        _showResetDialog.value = false
    }
}

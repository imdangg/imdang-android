package info.imdang.imdang.ui.main.storage.address

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.usecase.myinsight.GetAddressesUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.myinsight.MyInsightAddressVo
import info.imdang.imdang.model.myinsight.mapper
import info.imdang.imdang.ui.main.storage.address.InsightAddressActivity.Companion.SELECTED_PAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InsightAddressViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAddressesUseCase: GetAddressesUseCase
) : BaseViewModel() {

    private val _selectedPage = MutableStateFlow(savedStateHandle[SELECTED_PAGE] ?: 0)
    val selectedPage = _selectedPage.asStateFlow()

    private val _insightAddresses = MutableStateFlow<List<MyInsightAddressVo>>(emptyList())
    val insightAddresses = _insightAddresses.asStateFlow()

    init {
        fetchAddresses()
    }

    private fun fetchAddresses() {
        viewModelScope.launch {
            _insightAddresses.value = getAddressesUseCase(Unit)
                ?.mapIndexed { index, myInsightAddressDto ->
                    myInsightAddressDto.mapper(isSelected = index == selectedPage.value)
                } ?: emptyList()
        }
    }

    fun onClickAddress(item: MyInsightAddressVo) {
        _insightAddresses.value = insightAddresses.value.mapIndexed { index, storageAddressVo ->
            if (storageAddressVo.toSiGuDong() == item.toSiGuDong()) {
                _selectedPage.value = index
                storageAddressVo.copy(isSelected = true)
            } else {
                storageAddressVo.copy(isSelected = false)
            }
        }
    }
}

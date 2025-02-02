package info.imdang.imdang.ui.main.storage

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.myinsight.AptComplexDto
import info.imdang.domain.usecase.myinsight.GetAddressesUseCase
import info.imdang.domain.usecase.myinsight.GetComplexesByAddressUseCase
import info.imdang.domain.usecase.myinsight.GetInsightsByAddressParams
import info.imdang.domain.usecase.myinsight.GetInsightsByAddressUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.common.PagingState
import info.imdang.imdang.model.myinsight.AptComplexVo
import info.imdang.imdang.model.myinsight.mapper
import info.imdang.imdang.model.insight.mapper
import info.imdang.imdang.model.myinsight.MyInsightAddressVo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val getAddressesUseCase: GetAddressesUseCase,
    private val getComplexesByAddressUseCase: GetComplexesByAddressUseCase,
    private val getInsightsByAddressUseCase: GetInsightsByAddressUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<StorageEvent>()
    val event = _event.asSharedFlow()

    private val _isCollapsed = MutableStateFlow(false)
    val isCollapsed = _isCollapsed.asStateFlow()

    private val _selectedInsightAddressPage = MutableStateFlow(-1)
    val selectedInsightAddressPage = _selectedInsightAddressPage.asStateFlow()

    private val _pagingState = MutableStateFlow(PagingState())
    val pagingState = _pagingState.asStateFlow()

    private val _addresses = MutableStateFlow<List<MyInsightAddressVo>>(emptyList())
    val addresses = _addresses.asStateFlow()

    private val selectedAddress = addresses.map { addresses ->
        addresses.firstOrNull { it.isSelected }
    }.toStateFlow(null)

    private val _isSeeOnlyMyInsight = MutableStateFlow(false)
    val isSeeOnlyMyInsight = _isSeeOnlyMyInsight.asStateFlow()

    private val _complexes = MutableStateFlow<List<AptComplexVo>>(emptyList())
    val complexes = _complexes.asStateFlow()

    private val _selectedComplex = MutableStateFlow<AptComplexVo?>(null)
    val selectedComplex = _selectedComplex.asStateFlow()

    init {
        fetchAddresses()
    }

    private fun fetchAddresses() {
        viewModelScope.launch {
            _addresses.value = getAddressesUseCase(Unit)?.mapIndexed { index, myInsightAddressDto ->
                myInsightAddressDto.mapper(isSelected = index == 0)
            } ?: emptyList()
            fetchComplexesByAddress()
            fetchInsightsByAddress()
        }
    }

    private fun fetchComplexesByAddress() {
        viewModelScope.launch {
            _complexes.value = getComplexesByAddressUseCase(
                selectedAddress.value?.toAddressDto() ?: return@launch
            )?.map(AptComplexDto::mapper) ?: emptyList()
        }
    }

    private fun fetchInsightsByAddress() {
        viewModelScope.launch {
            getInsightsByAddressUseCase(
                GetInsightsByAddressParams(
                    address = selectedAddress.value?.toAddressDto() ?: return@launch,
                    aptComplexName = selectedComplex.value?.aptComplexName,
                    onlyMine = isSeeOnlyMyInsight.value,
                    pagingParams = PagingParams(
                        totalCountListener = {
                            updatePagingState(itemCount = it)
                        }
                    )
                )
            )
                ?.cachedIn(this)
                ?.collect {
                    _event.emit(StorageEvent.UpdateInsights(it.map(InsightDto::mapper)))
                }
        }
    }

    fun updateCollapsed(isCollapsed: Boolean) {
        _isCollapsed.value = isCollapsed
    }

    fun getSelectedDong() = addresses.value.first { it.isSelected }.eupMyeonDong

    fun selectInsightAddressPage(page: Int) {
        _selectedInsightAddressPage.value = page
        _addresses.value = addresses.value.mapIndexed { index, myInsightAddressVo ->
            myInsightAddressVo.copy(isSelected = index == page)
        }
        _selectedComplex.value = null
        fetchComplexesByAddress()
        fetchInsightsByAddress()
    }

    fun toggleMyInsightOnly() {
        _isSeeOnlyMyInsight.value = !isSeeOnlyMyInsight.value
        fetchInsightsByAddress()
    }

    fun updateSelectedComplex(item: AptComplexVo?) {
        _selectedComplex.value = item
        fetchInsightsByAddress()
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

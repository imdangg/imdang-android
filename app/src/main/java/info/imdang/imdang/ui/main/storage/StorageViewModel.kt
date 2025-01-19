package info.imdang.imdang.ui.main.storage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.aptcomplex.AptComplexDto
import info.imdang.domain.usecase.myinsight.GetAddressesUseCase
import info.imdang.domain.usecase.myinsight.GetComplexesByAddressUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.aptcomplex.AptComplexVo
import info.imdang.imdang.model.aptcomplex.mapper
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.model.myinsight.MyInsightAddressVo
import info.imdang.imdang.model.myinsight.mapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val getAddressesUseCase: GetAddressesUseCase,
    private val getComplexesByAddressUseCase: GetComplexesByAddressUseCase
) : BaseViewModel() {

    private val _selectedInsightAddressPage = MutableStateFlow(-1)
    val selectedInsightAddressPage = _selectedInsightAddressPage.asStateFlow()

    private val _addresses = MutableStateFlow<List<MyInsightAddressVo>>(emptyList())
    val addresses = _addresses.asStateFlow()

    private val _insights = MutableStateFlow<List<InsightVo>>(emptyList())
    val insights = _insights.asStateFlow()

    private val _myInsights = MutableStateFlow<List<InsightVo>>(emptyList())
    val myInsights = _myInsights.asStateFlow()

    private val _isSeeJustMyInsight = MutableStateFlow(false)
    val isSeeJustMyInsight = _isSeeJustMyInsight.asStateFlow()

    private val _complexes = MutableStateFlow<List<AptComplexVo>>(emptyList())
    val complexes = _complexes.asStateFlow()

    init {
        fetchAddresses()
        _insights.value = InsightVo.getSamples(10)
        _myInsights.value = InsightVo.getSamples(3)
    }

    private fun fetchAddresses() {
        viewModelScope.launch {
            _addresses.value = getAddressesUseCase(Unit)?.mapIndexed { index, myInsightAddressDto ->
                myInsightAddressDto.mapper(isSelected = index == 0)
            } ?: emptyList()
            fetchComplexesByAddress()
        }
    }

    private fun fetchComplexesByAddress() {
        viewModelScope.launch {
            val selectedAddress = addresses.value.firstOrNull {
                it.isSelected
            } ?: return@launch
            _complexes.value = getComplexesByAddressUseCase(
                selectedAddress.toAddressDto()
            )?.map(AptComplexDto::mapper) ?: emptyList()
        }
    }

    fun selectInsightAddressPage(page: Int) {
        _selectedInsightAddressPage.value = page
        _addresses.value = addresses.value.mapIndexed { index, myInsightAddressVo ->
            myInsightAddressVo.copy(isSelected = index == page)
        }
        fetchComplexesByAddress()
    }

    fun toggleMyInsightOnly() {
        _isSeeJustMyInsight.value = !isSeeJustMyInsight.value
    }
}

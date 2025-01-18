package info.imdang.imdang.ui.main.storage

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.usecase.myinsight.GetAddressesUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.insight.InsightAptVo
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.model.myinsight.MyInsightAddressVo
import info.imdang.imdang.model.myinsight.mapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val getAddressesUseCase: GetAddressesUseCase
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

    private val _insightApts = MutableStateFlow<List<InsightAptVo>>(emptyList())
    val insightApts = _insightApts.asStateFlow()

    init {
        fetchAddresses()
        _insights.value = InsightVo.getSamples(10)
        _myInsights.value = InsightVo.getSamples(3)
        _insightApts.value = InsightAptVo.getSamples(10)
    }

    private fun fetchAddresses() {
        viewModelScope.launch {
            _addresses.value = getAddressesUseCase(Unit)?.mapIndexed { index, myInsightAddressDto ->
                myInsightAddressDto.mapper(isSelected = index == 0)
            } ?: emptyList()
        }
    }

    fun selectInsightAddressPage(page: Int) {
        _selectedInsightAddressPage.value = page
        _addresses.value = addresses.value.mapIndexed { index, myInsightAddressVo ->
            myInsightAddressVo.copy(isSelected = index == page)
        }
    }

    fun toggleMyInsightOnly() {
        _isSeeJustMyInsight.value = !isSeeJustMyInsight.value
    }
}

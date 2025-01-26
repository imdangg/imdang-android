package info.imdang.imdang.ui.main.home.exchange

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.MyExchangesParams
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.usecase.myexchange.GetMyExchangeUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.insight.ExchangeRequestStatus
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.model.insight.mapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeExchangeViewModel @Inject constructor(
    private val getMyExchangeUseCase: GetMyExchangeUseCase
) : BaseViewModel() {

    private val _selectedChipId = MutableStateFlow(1)
    val selectedChipId = _selectedChipId.asStateFlow()

    private val _selectedChipCounts = MutableStateFlow(mapOf<ExchangeRequestStatus, Int>())
    val selectedChipCounts = _selectedChipCounts.asStateFlow()

    private val _myExchanges = MutableStateFlow<List<InsightVo>>(emptyList())
    val myExchanges = _myExchanges.asStateFlow()

    init {
        fetchMyExchange(ExchangeRequestStatus.PENDING)
    }

    fun onChipClicked(chipId: Int) {
        _selectedChipId.value = chipId

        val status = when (chipId) {
            1 -> ExchangeRequestStatus.PENDING
            2 -> ExchangeRequestStatus.REJECTED
            3 -> ExchangeRequestStatus.ACCEPTED
            else -> null
        }
        status?.let { fetchMyExchange(it) }
    }

    private fun fetchMyExchange(exchangeRequestStatus: ExchangeRequestStatus) {
        viewModelScope.launch {
            val response = getMyExchangeUseCase(
                MyExchangesParams(
                    exchangeRequestStatus = exchangeRequestStatus.name,
                    pagingParams = PagingParams()
                )
            )

            val totalCount = response?.totalElements ?: 0
            _selectedChipCounts.value = _selectedChipCounts.value.toMutableMap().apply {
                this[exchangeRequestStatus] = totalCount
            }

            _myExchanges.value = response?.content?.map(InsightDto::mapper) ?: emptyList()
        }
    }
}

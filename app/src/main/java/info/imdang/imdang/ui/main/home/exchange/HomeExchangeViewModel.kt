package info.imdang.imdang.ui.main.home.exchange

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.MyExchangesParams
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.usecase.coupon.GetCouponUseCase
import info.imdang.domain.usecase.myexchange.GetMyExchangeUseCase
import info.imdang.domain.usecase.myexchange.GetOthersExchangeUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.model.coupon.CouponVo
import info.imdang.imdang.model.coupon.mapper
import info.imdang.imdang.model.insight.ExchangeRequestStatus
import info.imdang.imdang.model.insight.InsightVo
import info.imdang.imdang.model.insight.mapper
import info.imdang.imdang.ui.main.home.history.ExchangeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeExchangeViewModel @Inject constructor(
    private val getMyExchangeUseCase: GetMyExchangeUseCase,
    private val getOthersExchangeUseCase: GetOthersExchangeUseCase,
    private val getCouponCountUseCase: GetCouponUseCase
) : BaseViewModel() {

    private val _currentExchangeType = MutableStateFlow(ExchangeType.REQUESTED)
    val currentExchangeType = _currentExchangeType.asStateFlow()

    private val _selectedChipId = MutableStateFlow(1)
    val selectedChipId = _selectedChipId.asStateFlow()

    private val _mySelectedChipCounts = MutableStateFlow(mapOf<ExchangeRequestStatus, Int>())
    val mySelectedChipCounts = _mySelectedChipCounts.asStateFlow()

    private val _othersSelectedChipCounts = MutableStateFlow(mapOf<ExchangeRequestStatus, Int>())
    val othersSelectedChipCounts = _othersSelectedChipCounts.asStateFlow()

    private val _myExchanges = MutableStateFlow<List<InsightVo>>(emptyList())
    val myExchanges = _myExchanges.asStateFlow()

    private val _othersExchanges = MutableStateFlow<List<InsightVo>>(emptyList())
    val othersExchanges = _othersExchanges.asStateFlow()

    private val _coupon = MutableStateFlow(CouponVo.init())
    val coupon = _coupon.asStateFlow()

    init {
        fetchMyExchange(ExchangeRequestStatus.PENDING)
        fetchOthersExchange(ExchangeRequestStatus.PENDING)
        fetchCoupon()
    }

    fun onChipClicked(chipId: Int) {
        _selectedChipId.value = chipId

        val status = when (chipId) {
            1 -> ExchangeRequestStatus.PENDING
            2 -> ExchangeRequestStatus.REJECTED
            3 -> ExchangeRequestStatus.ACCEPTED
            else -> null
        }

        status?.let {
            when (_currentExchangeType.value) {
                ExchangeType.REQUESTED -> fetchMyExchange(it)
                ExchangeType.RECEIVED -> fetchOthersExchange(it)
            }
        }
    }

    fun updateExchangeType(type: ExchangeType) {
        _currentExchangeType.value = type
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
            _mySelectedChipCounts.value = _mySelectedChipCounts.value.toMutableMap().apply {
                this[exchangeRequestStatus] = totalCount
            }

            _myExchanges.value = response?.content?.map(InsightDto::mapper) ?: emptyList()
        }
    }

    private fun fetchOthersExchange(exchangeRequestStatus: ExchangeRequestStatus) {
        viewModelScope.launch {
            val response = getOthersExchangeUseCase(
                MyExchangesParams(
                    exchangeRequestStatus = exchangeRequestStatus.name,
                    pagingParams = PagingParams()
                )
            )

            val totalCount = response?.totalElements ?: 0
            _othersSelectedChipCounts.value = _othersSelectedChipCounts.value.toMutableMap().apply {
                this[exchangeRequestStatus] = totalCount
            }

            _othersExchanges.value = response?.content?.map(InsightDto::mapper) ?: emptyList()
        }
    }

    private fun fetchCoupon() {
        viewModelScope.launch {
            getCouponCountUseCase(Unit)?.let {
                _coupon.value = it.mapper()
            }
        }
    }
}

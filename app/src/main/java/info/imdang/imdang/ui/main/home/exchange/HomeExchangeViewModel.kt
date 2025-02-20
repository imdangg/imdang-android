package info.imdang.imdang.ui.main.home.exchange

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.common.MyExchangesParams
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.usecase.coupon.GetCouponUseCase
import info.imdang.domain.usecase.myexchange.GetMyExchangeUseCase
import info.imdang.domain.usecase.myexchange.GetOthersExchangeUseCase
import info.imdang.imdang.base.BaseViewModel
import info.imdang.imdang.common.util.logEvent
import info.imdang.imdang.model.common.PagingState
import info.imdang.imdang.model.coupon.CouponVo
import info.imdang.imdang.model.coupon.mapper
import info.imdang.imdang.model.insight.ExchangeRequestStatus
import info.imdang.imdang.model.insight.mapper
import info.imdang.imdang.ui.main.home.history.ExchangeType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeExchangeViewModel @Inject constructor(
    private val getMyExchangeUseCase: GetMyExchangeUseCase,
    private val getOthersExchangeUseCase: GetOthersExchangeUseCase,
    private val getCouponCountUseCase: GetCouponUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<HomeExchangeEvent>()
    val event = _event.asSharedFlow()

    private val _currentExchangeType = MutableStateFlow(ExchangeType.REQUESTED)
    val currentExchangeType = _currentExchangeType.asStateFlow()

    private val _selectedChipId = MutableStateFlow(1)
    val selectedChipId = _selectedChipId.asStateFlow()

    private val _coupon = MutableStateFlow(CouponVo.init())
    val coupon = _coupon.asStateFlow()

    private val _pagingState = MutableStateFlow(PagingState())
    val pagingState = _pagingState.asStateFlow()

    init {
        fetchMyExchange(ExchangeRequestStatus.PENDING)
        fetchOthersExchange(ExchangeRequestStatus.PENDING)
        fetchCoupon()
    }

    fun onChipClicked(chipId: Int) {
        _selectedChipId.value = chipId

        val event = when (currentExchangeType.value) {
            ExchangeType.REQUESTED -> "내가 요청한 내역(교환상태)"
            ExchangeType.RECEIVED -> "요청 받은 내역(교환상태)"
        }
        val action = when (currentExchangeType.value) {
            ExchangeType.REQUESTED -> "요청한내역_상태_click"
            ExchangeType.RECEIVED -> "요청받은내역_상태_click"
        }
        val status = when (chipId) {
            1 -> {
                logEvent(event = event, category = "홈_교환소", action = action, label = "대기중")
                ExchangeRequestStatus.PENDING
            }
            2 -> {
                logEvent(event = event, category = "홈_교환소", action = action, label = "거절")
                ExchangeRequestStatus.REJECTED
            }
            3 -> {
                logEvent(event = event, category = "홈_교환소", action = action, label = "교환완료")
                ExchangeRequestStatus.ACCEPTED
            }
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
            getMyExchangeUseCase(
                MyExchangesParams(
                    exchangeRequestStatus = exchangeRequestStatus.name,
                    pagingParams = PagingParams(
                        totalCountListener = {
                            updatePagingState(itemCount = it)
                        }
                    )
                )
            )
                ?.cachedIn(this)
                ?.collect {
                    _event.emit(HomeExchangeEvent.UpdateMyExchanges(it.map(InsightDto::mapper)))
                }
        }
    }

    private fun fetchOthersExchange(exchangeRequestStatus: ExchangeRequestStatus) {
        viewModelScope.launch {
            getOthersExchangeUseCase(
                MyExchangesParams(
                    exchangeRequestStatus = exchangeRequestStatus.name,
                    pagingParams = PagingParams(
                        totalCountListener = {
                            updatePagingState(itemCount = it)
                        }
                    )
                )
            )
                ?.cachedIn(this)
                ?.collect {
                    _event.emit(HomeExchangeEvent.UpdateOthersExchanges(it.map(InsightDto::mapper)))
                }
        }
    }

    private fun fetchCoupon() {
        viewModelScope.launch {
            getCouponCountUseCase(Unit)?.let {
                _coupon.value = it.mapper()
            }
        }
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

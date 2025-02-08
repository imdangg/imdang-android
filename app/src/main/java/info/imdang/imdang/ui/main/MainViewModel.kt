package info.imdang.imdang.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.usecase.auth.GetMemberIdUseCase
import info.imdang.domain.usecase.coupon.IssueCouponParams
import info.imdang.domain.usecase.coupon.IssueCouponUseCase
import info.imdang.data.constant.ErrorCode
import info.imdang.data.constant.ErrorMessage
import info.imdang.data.model.response.common.ErrorResponse
import info.imdang.domain.usecase.auth.RemoveTokenUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getMemberIdUseCase: GetMemberIdUseCase,
    private val issueCouponUseCase: IssueCouponUseCase,
    private val networkErrorResponse: SharedFlow<ErrorResponse>,
    private val removeTokenUseCase: RemoveTokenUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<MainEvent>()
    val event = _event.asSharedFlow()

    private val memberId = getMemberIdUseCase()

    private val _isShowTooltip = MutableStateFlow(false)
    val isShowTooltip = _isShowTooltip.asStateFlow()

    init {
        viewModelScope.launch {
            networkErrorResponse.collect {
                when (ErrorCode.fromString(it.code)) {
                    ErrorCode.J001,
                    ErrorCode.J002,
                    ErrorCode.J003,
                    ErrorCode.J004,
                    ErrorCode.J005,
                    ErrorCode.J006,
                    ErrorCode.J007 -> {
                        removeTokenUseCase(Unit)
                        _event.emit(MainEvent.Logout)
                    }
                    else -> {
                    }
                }
                when (val errorMessage = ErrorMessage.fromString(it.message)) {
                    ErrorMessage.EXCHANGE_REQUIRED,
                    ErrorMessage.ALREADY_ACCUSED -> {
                        emitEvent(
                            MainEvent.ShowAlert(errorMessage.message, errorMessage.subMessage)
                        )
                    }
                    ErrorMessage.INVALID_TOKEN -> {
                        removeTokenUseCase(Unit)
                        _event.emit(MainEvent.Logout)
                    }
                    else -> emitEvent(MainEvent.ShowToast(it.message))
                }
            }
        }
    }

    fun showTooltip() {
        viewModelScope.launch {
            val welcomeCouponId = "f79a5af2-8726-4f93-b3cb-f3432d252cd1"
            issueCouponUseCase(
                IssueCouponParams(
                    memberId = memberId,
                    couponId = welcomeCouponId
                )
            )?.let {
                _isShowTooltip.value = true
            }
        }
    }

    fun hideTooltip() {
        _isShowTooltip.value = false
    }

    fun emitEvent(event: MainEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }
}

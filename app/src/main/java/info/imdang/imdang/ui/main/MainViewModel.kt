package info.imdang.imdang.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.usecase.auth.GetMemberIdUseCase
import info.imdang.domain.usecase.coupon.IssueCouponParams
import info.imdang.domain.usecase.coupon.IssueCouponUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getMemberIdUseCase: GetMemberIdUseCase,
    private val issueCouponUseCase: IssueCouponUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<MainEvent>()
    val event = _event.asSharedFlow()

    private val memberId = getMemberIdUseCase()

    private val _isShowTooltip = MutableStateFlow(false)
    val isShowTooltip = _isShowTooltip.asStateFlow()

    fun showTooltip() {
        viewModelScope.launch {
            val welcomeCouponId = "4079f098-572c-4399-9728-a1e1e7f29d58"
            issueCouponUseCase(
                IssueCouponParams(
                    memberId = memberId,
                    couponId = welcomeCouponId
                )
            )
            _isShowTooltip.value = true
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

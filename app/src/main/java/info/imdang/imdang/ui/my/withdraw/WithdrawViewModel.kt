package info.imdang.imdang.ui.my.withdraw

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.usecase.auth.GetLoginTypeUseCase
import info.imdang.imdang.base.BaseViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WithdrawViewModel @Inject constructor(
    getLoginTypeUseCase: GetLoginTypeUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<WithdrawEvent>()
    val event = _event.asSharedFlow()

    private val _isAgreeSelected = MutableStateFlow(false)
    val isAgreeSelected = _isAgreeSelected.asStateFlow()

    val loginType = getLoginTypeUseCase()

    fun onClickAgree() {
        _isAgreeSelected.value = !isAgreeSelected.value
    }

    fun onClickWithdraw() {
        if (isAgreeSelected.value) {
            viewModelScope.launch {
                _event.emit(WithdrawEvent.Withdraw)
            }
        }
    }
}

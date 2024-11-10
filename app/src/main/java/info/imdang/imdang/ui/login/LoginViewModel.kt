package info.imdang.imdang.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    fun onClickKakaoLoginButton() {
        viewModelScope.launch {
            _event.emit(LoginEvent.ShowOnboardingBottomSheet)
        }
    }

    fun onClickGoogleLoginButton() {
        viewModelScope.launch {
            _event.emit(LoginEvent.ShowOnboardingBottomSheet)
        }
    }
}

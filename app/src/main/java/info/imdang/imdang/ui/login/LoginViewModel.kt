package info.imdang.imdang.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.usecase.auth.GoogleLoginUseCase
import info.imdang.domain.usecase.auth.KakaoLoginUseCase
import info.imdang.domain.usecase.google.GetGoogleAccessTokenUseCase
import info.imdang.imdang.model.auth.mapper
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val googleLoginUseCase: GoogleLoginUseCase,
    private val getGoogleAccessTokenUseCase: GetGoogleAccessTokenUseCase
) : ViewModel() {

    private val _event = MutableSharedFlow<LoginEvent>()
    val event = _event.asSharedFlow()

    private val showToast: (e: Exception) -> Unit = {
        viewModelScope.launch { _event.emit(LoginEvent.ShowToast(it.toString())) }
    }

    fun kakaoLogin(token: String) {
        viewModelScope.launch {
            kakaoLoginUseCase(token, showToast)?.mapper()?.let {
                _event.emit(
                    if (it.isJoined) {
                        LoginEvent.MoveMainActivity
                    } else {
                        LoginEvent.MoveOnboardingActivity
                    }
                )
            }
        }
    }

    fun googleLogin(token: String) {
        viewModelScope.launch {
            googleLoginUseCase(token, showToast)?.mapper()?.let {
                _event.emit(
                    if (it.isJoined) {
                        LoginEvent.MoveMainActivity
                    } else {
                        LoginEvent.MoveOnboardingActivity
                    }
                )
            }
        }
    }

    fun getGoogleAccessToken(
        authCode: String,
        onSuccess: (String) -> Unit,
        onError: (Exception) -> Unit
    ) {
        viewModelScope.launch {
            getGoogleAccessTokenUseCase(authCode, onError)?.let {
                onSuccess(it.accessToken)
            }
        }
    }
}

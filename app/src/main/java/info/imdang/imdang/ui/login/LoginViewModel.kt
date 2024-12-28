package info.imdang.imdang.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.usecase.auth.GoogleLoginUseCase
import info.imdang.domain.usecase.auth.KakaoLoginUseCase
import info.imdang.domain.usecase.google.GetGoogleAccessTokenUseCase
import info.imdang.imdang.model.auth.LoginVo
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

    suspend fun kakaoLogin(token: String): LoginVo? {
        return kakaoLoginUseCase(token, showToast)?.mapper()
    }

    suspend fun googleLogin(token: String): LoginVo? {
        return googleLoginUseCase(token, showToast)?.mapper()
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

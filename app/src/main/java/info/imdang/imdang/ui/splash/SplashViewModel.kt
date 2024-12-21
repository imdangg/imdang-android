package info.imdang.imdang.ui.splash

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.usecase.auth.GetTokenUseCase
import info.imdang.imdang.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getTokenUseCase: GetTokenUseCase
) : BaseViewModel() {

    private val _event = MutableSharedFlow<SplashEvent>()
    val event = _event.asSharedFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            val accessToken = getTokenUseCase(Unit)
            _event.emit(
                if (accessToken.isNullOrBlank()) {
                    SplashEvent.MoveLoginActivity
                } else {
                    SplashEvent.MoveMainActivity
                }
            )
        }
    }
}

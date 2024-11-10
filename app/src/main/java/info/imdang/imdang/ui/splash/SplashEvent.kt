package info.imdang.imdang.ui.splash

sealed class SplashEvent {

    data object MoveLoginActivity : SplashEvent()

    data object MoveMainActivity : SplashEvent()
}

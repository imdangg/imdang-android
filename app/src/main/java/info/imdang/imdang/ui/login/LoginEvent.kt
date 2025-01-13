package info.imdang.imdang.ui.login

sealed class LoginEvent {

    data class ShowToast(val message: String) : LoginEvent()

    data object MoveMainActivity : LoginEvent()

    data object MoveOnboardingActivity : LoginEvent()
}

package info.imdang.imdang.ui.login

sealed class LoginEvent {

    data object ShowOnboardingBottomSheet : LoginEvent()
}

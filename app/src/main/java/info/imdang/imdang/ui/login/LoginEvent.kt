package info.imdang.imdang.ui.login

sealed class LoginEvent {

    data class ShowToast(val message: String) : LoginEvent()
}

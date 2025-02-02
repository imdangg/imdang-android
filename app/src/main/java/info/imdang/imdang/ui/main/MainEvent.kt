package info.imdang.imdang.ui.main

sealed class MainEvent {

    data class ShowToast(val message: String) : MainEvent()

    data class ShowAlert(val message: String, val subMessage: String? = null) : MainEvent()

    data object MoveStorage : MainEvent()

    data object Logout : MainEvent()
}

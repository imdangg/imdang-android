package info.imdang.imdang.ui.main.home

sealed class HomeEvent {

    data object Logout : HomeEvent()
}
package info.imdang.imdang.ui.main.home.notification

sealed class NotificationEvent {

    data object MoveInsightDetailActivity : NotificationEvent()

    data object MoveStorage : NotificationEvent()
}

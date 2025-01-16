package info.imdang.imdang.model.notification

sealed class NotificationItem {

    data class Title(val title: String) : NotificationItem()

    data class Notification(val notification: NotificationVo) : NotificationItem()
}

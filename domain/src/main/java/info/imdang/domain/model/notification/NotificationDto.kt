package info.imdang.domain.model.notification

data class NotificationDto(
    val notificationId: Long,
    val category: String,
    val message: String,
    val createdAt: String
)

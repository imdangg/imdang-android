package info.imdang.data.model.response.notification

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.notification.NotificationDto

data class NotificationResponse(
    val notificationId: String,
    val category: String,
    val message: String
) : DataToDomainMapper<NotificationDto> {
    override fun mapper(): NotificationDto = NotificationDto(
        notificationId = notificationId,
        category = category,
        message = message
    )
}

package info.imdang.domain.repository

import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.notification.NotificationDto

interface NotificationRepository {

    suspend fun hasNewNotification(): Boolean

    suspend fun getNotifications(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<NotificationDto>
}

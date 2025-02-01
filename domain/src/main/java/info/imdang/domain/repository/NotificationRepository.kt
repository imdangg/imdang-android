package info.imdang.domain.repository

import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.notification.NotificationDto

interface NotificationRepository {

    suspend fun getNotifications(
        checked: String,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<NotificationDto>
}

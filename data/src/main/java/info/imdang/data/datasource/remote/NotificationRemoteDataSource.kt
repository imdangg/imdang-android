package info.imdang.data.datasource.remote

import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.notification.NotificationResponse
import info.imdang.domain.model.notification.NotificationDto

interface NotificationRemoteDataSource {

    suspend fun hasNewNotification(): Boolean

    suspend fun getNotifications(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<NotificationResponse, NotificationDto>
}

package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.NotificationRemoteDataSource
import info.imdang.data.model.response.common.PagingResponse
import info.imdang.data.model.response.notification.NotificationResponse
import info.imdang.domain.model.notification.NotificationDto
import info.imdang.remote.service.NotificationService
import javax.inject.Inject

internal class NotificationRemoteDataSourceImpl @Inject constructor(
    private val notificationService: NotificationService
) : NotificationRemoteDataSource {

    override suspend fun hasNewNotification(): Boolean = notificationService.hasNewNotification()

    override suspend fun getNotifications(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingResponse<NotificationResponse, NotificationDto> = notificationService.getNotifications(
        page = page,
        size = size,
        direction = direction,
        properties = properties
    )
}

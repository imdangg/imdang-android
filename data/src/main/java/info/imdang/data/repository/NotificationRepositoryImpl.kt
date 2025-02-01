package info.imdang.data.repository

import info.imdang.data.datasource.remote.NotificationRemoteDataSource
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.notification.NotificationDto
import info.imdang.domain.repository.NotificationRepository
import javax.inject.Inject

internal class NotificationRepositoryImpl @Inject constructor(
    private val notificationRemoteDataSource: NotificationRemoteDataSource
) : NotificationRepository {

    override suspend fun getNotifications(
        checked: String,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<NotificationDto> = notificationRemoteDataSource.getNotifications(
        checked = checked,
        page = page,
        size = size,
        direction = direction,
        properties = properties
    ).mapper()
}

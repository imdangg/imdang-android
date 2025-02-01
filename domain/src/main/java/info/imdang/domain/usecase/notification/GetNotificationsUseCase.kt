package info.imdang.domain.usecase.notification

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.notification.NotificationDto
import info.imdang.domain.repository.NotificationRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetNotificationsUseCase @Inject constructor(
    private val notificationRepository: NotificationRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetNotificationsParams, PagingDto<NotificationDto>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: GetNotificationsParams
    ): PagingDto<NotificationDto> = notificationRepository.getNotifications(
        checked = if (parameters.isChecked) "checked" else "unchecked",
        page = parameters.pagingParams.page - 1,
        size = parameters.pagingParams.size,
        direction = parameters.pagingParams.direction,
        properties = parameters.pagingParams.properties
    )
}

data class GetNotificationsParams(
    val isChecked: Boolean,
    val pagingParams: PagingParams
)

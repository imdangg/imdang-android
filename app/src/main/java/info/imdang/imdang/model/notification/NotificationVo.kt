package info.imdang.imdang.model.notification

import info.imdang.domain.model.notification.NotificationDto

data class NotificationVo(
    val notificationId: String,
    val category: NotificationCategory,
    val message: String
) {
    companion object {
        fun getNewSamples(): List<NotificationVo> {
            val samples = mutableListOf<NotificationVo>()
            samples.add(
                NotificationVo(
                    notificationId = "1",
                    category = NotificationCategory.ACCEPTED,
                    message = "길동님이 인사이트 교환을 수락했어요.\n교환한 인사이트를 보관함에서 확인해보세요."
                )
            )
            samples.add(
                NotificationVo(
                    notificationId = "2",
                    category = NotificationCategory.REJECTED,
                    message = "민지님이 인사이트 교환을 거절했어요."
                )
            )
            samples.add(
                NotificationVo(
                    notificationId = "3",
                    category = NotificationCategory.REQUESTED,
                    message = "민호님이 인사이트 교환을 요청했어요.\n인사이트 확인 후 수락 및 거절을 선택해주세요."
                )
            )
            return samples
        }

        fun getLastSamples(): List<NotificationVo> {
            val samples = mutableListOf<NotificationVo>()
            samples.add(
                NotificationVo(
                    notificationId = "4",
                    category = NotificationCategory.ACCEPTED,
                    message = "길동님이 인사이트 교환을 수락했어요.\n교환한 인사이트를 보관함에서 확인해보세요."
                )
            )
            samples.add(
                NotificationVo(
                    notificationId = "5",
                    category = NotificationCategory.ACCEPTED,
                    message = "길동님이 인사이트 교환을 수락했어요.\n교환한 인사이트를 보관함에서 확인해보세요."
                )
            )
            samples.add(
                NotificationVo(
                    notificationId = "6",
                    category = NotificationCategory.REJECTED,
                    message = "민지님이 인사이트 교환을 거절했어요."
                )
            )
            samples.add(
                NotificationVo(
                    notificationId = "7",
                    category = NotificationCategory.REQUESTED,
                    message = "민호님이 인사이트 교환을 요청했어요.\n인사이트 확인 후 수락 및 거절을 선택해주세요."
                )
            )
            return samples
        }
    }
}

fun NotificationDto.mapper(): NotificationVo = NotificationVo(
    notificationId = notificationId,
    category = NotificationCategory.fromString(category),
    message = message
)

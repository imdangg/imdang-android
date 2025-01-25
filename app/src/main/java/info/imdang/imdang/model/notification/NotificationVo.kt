package info.imdang.imdang.model.notification

data class NotificationVo(
    val type: NotificationType,
    val dateTime: String,
    val message: String
) {
    companion object {
        fun getNewSamples(): List<NotificationVo> {
            val samples = mutableListOf<NotificationVo>()
            samples.add(
                NotificationVo(
                    type = NotificationType.EXCHANGE_ACCEPT,
                    message = "길동님이 인사이트 교환을 수락했어요.\n교환한 인사이트를 보관함에서 확인해보세요.",
                    dateTime = "2025.01.17 00:17:00"
                )
            )
            samples.add(
                NotificationVo(
                    type = NotificationType.EXCHANGE_REJECT,
                    message = "민지님이 인사이트 교환을 거절했어요.",
                    dateTime = "2025.01.17 00:16:00"
                )
            )
            samples.add(
                NotificationVo(
                    type = NotificationType.EXCHANGE_REJECT,
                    message = "민지님이 인사이트 교환을 거절했어요.",
                    dateTime = "2025.01.17 00:16:00"
                )
            )
            samples.add(
                NotificationVo(
                    type = NotificationType.EXCHANGE_ACCEPT,
                    message = "길동님이 인사이트 교환을 수락했어요.\n교환한 인사이트를 보관함에서 확인해보세요.",
                    dateTime = "2025.01.17 00:17:00"
                )
            )
            return samples
        }

        fun getLastSamples(): List<NotificationVo> {
            val samples = mutableListOf<NotificationVo>()
            samples.add(
                NotificationVo(
                    type = NotificationType.EXCHANGE_REQUESTED,
                    message = "민호님이 인사이트 교환을 요청했어요.\n인사이트 확인 후 수락 및 거절을 선택해주세요.",
                    dateTime = "2024.12.16 11:16:00"
                )
            )
            samples.add(
                NotificationVo(
                    type = NotificationType.EXCHANGE_REQUESTED,
                    message = "민호님이 인사이트 교환을 요청했어요.\n인사이트 확인 후 수락 및 거절을 선택해주세요.",
                    dateTime = "2024.12.16 11:16:00"
                )
            )
            samples.add(
                NotificationVo(
                    type = NotificationType.EXCHANGE_REQUESTED,
                    message = "민호님이 인사이트 교환을 요청했어요.\n인사이트 확인 후 수락 및 거절을 선택해주세요.",
                    dateTime = "2024.12.16 11:16:00"
                )
            )
            return samples
        }
    }
}

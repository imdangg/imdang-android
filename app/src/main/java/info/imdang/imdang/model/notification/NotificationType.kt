package info.imdang.imdang.model.notification

enum class NotificationType(val actionMessage: String) {

    EXCHANGE_REQUESTED(
        actionMessage = "인사이트 확인하기"
    ),
    EXCHANGE_ACCEPT(
        actionMessage = "보관함 확인하기"
    ),
    EXCHANGE_REJECT(
        actionMessage = "다시 요청하기"
    )
}

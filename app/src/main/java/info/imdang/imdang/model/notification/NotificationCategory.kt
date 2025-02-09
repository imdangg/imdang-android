package info.imdang.imdang.model.notification

enum class NotificationCategory(val actionMessage: String, val actionMessage2: String? = null) {

    REQUESTED(actionMessage = "거절하기", actionMessage2 = "수락하기"),
    ACCEPTED(actionMessage = "보관함 확인하기"),
    REJECTED(actionMessage = "다시 요청하기");

    companion object {
        fun fromString(category: String): NotificationCategory = entries.first {
            it.name == category
        }
    }
}

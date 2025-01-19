package info.imdang.imdang.model.insight

enum class InsightDetailStatus(val text: String) {
    EXCHANGE_REQUEST(text = "교환 요청"),
    EXCHANGE_REQUESTED(text = ""),
    EXCHANGE_WAITING(text = "대기중"),
    EXCHANGE_COMPLETE(text = "교환 완료"),
    MY_INSIGHT("")
}

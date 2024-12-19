package info.imdang.imdang.model.insight

enum class InsightDetailState(val text: String) {
    ExchangeRequest(text = "교환 요청"),
    ExchangeRequested(text = ""),
    ExchangeWaiting(text = "대기중"),
    ExchangeComplete(text = "교환 완료")
}

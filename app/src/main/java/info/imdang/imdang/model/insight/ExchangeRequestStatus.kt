package info.imdang.imdang.model.insight

enum class ExchangeRequestStatus {
    PENDING, // 내 인사이트인 경우 대기중, 다른 사람의 인사이트인 경우 교환 요청 받은 상태
    REJECTED, // 거절, 앱에선 사용하지 않음
    ACCEPTED; // 수락, 교환 완료 상태

    companion object {
        fun fromString(status: String?): ExchangeRequestStatus? = entries.firstOrNull {
            it.name == status
        }
    }
}

fun ExchangeRequestStatus?.toInsightDetailStatus(
    isMyInsight: Boolean
): InsightDetailStatus = when (this) {
    ExchangeRequestStatus.PENDING -> if (isMyInsight) {
        InsightDetailStatus.EXCHANGE_WAITING
    } else {
        InsightDetailStatus.EXCHANGE_REQUESTED
    }
    ExchangeRequestStatus.ACCEPTED -> InsightDetailStatus.EXCHANGE_COMPLETE
    else -> {
        if (isMyInsight) {
            InsightDetailStatus.MY_INSIGHT
        } else {
            InsightDetailStatus.EXCHANGE_REQUEST
        }
    }
}

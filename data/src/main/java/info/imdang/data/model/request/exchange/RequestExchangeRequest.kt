package info.imdang.data.model.request.exchange

data class RequestExchangeRequest(
    val requestedInsightId: String,
    val requestMemberId: String?,
    val requestMemberInsightId: String?,
    val memberCouponId: Long?
)

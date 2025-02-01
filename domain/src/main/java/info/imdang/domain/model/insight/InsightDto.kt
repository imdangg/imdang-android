package info.imdang.domain.model.insight

import info.imdang.domain.model.common.AddressDto

data class InsightDto(
    val insightId: String,
    val recommendedCount: Int?,
    val address: AddressDto,
    val title: String,
    val mainImage: String?,
    val memberId: String?,
    val memberNickname: String?
)

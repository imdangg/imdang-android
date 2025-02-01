package info.imdang.data.model.response.insight

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.data.model.response.common.AddressResponse
import info.imdang.domain.model.insight.InsightDto

data class InsightResponse(
    val insightId: String,
    val recommendedCount: Int?,
    val address: AddressResponse,
    val title: String,
    val mainImage: String?,
    val memberId: String?,
    val memberNickname: String?
) : DataToDomainMapper<InsightDto> {
    override fun mapper(): InsightDto = InsightDto(
        insightId = insightId,
        recommendedCount = recommendedCount,
        address = address.mapper(),
        title = title,
        mainImage = mainImage,
        memberId = memberId,
        memberNickname = memberNickname
    )
}

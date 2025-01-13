package info.imdang.data.model.response.insight

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.insight.InsightIdDto

data class InsightIdResponse(
    val insightId: String
) : DataToDomainMapper<InsightIdDto> {
    override fun mapper(): InsightIdDto = InsightIdDto(insightId = insightId)
}

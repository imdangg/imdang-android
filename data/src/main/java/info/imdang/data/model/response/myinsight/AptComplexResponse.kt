package info.imdang.data.model.response.myinsight

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.myinsight.AptComplexDto

data class AptComplexResponse(
    val apartmentComplexName: String,
    val insightCount: Int?
) : DataToDomainMapper<AptComplexDto> {
    override fun mapper(): AptComplexDto = AptComplexDto(
        apartmentComplexName = apartmentComplexName,
        insightCount = insightCount
    )
}

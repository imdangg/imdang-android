package info.imdang.data.model.response.aptcomplex

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.aptcomplex.VisitedAptComplexDto

data class VisitedAptComplexResponse(
    val name: String
) : DataToDomainMapper<VisitedAptComplexDto> {
    override fun mapper(): VisitedAptComplexDto = VisitedAptComplexDto(
        name = name
    )
}

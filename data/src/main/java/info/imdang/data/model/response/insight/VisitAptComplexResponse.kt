package info.imdang.data.model.response.insight

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.aptcomplex.VisitAptComplexDto

data class VisitAptComplexResponse(
    val name: String
) : DataToDomainMapper<VisitAptComplexDto> {
    override fun mapper(): VisitAptComplexDto = VisitAptComplexDto(
        name = name
    )
}

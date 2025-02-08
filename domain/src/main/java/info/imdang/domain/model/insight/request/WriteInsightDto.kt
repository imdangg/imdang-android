package info.imdang.domain.model.insight.request

import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.insight.ApartmentComplexDto
import info.imdang.domain.model.insight.ComplexEnvironmentDto
import info.imdang.domain.model.insight.ComplexFacilityDto
import info.imdang.domain.model.insight.FavorableNewsDto
import info.imdang.domain.model.insight.InfraDto

data class WriteInsightDto(
    val insightId: String? = null,
    val score: Int,
    val title: String,
    val address: AddressDto,
    val apartmentComplex: ApartmentComplexDto,
    val visitAt: String,
    val visitTimes: List<String>,
    val visitMethods: List<String>,
    val access: String,
    val summary: String,
    val infra: InfraDto,
    val complexEnvironment: ComplexEnvironmentDto,
    val complexFacility: ComplexFacilityDto,
    val favorableNews: FavorableNewsDto
)

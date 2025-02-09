package info.imdang.domain.model.insight

import info.imdang.domain.model.common.AddressDto

data class InsightDetailDto(
    val memberId: String,
    val memberNickname: String,
    val insightId: String,
    val snapshotId: Long?,
    val mainImage: String?,
    val title: String,
    val address: AddressDto,
    val apartmentComplex: ApartmentComplexDto,
    val visitAt: String,
    val visitTimes: List<String>,
    val visitMethods: List<String>,
    val access: String,
    val summary: String,
    val infra: InfraDto?,
    val complexEnvironment: ComplexEnvironmentDto?,
    val complexFacility: ComplexFacilityDto?,
    val favorableNews: FavorableNewsDto?,
    val recommended: Boolean,
    val accused: Boolean,
    val recommendedCount: Int,
    val accusedCount: Int?,
    val viewCount: Int?,
    val score: Int,
    val createdAt: String?,
    val createdByMe: Boolean?,
    val exchangeRequestStatus: String?,
    val exchangeRequestCreatedByMe: Boolean?,
    val exchangeRequestId: String?
)

data class ApartmentComplexDto(
    val name: String
)

data class InfraDto(
    val transportations: List<String>,
    val schoolDistricts: List<String>,
    val amenities: List<String>,
    val facilities: List<String>,
    val surroundings: List<String>,
    val landmarks: List<String>,
    val unpleasantFacilities: List<String>,
    val text: String
)

data class ComplexEnvironmentDto(
    val buildingCondition: String,
    val security: String,
    val childrenFacility: String,
    val seniorFacility: String,
    val text: String
)

data class ComplexFacilityDto(
    val familyFacilities: List<String>,
    val multipurposeFacilities: List<String>,
    val leisureFacilities: List<String>,
    val surroundings: List<String>,
    val text: String
)

data class FavorableNewsDto(
    val transportations: List<String>,
    val developments: List<String>,
    val educations: List<String>,
    val environments: List<String>,
    val cultures: List<String>,
    val industries: List<String>,
    val policies: List<String>,
    val text: String
)

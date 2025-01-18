package info.imdang.domain.model.insight.request

data class WriteInsightDto(
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

data class AddressDto(
    val siDo: String,
    val siGunGu: String,
    val eupMyeonDong: String,
    val roadName: String,
    val buildingNumber: String,
    val detail: String
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

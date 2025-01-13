package info.imdang.domain.model.insight.request

data class WriteInsightDto(
    val score: Int,
    val address: AddressDto,
    val apartmentComplex: ApartmentComplexDto,
    val title: String,
    val contents: String,
    val mainImage: String,
    val summary: String,
    val visitAt: String,
    val visitMethod: String,
    val access: String,
    val infra: InfraDto,
    val complexEnvironment: ComplexEnvironmentDto,
    val complexFacility: ComplexFacilityDto,
    val favorableNews: FavorableNewsDto
)

data class AddressDto(
    val siGunGu: String,
    val dong: String
)

data class ApartmentComplexDto(
    val name: String,
    val key: String
)

data class InfraDto(
    val transportation: MultiChoiceDto,
    val schoolDistrict: MultiChoiceDto,
    val amenity: MultiChoiceDto,
    val facility: MultiChoiceDto,
    val surroundings: MultiChoiceDto,
    val landmark: MultiChoiceDto,
    val unpleasantFacility: MultiChoiceDto
)

data class ComplexEnvironmentDto(
    val buildingCondition: SingleChoiceDto,
    val security: SingleChoiceDto,
    val childrenFacility: SingleChoiceDto,
    val seniorFacility: SingleChoiceDto
)

data class ComplexFacilityDto(
    val family: MultiChoiceDto,
    val multipurpose: MultiChoiceDto,
    val leisure: MultiChoiceDto,
    val surroundings: MultiChoiceDto
)

data class FavorableNewsDto(
    val transportation: MultiChoiceDto,
    val development: MultiChoiceDto,
    val education: MultiChoiceDto,
    val environment: MultiChoiceDto,
    val culture: MultiChoiceDto,
    val industry: MultiChoiceDto,
    val policy: MultiChoiceDto
)

data class MultiChoiceDto(
    val choice: List<String>,
    val text: String
)

data class SingleChoiceDto(
    val choice: String,
    val text: String
)

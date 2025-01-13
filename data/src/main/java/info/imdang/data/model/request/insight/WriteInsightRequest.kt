package info.imdang.data.model.request.insight

import info.imdang.domain.model.insight.request.AddressDto
import info.imdang.domain.model.insight.request.ApartmentComplexDto
import info.imdang.domain.model.insight.request.ComplexEnvironmentDto
import info.imdang.domain.model.insight.request.ComplexFacilityDto
import info.imdang.domain.model.insight.request.FavorableNewsDto
import info.imdang.domain.model.insight.request.InfraDto
import info.imdang.domain.model.insight.request.MultiChoiceDto
import info.imdang.domain.model.insight.request.SingleChoiceDto
import info.imdang.domain.model.insight.request.WriteInsightDto

data class WriteInsightRequest(
    val score: Int,
    val address: AddressRequest,
    val apartmentComplex: ApartmentComplexRequest,
    val title: String,
    val contents: String,
    val mainImage: String,
    val summary: String,
    val visitAt: String,
    val visitMethod: String,
    val access: String,
    val infra: InfraRequest,
    val complexEnvironment: ComplexEnvironmentRequest,
    val complexFacility: ComplexFacilityRequest,
    val favorableNews: FavorableNewsRequest
) {
    companion object {
        fun fromDto(dto: WriteInsightDto) = WriteInsightRequest(
            score = dto.score,
            address = dto.address.toRequest(),
            apartmentComplex = dto.apartmentComplex.toRequest(),
            title = dto.title,
            contents = dto.contents,
            mainImage = dto.mainImage,
            summary = dto.summary,
            visitAt = dto.visitAt,
            visitMethod = dto.visitMethod,
            access = dto.access,
            infra = dto.infra.toRequest(),
            complexEnvironment = dto.complexEnvironment.toRequest(),
            complexFacility = dto.complexFacility.toRequest(),
            favorableNews = dto.favorableNews.toRequest()
        )

        private fun AddressDto.toRequest() = AddressRequest(
            siGunGu = siGunGu,
            dong = dong
        )

        private fun ApartmentComplexDto.toRequest() = ApartmentComplexRequest(
            name = name,
            key = key
        )

        private fun InfraDto.toRequest() = InfraRequest(
            transportation = transportation.toRequest(),
            schoolDistrict = schoolDistrict.toRequest(),
            amenity = amenity.toRequest(),
            facility = facility.toRequest(),
            surroundings = surroundings.toRequest(),
            landmark = landmark.toRequest(),
            unpleasantFacility = unpleasantFacility.toRequest()
        )

        private fun ComplexEnvironmentDto.toRequest() = ComplexEnvironmentRequest(
            buildingCondition = buildingCondition.toRequest(),
            security = security.toRequest(),
            childrenFacility = childrenFacility.toRequest(),
            seniorFacility = seniorFacility.toRequest()
        )

        private fun ComplexFacilityDto.toRequest() = ComplexFacilityRequest(
            family = family.toRequest(),
            multipurpose = multipurpose.toRequest(),
            leisure = leisure.toRequest(),
            surroundings = surroundings.toRequest()
        )

        private fun FavorableNewsDto.toRequest() = FavorableNewsRequest(
            transportation = transportation.toRequest(),
            development = development.toRequest(),
            education = education.toRequest(),
            environment = environment.toRequest(),
            culture = culture.toRequest(),
            industry = industry.toRequest(),
            policy = policy.toRequest()
        )

        private fun MultiChoiceDto.toRequest() = MultiChoiceRequest(
            choice = choice,
            text = text
        )

        private fun SingleChoiceDto.toRequest() = SingleChoiceRequest(
            choice = choice,
            text = text
        )
    }
}

data class AddressRequest(
    val siGunGu: String,
    val dong: String
)

data class ApartmentComplexRequest(
    val name: String,
    val key: String
)

data class InfraRequest(
    val transportation: MultiChoiceRequest,
    val schoolDistrict: MultiChoiceRequest,
    val amenity: MultiChoiceRequest,
    val facility: MultiChoiceRequest,
    val surroundings: MultiChoiceRequest,
    val landmark: MultiChoiceRequest,
    val unpleasantFacility: MultiChoiceRequest
)

data class ComplexEnvironmentRequest(
    val buildingCondition: SingleChoiceRequest,
    val security: SingleChoiceRequest,
    val childrenFacility: SingleChoiceRequest,
    val seniorFacility: SingleChoiceRequest
)

data class ComplexFacilityRequest(
    val family: MultiChoiceRequest,
    val multipurpose: MultiChoiceRequest,
    val leisure: MultiChoiceRequest,
    val surroundings: MultiChoiceRequest
)

data class FavorableNewsRequest(
    val transportation: MultiChoiceRequest,
    val development: MultiChoiceRequest,
    val education: MultiChoiceRequest,
    val environment: MultiChoiceRequest,
    val culture: MultiChoiceRequest,
    val industry: MultiChoiceRequest,
    val policy: MultiChoiceRequest
)

data class MultiChoiceRequest(
    val choice: List<String>,
    val text: String
)

data class SingleChoiceRequest(
    val choice: String,
    val text: String
)

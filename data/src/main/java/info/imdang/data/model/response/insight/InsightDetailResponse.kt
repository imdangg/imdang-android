package info.imdang.data.model.response.insight

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.data.model.response.common.AddressResponse
import info.imdang.domain.model.insight.ApartmentComplexDto
import info.imdang.domain.model.insight.ComplexEnvironmentDto
import info.imdang.domain.model.insight.ComplexFacilityDto
import info.imdang.domain.model.insight.FavorableNewsDto
import info.imdang.domain.model.insight.InfraDto
import info.imdang.domain.model.insight.InsightDetailDto

data class InsightDetailResponse(
    val memberId: String,
    val memberNickname: String,
    val insightId: String,
    val snapshotId: Long?,
    val mainImage: String?,
    val title: String,
    val address: AddressResponse,
    val apartmentComplex: ApartmentComplexResponse,
    val visitAt: String,
    val visitTimes: List<String>,
    val visitMethods: List<String>,
    val access: String,
    val summary: String,
    val infra: InfraResponse?,
    val complexEnvironment: ComplexEnvironmentResponse?,
    val complexFacility: ComplexFacilityResponse?,
    val favorableNews: FavorableNewsResponse?,
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
) : DataToDomainMapper<InsightDetailDto> {
    override fun mapper(): InsightDetailDto = InsightDetailDto(
        memberId = memberId,
        memberNickname = memberNickname,
        insightId = insightId,
        snapshotId = snapshotId,
        mainImage = mainImage,
        title = title,
        address = address.mapper(),
        apartmentComplex = apartmentComplex.mapper(),
        visitAt = visitAt,
        visitTimes = visitTimes,
        visitMethods = visitMethods,
        access = access,
        summary = summary,
        infra = infra?.mapper(),
        complexEnvironment = complexEnvironment?.mapper(),
        complexFacility = complexFacility?.mapper(),
        favorableNews = favorableNews?.mapper(),
        recommended = recommended,
        accused = accused,
        recommendedCount = recommendedCount,
        accusedCount = accusedCount,
        viewCount = viewCount,
        score = score,
        createdAt = createdAt,
        createdByMe = createdByMe,
        exchangeRequestStatus = exchangeRequestStatus,
        exchangeRequestCreatedByMe = exchangeRequestCreatedByMe,
        exchangeRequestId = exchangeRequestId
    )
}

data class ApartmentComplexResponse(
    val name: String
) : DataToDomainMapper<ApartmentComplexDto> {
    override fun mapper(): ApartmentComplexDto = ApartmentComplexDto(
        name = name
    )
}

data class InfraResponse(
    val transportations: List<String>,
    val schoolDistricts: List<String>,
    val amenities: List<String>,
    val facilities: List<String>,
    val surroundings: List<String>,
    val landmarks: List<String>,
    val unpleasantFacilities: List<String>,
    val text: String
) : DataToDomainMapper<InfraDto> {
    override fun mapper(): InfraDto = InfraDto(
        transportations = transportations,
        schoolDistricts = schoolDistricts,
        amenities = amenities,
        facilities = facilities,
        surroundings = surroundings,
        landmarks = landmarks,
        unpleasantFacilities = unpleasantFacilities,
        text = text
    )
}

data class ComplexEnvironmentResponse(
    val buildingCondition: String,
    val security: String,
    val childrenFacility: String,
    val seniorFacility: String,
    val text: String
) : DataToDomainMapper<ComplexEnvironmentDto> {
    override fun mapper(): ComplexEnvironmentDto = ComplexEnvironmentDto(
        buildingCondition = buildingCondition,
        security = security,
        childrenFacility = childrenFacility,
        seniorFacility = seniorFacility,
        text = text
    )
}

data class ComplexFacilityResponse(
    val familyFacilities: List<String>,
    val multipurposeFacilities: List<String>,
    val leisureFacilities: List<String>,
    val surroundings: List<String>,
    val text: String
) : DataToDomainMapper<ComplexFacilityDto> {
    override fun mapper(): ComplexFacilityDto = ComplexFacilityDto(
        familyFacilities = familyFacilities,
        multipurposeFacilities = multipurposeFacilities,
        leisureFacilities = leisureFacilities,
        surroundings = surroundings,
        text = text
    )
}

data class FavorableNewsResponse(
    val transportations: List<String>,
    val developments: List<String>,
    val educations: List<String>,
    val environments: List<String>,
    val cultures: List<String>,
    val industries: List<String>,
    val policies: List<String>,
    val text: String
) : DataToDomainMapper<FavorableNewsDto> {
    override fun mapper(): FavorableNewsDto = FavorableNewsDto(
        transportations = transportations,
        developments = developments,
        educations = educations,
        environments = environments,
        cultures = cultures,
        industries = industries,
        policies = policies,
        text = text
    )
}

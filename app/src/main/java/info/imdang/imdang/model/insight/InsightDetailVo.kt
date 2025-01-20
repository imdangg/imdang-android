package info.imdang.imdang.model.insight

import android.os.Parcelable
import info.imdang.domain.model.insight.ComplexEnvironmentDto
import info.imdang.domain.model.insight.ComplexFacilityDto
import info.imdang.domain.model.insight.FavorableNewsDto
import info.imdang.domain.model.insight.InfraDto
import info.imdang.domain.model.insight.InsightDetailDto
import info.imdang.imdang.common.util.formatDate
import info.imdang.imdang.model.common.AddressVo
import info.imdang.imdang.model.common.mapper
import kotlinx.parcelize.Parcelize

const val REVIEW = "단지는 전반적으로 관리 상태가 양호했으며, " +
    "주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. " +
    "다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
    "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다."

@Parcelize
data class InsightDetailVo(
    val memberId: String,
    val insightId: String,
    val snapshotId: Long?,
    val mainImage: String,
    val title: String,
    val address: AddressVo,
    val aptComplex: String,
    val visitAt: String,
    val visitTimes: List<String>,
    val visitMethods: List<String>,
    val access: String,
    val summary: String,
    val infra: InfraVo?,
    val complexEnvironment: ComplexEnvironmentVo?,
    val complexFacility: ComplexFacilityVo?,
    val goodNews: GoodNewsVo?,
    val recommendedCount: Int,
    val accusedCount: Int,
    val viewCount: Int,
    val score: Int,
    val createdAt: String,
    val exchangeRequestStatus: ExchangeRequestStatus?,
    val exchangeRequestId: String?,
    val isRecommended: Boolean = false,
    val isReported: Boolean = false
) : Parcelable {

    fun toBasicInfo(): InsightDetailItem.BasicInfo = InsightDetailItem.BasicInfo(
        mainImage = mainImage,
        title = title,
        address = address.siDo +
            " ${address.siGunGu}" +
            " ${address.eupMyeonDong}" +
            " ${address.buildingNumber}" +
            "\n($aptComplex)",
        latitude = address.latitude ?: 0.0,
        longitude = address.longitude ?: 0.0,
        visitAt = visitAt,
        visitTimes = visitTimes.joinToString(", "),
        visitMethods = visitMethods.joinToString(", "),
        access = access,
        summary = summary
    )

    companion object {
        fun getSample() = InsightDetailVo(
            memberId = "memberId",
            insightId = "insightId",
            snapshotId = 0,
            mainImage = "https://www.hyundai.co.kr/image/upload/asset_library/" +
                "MDA00000000000003878/ff36eb6226674c648106fd06ff971e6c.jpg",
            title = "초역세권 대단지 아파트 후기",
            address = AddressVo(
                siDo = "서울",
                siGunGu = "영등포구",
                eupMyeonDong = "당산2동",
                roadName = "",
                buildingNumber = "123-467",
                detail = "",
                latitude = 37.5304831048862,
                longitude = 126.902812773342
            ),
            aptComplex = "당산아이파크1차",
            visitAt = "2024.01.01",
            visitTimes = listOf("저녁"),
            visitMethods = listOf("자차, 도보"),
            access = "자유로움",
            summary = "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. " +
                "다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며, " +
                "주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며, " +
                "주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요.",
            infra = InfraVo(
                traffics = listOf("해당 없음"),
                schools = listOf("초품아", "학원가"),
                lifeFacilities = listOf("주민센터", "편의점"),
                cultureFacilities = listOf("도서관", "영화관", "헬스장"),
                surroundingEnvironments = listOf("산, 공원"),
                landmarks = listOf("놀이공원", "복합 쇼핑몰", "고궁", "전망대", "국립공원", "한옥마을"),
                avoidFacilities = listOf("고속도로", "유흥거리"),
                infraReview = REVIEW
            ),
            complexEnvironment = ComplexEnvironmentVo(
                building = "잘모르겠어요",
                safety = "좋아요",
                childrenFacility = "평범해요",
                silverFacility = "좋아요",
                complexEnvironmentReview = REVIEW
            ),
            complexFacility = ComplexFacilityVo(
                familyFacilities = listOf("어린이집"),
                multipurposeFacilities = listOf("해당 없음"),
                leisureFacilities = listOf(
                    "피트니스 센터",
                    "독서실",
                    "사우나",
                    "목욕탕",
                    "스크린 골프장",
                    "영화관",
                    "도서관",
                    "수영장"
                ),
                environments = listOf("잔디밭"),
                complexFacilityReview = REVIEW
            ),
            goodNews = GoodNewsVo(
                traffics = listOf("잘모르겠어요"),
                developments = listOf("재개발", "재건축", "인근 신도시 개발"),
                educations = listOf("잘모르겠어요"),
                naturalEnvironments = listOf("하천 복원", "호수 복원"),
                cultures = listOf("대형병원", "문화센터", "도서관", "공연장"),
                industries = listOf("산업단지"),
                policies = listOf("투기 과열 지구 해제", "일자리 창출 정책"),
                goodNewsReview = REVIEW
            ),
            recommendedCount = 10,
            accusedCount = 0,
            viewCount = 10,
            score = 100,
            createdAt = "",
            exchangeRequestStatus = null,
            exchangeRequestId = null
        )
    }
}

@Parcelize
data class InfraVo(
    val traffics: List<String>,
    val schools: List<String>,
    val lifeFacilities: List<String>,
    val cultureFacilities: List<String>,
    val surroundingEnvironments: List<String>,
    val landmarks: List<String>,
    val avoidFacilities: List<String>,
    val infraReview: String
) : Parcelable

@Parcelize
data class ComplexEnvironmentVo(
    val building: String,
    val safety: String,
    val childrenFacility: String,
    val silverFacility: String,
    val complexEnvironmentReview: String
) : Parcelable

@Parcelize
data class ComplexFacilityVo(
    val familyFacilities: List<String>,
    val multipurposeFacilities: List<String>,
    val leisureFacilities: List<String>,
    val environments: List<String>,
    val complexFacilityReview: String
) : Parcelable

@Parcelize
data class GoodNewsVo(
    val traffics: List<String>,
    val developments: List<String>,
    val educations: List<String>,
    val naturalEnvironments: List<String>,
    val cultures: List<String>,
    val industries: List<String>,
    val policies: List<String>,
    val goodNewsReview: String
) : Parcelable

fun InsightDetailDto.mapper(): InsightDetailVo = InsightDetailVo(
    memberId = memberId,
    insightId = insightId,
    snapshotId = snapshotId,
    mainImage = mainImage,
    title = title,
    address = address.mapper(),
    aptComplex = apartmentComplex.name,
    visitAt = visitAt.formatDate(fromFormat = "yyyy-MM-dd", toFormat = "yyyy.MM.dd"),
    visitTimes = visitTimes.formatSelectedItems(),
    visitMethods = visitMethods.formatSelectedItems(),
    access = access.replace("_", " "),
    summary = summary,
    infra = infra?.mapper(),
    complexEnvironment = complexEnvironment?.mapper(),
    complexFacility = complexFacility?.mapper(),
    goodNews = favorableNews?.mapper(),
    recommendedCount = recommendedCount,
    accusedCount = accusedCount ?: 0,
    viewCount = viewCount ?: 0,
    score = score,
    createdAt = createdAt,
    exchangeRequestStatus = ExchangeRequestStatus.fromString(exchangeRequestStatus),
    exchangeRequestId = exchangeRequestId
)

fun InfraDto.mapper(): InfraVo = InfraVo(
    traffics = transportations.formatSelectedItems(),
    schools = schoolDistricts.formatSelectedItems(),
    lifeFacilities = amenities.formatSelectedItems(),
    cultureFacilities = facilities.formatSelectedItems(),
    surroundingEnvironments = surroundings.formatSelectedItems(),
    landmarks = landmarks.formatSelectedItems(),
    avoidFacilities = unpleasantFacilities.formatSelectedItems(),
    infraReview = text
)

fun ComplexEnvironmentDto.mapper(): ComplexEnvironmentVo = ComplexEnvironmentVo(
    building = buildingCondition.replace("_", " "),
    safety = security.replace("_", " "),
    childrenFacility = childrenFacility.replace("_", " "),
    silverFacility = seniorFacility.replace("_", " "),
    complexEnvironmentReview = text
)

fun ComplexFacilityDto.mapper(): ComplexFacilityVo = ComplexFacilityVo(
    familyFacilities = familyFacilities.formatSelectedItems(),
    multipurposeFacilities = multipurposeFacilities.formatSelectedItems(),
    leisureFacilities = leisureFacilities.formatSelectedItems(),
    environments = surroundings.formatSelectedItems(),
    complexFacilityReview = text
)

fun FavorableNewsDto.mapper(): GoodNewsVo = GoodNewsVo(
    traffics = transportations.formatSelectedItems(),
    developments = developments.formatSelectedItems(),
    educations = educations.formatSelectedItems(),
    naturalEnvironments = environments.formatSelectedItems(),
    cultures = cultures.formatSelectedItems(),
    industries = industries.formatSelectedItems(),
    policies = policies.formatSelectedItems(),
    goodNewsReview = text
)

private fun List<String>.formatSelectedItems() = map {
    it.replace("_", " ")
}

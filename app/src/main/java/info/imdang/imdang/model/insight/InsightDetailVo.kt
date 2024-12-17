package info.imdang.imdang.model.insight

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

const val REVIEW = "단지는 전반적으로 관리 상태가 양호했으며, " +
    "주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. " +
    "다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. " +
    "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다."

// todo : Sample data, 실제 서버 데이터 나오면 변경 예정
@Parcelize
data class InsightDetailVo(
    val profileImage: String,
    val nickname: String,
    val recommendCount: Int,
    val basicInfo: InsightDetailBasicInfo,
    val infra: InsightDetailInfra,
    val aptEnvironment: InsightDetailAptEnvironment,
    val aptFacility: InsightDetailAptFacility,
    val goodNews: InsightDetailGoodNews
) : Parcelable {
    companion object {
        fun getSample() = InsightDetailVo(
            profileImage = "",
            nickname = "홍길동",
            recommendCount = 24,
            basicInfo = InsightDetailBasicInfo(
                thumbnail = "https://www.hyundai.co.kr/image/upload/asset_library/" +
                    "MDA00000000000003878/ff36eb6226674c648106fd06ff971e6c.jpg",
                title = "초역세권 대단지 아파트 후기",
                address = "서울특별시 영등포구 당산 2동 123-467\n(당산아이파크1차)",
                latitude = 37.5304831048862,
                longitude = 126.902812773342,
                visitDate = "2024.01.01",
                visitTime = listOf("저녁"),
                traffic = listOf("자차, 도보"),
                entranceLimit = "자유로움",
                insightSummary = "단지는 전반적으로 관리 상태가 양호했으며, 주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. " +
                    "다만 주차 공간이 협소하고, 단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며, " +
                    "주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                    "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요. 단지는 전반적으로 관리 상태가 양호했으며, " +
                    "주변에 대형 마트와 초등학교가 가까워 생활 편의성이 뛰어납니다. 다만 주차 공간이 협소하고, " +
                    "단지 내 보안 카메라 설치가 부족한 점이 아쉬워요."
            ),
            infra = InsightDetailInfra(
                traffic = listOf("해당 없음"),
                school = listOf("초품아", "학원가"),
                lifeFacility = listOf("주민센터", "편의점"),
                cultureFacility = listOf("도서관", "영화관", "헬스장"),
                surroundingEnvironment = listOf("산, 공원"),
                landmark = listOf("놀이공원", "복합 쇼핑몰", "고궁", "전망대", "국립공원", "한옥마을"),
                avoidFacility = listOf("고속도로", "유흥거리"),
                infraReview = REVIEW
            ),
            aptEnvironment = InsightDetailAptEnvironment(
                building = "잘모르겠어요",
                safety = "좋아요",
                childrenFacility = "평범해요",
                silverFacility = "좋아요",
                aptEnvironmentReview = REVIEW
            ),
            aptFacility = InsightDetailAptFacility(
                family = listOf("어린이집"),
                multipurpose = listOf("해당 없음"),
                leisure = listOf("피트니스 센터", "독서실", "사우나", "목욕탕", "스크린 골프장", "영화관", "도서관", "수영장"),
                environment = listOf("잔디밭"),
                aptFacilityReview = REVIEW
            ),
            goodNews = InsightDetailGoodNews(
                traffic = listOf("잘모르겠어요"),
                development = listOf("재개발", "재건축", "인근 신도시 개발"),
                education = listOf("잘모르겠어요"),
                naturalEnvironment = listOf("하천 복원", "호수 복원"),
                culture = listOf("대형병원", "문화센터", "도서관", "공연장"),
                industry = listOf("산업단지"),
                policy = listOf("투기 과열 지구 해제", "일자리 창출 정책"),
                goodNewsReview = REVIEW
            )
        )
    }
}

@Parcelize
data class InsightDetailBasicInfo(
    val thumbnail: String,
    val title: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val visitDate: String,
    val visitTime: List<String>,
    val traffic: List<String>,
    val entranceLimit: String,
    val insightSummary: String
) : Parcelable

@Parcelize
data class InsightDetailInfra(
    val traffic: List<String>,
    val school: List<String>,
    val lifeFacility: List<String>,
    val cultureFacility: List<String>,
    val surroundingEnvironment: List<String>,
    val landmark: List<String>,
    val avoidFacility: List<String>,
    val infraReview: String
) : Parcelable

@Parcelize
data class InsightDetailAptEnvironment(
    val building: String,
    val safety: String,
    val childrenFacility: String,
    val silverFacility: String,
    val aptEnvironmentReview: String
) : Parcelable

@Parcelize
data class InsightDetailAptFacility(
    val family: List<String>,
    val multipurpose: List<String>,
    val leisure: List<String>,
    val environment: List<String>,
    val aptFacilityReview: String
) : Parcelable

@Parcelize
data class InsightDetailGoodNews(
    val traffic: List<String>,
    val development: List<String>,
    val education: List<String>,
    val naturalEnvironment: List<String>,
    val culture: List<String>,
    val industry: List<String>,
    val policy: List<String>,
    val goodNewsReview: String
) : Parcelable

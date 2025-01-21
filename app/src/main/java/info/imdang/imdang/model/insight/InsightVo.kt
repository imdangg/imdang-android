package info.imdang.imdang.model.insight

import android.os.Parcelable
import info.imdang.domain.model.insight.InsightDto
import info.imdang.imdang.model.common.AddressVo
import info.imdang.imdang.model.common.mapper
import kotlinx.parcelize.Parcelize

@Parcelize
data class InsightVo(
    val insightId: String,
    val recommendedCount: Int,
    val address: AddressVo,
    val title: String,
    val mainImage: String?,
    val memberId: String?,
    val profileImage: String?,
    val nickname: String
) : Parcelable {
    companion object {
        fun getSamples(size: Int): List<InsightVo> {
            val samples = mutableListOf<InsightVo>()
            repeat(size) {
                samples.add(
                    InsightVo(
                        insightId = "$it",
                        recommendedCount = 24,
                        address = AddressVo.getSample(),
                        title = "초역세권 대단지 아파트 후기",
                        mainImage = "https://www.hyundai.co.kr/image/upload/asset_library/" +
                            "MDA00000000000003878/ff36eb6226674c648106fd06ff971e6c.jpg",
                        memberId = "memberId",
                        profileImage = null,
                        nickname = "홍길동"
                    )
                )
            }
            return samples
        }
    }
}

fun InsightDto.mapper(): InsightVo = InsightVo(
    insightId = insightId,
    recommendedCount = recommendedCount ?: 0,
    address = address.mapper(),
    title = title,
    mainImage = mainImage ?: "",
    memberId = memberId ?: "",
    profileImage = null,
    nickname = "홍길동"
)

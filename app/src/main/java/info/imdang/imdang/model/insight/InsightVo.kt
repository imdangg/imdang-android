package info.imdang.imdang.model.insight

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// todo : Sample data, 실제 서버 데이터 나오면 변경 예정
@Parcelize
data class InsightVo(
    val insightId: Int,
    val thumbnail: String,
    val region: String,
    val recommendCount: Int,
    val title: String,
    val profileImage: String,
    val nickname: String
) : Parcelable {
    companion object {
        fun getSamples(size: Int): List<InsightVo> {
            val samples = mutableListOf<InsightVo>()
            repeat(size) {
                samples.add(
                    InsightVo(
                        insightId = it,
                        thumbnail = "https://www.hyundai.co.kr/image/upload/asset_library/" +
                            "MDA00000000000003878/ff36eb6226674c648106fd06ff971e6c.jpg",
                        region = "강남구 신논현동",
                        recommendCount = 24,
                        title = "초역세권 대단지 아파트 후기",
                        profileImage = "",
                        nickname = "홍길동"
                    )
                )
            }
            return samples
        }
    }
}

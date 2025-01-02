package info.imdang.imdang.model.my

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// todo : Sample data, 실제 서버 데이터 나오면 변경 예정
@Parcelize
data class MyInfoVo(
    val memberId: Int,
    val profile: String,
    val nickname: String,
    val writtenInsightCount: Int,
    val totalExchangeCount: Int
) : Parcelable {
    companion object {
        fun getSample(): MyInfoVo = MyInfoVo(
            memberId = 0,
            profile = "",
            nickname = "홍길동",
            writtenInsightCount = 16,
            totalExchangeCount = 8
        )
    }
}

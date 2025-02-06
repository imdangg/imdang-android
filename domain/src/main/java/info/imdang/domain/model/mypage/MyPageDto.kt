package info.imdang.domain.model.mypage

data class MyPageDto(
    val memberId: String,
    val nickname: String,
    val birthDate: String,
    val gender: String,
    val deviceToken: String,
    val exchangeCount: Int,
    val insightCount: Int,
    val rejectedCount: Int
)

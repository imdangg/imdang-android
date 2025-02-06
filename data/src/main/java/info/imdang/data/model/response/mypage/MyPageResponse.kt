package info.imdang.data.model.response.mypage

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.mypage.MyPageDto

data class MyPageResponse(
    val memberId: String,
    val nickname: String,
    val birthDate: String,
    val gender: String,
    val deviceToken: String,
    val exchangeCount: Int,
    val insightCount: Int,
    val rejectedCount: Int
) : DataToDomainMapper<MyPageDto> {
    override fun mapper(): MyPageDto = MyPageDto(
        memberId = memberId,
        nickname = nickname,
        birthDate = birthDate,
        gender = gender,
        deviceToken = deviceToken,
        exchangeCount = exchangeCount,
        insightCount = insightCount,
        rejectedCount = rejectedCount
    )
}

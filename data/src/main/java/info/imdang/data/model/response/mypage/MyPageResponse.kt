package info.imdang.data.model.response.mypage

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.mypage.MyPageDto

data class MyPageResponse(
    val nickname: String,
    val insightCount: Int,
    val requestCount: Int
) : DataToDomainMapper<MyPageDto> {
    override fun mapper(): MyPageDto = MyPageDto(
        nickname = nickname,
        insightCount = insightCount,
        requestCount = requestCount
    )
}

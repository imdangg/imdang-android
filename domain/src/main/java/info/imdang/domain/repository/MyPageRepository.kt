package info.imdang.domain.repository

import info.imdang.domain.model.mypage.MyPageDto

interface MyPageRepository {

    suspend fun getMyPageInfo(
        memberId: String
    ): MyPageDto

    suspend fun withdrawalKakao(accessToken: String)
}

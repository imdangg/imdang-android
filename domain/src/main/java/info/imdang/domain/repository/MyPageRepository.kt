package info.imdang.domain.repository

import info.imdang.domain.model.mypage.MyPageDto

interface MyPageRepository {

    suspend fun getMyPageInfo(): MyPageDto

    suspend fun withdrawalKakao(accessToken: String)

    suspend fun withdrawalGoogle(accessToken: String)
}

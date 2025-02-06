package info.imdang.data.datasource.remote

import info.imdang.data.model.response.mypage.MyPageResponse

interface MyPageRemoteDataSource {

    suspend fun getMyPageInfo(
        memberId: String
    ): MyPageResponse
}

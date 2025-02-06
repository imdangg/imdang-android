package info.imdang.remote.service

import info.imdang.data.model.response.mypage.MyPageResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MyPageService {

    /** 마이 페이지 정보 조회 */
    @GET("members/info")
    suspend fun getMyPageInfo(
        @Query("memberId") memberId: String
    ): MyPageResponse
}

package info.imdang.remote.service

import info.imdang.data.model.request.auth.WithdrawRequest
import info.imdang.data.model.response.mypage.MyPageResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface MyPageService {

    /** 마이 페이지 정보 조회 */
    @GET("members/detail")
    suspend fun getMyPageInfo(): MyPageResponse

    /** 카카오 회원 탈퇴 API */
    @POST("members/withdrawal/kakao")
    suspend fun withdrawalKakao(
        @Body withdrawRequest: WithdrawRequest
    ): Response<Unit>

    /** 구글 회원 탈퇴 API */
    @POST("members/withdrawal/google")
    suspend fun withdrawalGoogle(
        @Body withdrawRequest: WithdrawRequest
    ): Response<Unit>
}

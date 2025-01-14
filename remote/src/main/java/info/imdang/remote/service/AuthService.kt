package info.imdang.remote.service

import info.imdang.data.model.request.auth.LoginRequest
import info.imdang.data.model.request.auth.OnboardingRequest
import info.imdang.data.model.response.auth.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

internal interface AuthService {

    /** 카카오 로그인 */
    @POST("auth/kakao")
    suspend fun kakaoLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    /** 구글 로그인 */
    @POST("auth/google")
    suspend fun googleLogin(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    /** 온보딩 */
    @PUT("auth/join")
    suspend fun onboardingJoin(
        @Body onboardingRequest: OnboardingRequest
    ): Response<Unit>
}

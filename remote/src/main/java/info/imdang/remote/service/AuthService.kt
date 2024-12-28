package info.imdang.remote.service

import info.imdang.data.model.request.auth.LoginRequest
import info.imdang.data.model.response.auth.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

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
}

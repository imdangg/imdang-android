package info.imdang.data.datasource.remote

import info.imdang.data.model.request.auth.LoginRequest
import info.imdang.data.model.request.auth.OnboardingRequest
import info.imdang.data.model.response.auth.LoginResponse
import retrofit2.Response

interface AuthRemoteDataSource {

    suspend fun kakaoLogin(loginRequest: LoginRequest): LoginResponse

    suspend fun googleLogin(loginRequest: LoginRequest): LoginResponse

    suspend fun onboardingJoin(
        accessToken: String,
        onboardingRequest: OnboardingRequest
    ): Response<Unit>
}

package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.AuthRemoteDataSource
import info.imdang.data.model.request.auth.LoginRequest
import info.imdang.data.model.request.auth.OnboardingRequest
import info.imdang.data.model.response.auth.LoginResponse
import info.imdang.data.model.response.auth.TokenResponse
import info.imdang.remote.service.AuthService
import retrofit2.Response
import javax.inject.Inject

internal class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {

    override suspend fun kakaoLogin(loginRequest: LoginRequest): LoginResponse =
        authService.kakaoLogin(loginRequest)

    override suspend fun googleLogin(loginRequest: LoginRequest): LoginResponse =
        authService.googleLogin(loginRequest)

    override suspend fun onboardingJoin(
        onboardingRequest: OnboardingRequest
    ): Response<Unit> =
        authService.onboardingJoin(onboardingRequest)

    override suspend fun reissueToken(): TokenResponse = authService.reissueToken()
}

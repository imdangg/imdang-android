package info.imdang.remote.datasource

import info.imdang.data.datasource.AuthRemoteDataSource
import info.imdang.data.model.request.auth.LoginRequest
import info.imdang.data.model.response.auth.LoginResponse
import info.imdang.remote.service.AuthService
import javax.inject.Inject

internal class AuthRemoteDataSourceImpl @Inject constructor(
    private val authService: AuthService
) : AuthRemoteDataSource {

    override suspend fun kakaoLogin(loginRequest: LoginRequest): LoginResponse =
        authService.kakaoLogin(loginRequest)

    override suspend fun googleLogin(loginRequest: LoginRequest): LoginResponse =
        authService.googleLogin(loginRequest)
}

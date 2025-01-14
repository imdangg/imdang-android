package info.imdang.data.repository

import info.imdang.data.datasource.lcoal.AuthLocalDataSource
import info.imdang.data.datasource.remote.AuthRemoteDataSource
import info.imdang.data.model.request.auth.LoginRequest
import info.imdang.data.model.request.auth.OnboardingRequest
import info.imdang.domain.model.auth.LoginDto
import info.imdang.domain.model.auth.OnboardingDto
import info.imdang.domain.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalDataSource: AuthLocalDataSource
) : AuthRepository {

    override suspend fun kakaoLogin(authorizationCode: String): LoginDto =
        authRemoteDataSource.kakaoLogin(
            LoginRequest(accessToken = authorizationCode)
        ).mapper()

    override suspend fun googleLogin(authorizationCode: String): LoginDto =
        authRemoteDataSource.googleLogin(
            LoginRequest(accessToken = authorizationCode)
        ).mapper()

    override suspend fun saveAccessToken(accessToken: String) {
        authLocalDataSource.saveAccessToken(accessToken)
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        authLocalDataSource.saveRefreshToken(refreshToken)
    }

    override suspend fun getAccessToken(): String = authLocalDataSource.getAccessToken()

    override suspend fun getRefreshToken(): String = authLocalDataSource.getRefreshToken()

    override suspend fun removeToken() = authLocalDataSource.removeToken()

    override suspend fun onboardingJoin(onboardingRequest: OnboardingDto) {
        authRemoteDataSource.onboardingJoin(
            onboardingRequest = OnboardingRequest.fromDomain(onboardingRequest)
        )
    }
}

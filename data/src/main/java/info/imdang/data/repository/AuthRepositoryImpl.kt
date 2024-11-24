package info.imdang.data.repository

import info.imdang.data.datasource.AuthRemoteDataSource
import info.imdang.data.model.request.auth.LoginRequest
import info.imdang.domain.model.auth.LoginDto
import info.imdang.domain.repository.AuthRepository
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource
) : AuthRepository {

    override suspend fun kakaoLogin(authorizationCode: String): LoginDto =
        authRemoteDataSource.kakaoLogin(
            LoginRequest(authorizationCode = authorizationCode)
        ).mapper()

    override suspend fun googleLogin(authorizationCode: String): LoginDto =
        authRemoteDataSource.googleLogin(
            LoginRequest(authorizationCode = authorizationCode)
        ).mapper()
}

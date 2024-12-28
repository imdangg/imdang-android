package info.imdang.domain.repository

import info.imdang.domain.model.auth.LoginDto

interface AuthRepository {

    suspend fun kakaoLogin(authorizationCode: String): LoginDto

    suspend fun googleLogin(authorizationCode: String): LoginDto

    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun removeToken()
}

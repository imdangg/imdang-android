package info.imdang.domain.repository

import info.imdang.domain.model.auth.LoginDto

interface AuthRepository {

    suspend fun kakaoLogin(authorizationCode: String): LoginDto

    suspend fun googleLogin(authorizationCode: String): LoginDto

    suspend fun saveToken(token: String)

    suspend fun getToken(): String

    suspend fun removeToken()
}

package info.imdang.data.datasource.lcoal

interface AuthLocalDataSource {

    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun removeToken()
}

package info.imdang.data.datasource.lcoal

interface AuthLocalDataSource {

    fun saveAccessToken(accessToken: String)

    fun saveRefreshToken(refreshToken: String)

    fun getAccessToken(): String

    fun getRefreshToken(): String

    fun removeToken()
}

package info.imdang.data.datasource.lcoal

interface AuthLocalDataSource {

    suspend fun saveToken(token: String)

    suspend fun getToken(): String

    suspend fun removeToken()
}

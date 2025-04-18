package info.imdang.data.datasource.lcoal

interface AuthLocalDataSource {

    fun saveAccessToken(accessToken: String)

    fun saveRefreshToken(refreshToken: String)

    fun getAccessToken(): String

    fun getRefreshToken(): String

    fun removeToken()

    fun saveMemberId(memberId: String)

    fun getMemberId(): String

    fun saveLoginType(loginType: String)

    fun getLoginType(): String

    fun saveOriginAccessToken(accessToken: String)

    fun getOriginAccessToken(): String
}

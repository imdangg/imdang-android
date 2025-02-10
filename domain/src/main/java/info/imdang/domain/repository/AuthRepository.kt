package info.imdang.domain.repository

import info.imdang.domain.model.auth.LoginDto
import info.imdang.domain.model.auth.OnboardingDto
import info.imdang.domain.model.auth.TokenDto

interface AuthRepository {

    suspend fun kakaoLogin(authorizationCode: String): LoginDto

    suspend fun googleLogin(authorizationCode: String): LoginDto

    suspend fun saveAccessToken(accessToken: String)

    suspend fun saveRefreshToken(refreshToken: String)

    suspend fun getAccessToken(): String

    suspend fun getRefreshToken(): String

    suspend fun removeToken()

    suspend fun onboardingJoin(onboardingRequest: OnboardingDto)

    suspend fun reissueToken(memberId: String, refreshToken: String): TokenDto?

    fun saveMemberId(memberId: String)

    fun getMemberId(): String

    fun saveLoginType(loginType: String)

    fun getLoginType(): String

    fun saveOriginAccessToken(accessToken: String)

    fun getOriginAccessToken(): String
}

package info.imdang.local.datasource

import android.content.SharedPreferences
import info.imdang.data.datasource.lcoal.AuthLocalDataSource
import info.imdang.local.constant.PREF_ACCESS_TOKEN_KEY
import info.imdang.local.constant.PREF_LOGIN_TYPE_KEY
import info.imdang.local.constant.PREF_MEMBER_ID_KEY
import info.imdang.local.constant.PREF_ORIGIN_ACCESS_TOKEN_KEY
import info.imdang.local.constant.PREF_REFRESH_TOKEN_KEY
import javax.inject.Inject

internal class AuthLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AuthLocalDataSource {

    override fun saveAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(PREF_ACCESS_TOKEN_KEY, accessToken).apply()
    }

    override fun saveRefreshToken(refreshToken: String) {
        sharedPreferences.edit().putString(PREF_REFRESH_TOKEN_KEY, refreshToken).apply()
    }

    override fun getAccessToken(): String =
        sharedPreferences.getString(PREF_ACCESS_TOKEN_KEY, "") ?: ""

    override fun getRefreshToken(): String =
        sharedPreferences.getString(PREF_REFRESH_TOKEN_KEY, "") ?: ""

    override fun removeToken() {
        sharedPreferences
            .edit()
            .remove(PREF_ACCESS_TOKEN_KEY)
            .remove(PREF_REFRESH_TOKEN_KEY)
            .apply()
    }

    override fun saveMemberId(memberId: String) {
        sharedPreferences.edit().putString(PREF_MEMBER_ID_KEY, memberId).apply()
    }

    override fun getMemberId(): String =
        sharedPreferences.getString(PREF_MEMBER_ID_KEY, "") ?: ""

    override fun saveLoginType(loginType: String) {
        sharedPreferences.edit().putString(PREF_LOGIN_TYPE_KEY, loginType).apply()
    }

    override fun getLoginType(): String =
        sharedPreferences.getString(PREF_LOGIN_TYPE_KEY, "") ?: ""

    override fun saveOriginAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(PREF_ORIGIN_ACCESS_TOKEN_KEY, accessToken).apply()
    }

    override fun getOriginAccessToken(): String =
        sharedPreferences.getString(PREF_ORIGIN_ACCESS_TOKEN_KEY, "") ?: ""
}

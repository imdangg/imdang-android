package info.imdang.local.datasource

import android.content.SharedPreferences
import info.imdang.data.datasource.lcoal.AuthLocalDataSource
import info.imdang.local.constant.PREF_ACCESS_TOKEN_KEY
import info.imdang.local.constant.PREF_REFRESH_TOKEN_KEY
import javax.inject.Inject

internal class AuthLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AuthLocalDataSource {

    override suspend fun saveAccessToken(accessToken: String) {
        sharedPreferences.edit().putString(PREF_ACCESS_TOKEN_KEY, accessToken).apply()
    }

    override suspend fun saveRefreshToken(refreshToken: String) {
        sharedPreferences.edit().putString(PREF_REFRESH_TOKEN_KEY, refreshToken).apply()
    }

    override suspend fun getAccessToken(): String =
        sharedPreferences.getString(PREF_ACCESS_TOKEN_KEY, "") ?: ""

    override suspend fun getRefreshToken(): String =
        sharedPreferences.getString(PREF_REFRESH_TOKEN_KEY, "") ?: ""

    override suspend fun removeToken() {
        sharedPreferences.edit().remove(PREF_ACCESS_TOKEN_KEY).apply()
    }
}

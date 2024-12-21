package info.imdang.local.datasource

import android.content.SharedPreferences
import info.imdang.data.datasource.lcoal.AuthLocalDataSource
import info.imdang.local.constant.PREF_TOKEN_KEY
import javax.inject.Inject

internal class AuthLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : AuthLocalDataSource {

    override suspend fun saveToken(token: String) {
        sharedPreferences.edit().putString(PREF_TOKEN_KEY, token).apply()
    }

    override suspend fun getToken(): String = sharedPreferences.getString(PREF_TOKEN_KEY, "") ?: ""

    override suspend fun removeToken() {
        sharedPreferences.edit().remove(PREF_TOKEN_KEY).apply()
    }
}

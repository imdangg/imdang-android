package info.imdang.local.datasource

import android.content.SharedPreferences
import info.imdang.data.datasource.lcoal.HomeLocalDataSource
import info.imdang.local.constant.PREF_CLOSE_HOME_FREE_PASS_POPUP_KEY
import info.imdang.local.constant.PREF_FIRST_OPEN_HOME_FREE_PASS_POPUP_KEY
import javax.inject.Inject

internal class HomeLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : HomeLocalDataSource {

    override suspend fun setFirstOpenDateOfHomeFreePassPopup(openDate: Long) {
        sharedPreferences.edit().putLong(PREF_FIRST_OPEN_HOME_FREE_PASS_POPUP_KEY, openDate).apply()
    }

    override suspend fun getFirstOpenDateOfHomeFreePassPopup(): Long =
        sharedPreferences.getLong(PREF_FIRST_OPEN_HOME_FREE_PASS_POPUP_KEY, 0)

    override suspend fun setCloseTimeOfHomeFreePassPopup(closeTime: Long) {
        sharedPreferences.edit().putLong(PREF_CLOSE_HOME_FREE_PASS_POPUP_KEY, closeTime).apply()
    }

    override suspend fun getCloseTimeOfHomeFreePassPopup(): Long =
        sharedPreferences.getLong(PREF_CLOSE_HOME_FREE_PASS_POPUP_KEY, 0)
}

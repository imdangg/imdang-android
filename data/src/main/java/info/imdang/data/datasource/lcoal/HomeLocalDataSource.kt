package info.imdang.data.datasource.lcoal

interface HomeLocalDataSource {

    suspend fun setCloseTimeOfHomeFreePassPopup(closeTime: Long)

    suspend fun getCloseTimeOfHomeFreePassPopup(): Long
}

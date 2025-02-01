package info.imdang.data.datasource.lcoal

interface HomeLocalDataSource {

    suspend fun setFirstOpenDateOfHomeFreePassPopup(openDate: Long)

    suspend fun getFirstOpenDateOfHomeFreePassPopup(): Long

    suspend fun setCloseTimeOfHomeFreePassPopup(closeTime: Long)

    suspend fun getCloseTimeOfHomeFreePassPopup(): Long
}

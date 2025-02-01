package info.imdang.domain.repository

interface HomeRepository {

    suspend fun setFirstOpenDateOfHomeFreePassPopup(openDate: Long)

    suspend fun getFirstOpenDateOfHomeFreePassPopup(): Long

    suspend fun setCloseTimeOfHomeFreePassPopup(closeTime: Long)

    suspend fun getCloseTimeOfHomeFreePassPopup(): Long
}

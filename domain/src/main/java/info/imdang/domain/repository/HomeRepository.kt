package info.imdang.domain.repository

interface HomeRepository {

    suspend fun setCloseTimeOfHomeFreePassPopup(closeTime: Long)

    suspend fun getCloseTimeOfHomeFreePassPopup(): Long
}

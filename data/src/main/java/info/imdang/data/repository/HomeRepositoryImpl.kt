package info.imdang.data.repository

import info.imdang.data.datasource.lcoal.HomeLocalDataSource
import info.imdang.domain.repository.HomeRepository
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    private val homeLocalDataSource: HomeLocalDataSource
) : HomeRepository {

    override suspend fun setCloseTimeOfHomeFreePassPopup(closeTime: Long) {
        homeLocalDataSource.setCloseTimeOfHomeFreePassPopup(closeTime)
    }

    override suspend fun getCloseTimeOfHomeFreePassPopup(): Long =
        homeLocalDataSource.getCloseTimeOfHomeFreePassPopup()
}

package info.imdang.data.repository

import info.imdang.data.datasource.lcoal.HomeLocalDataSource
import info.imdang.domain.repository.HomeRepository
import javax.inject.Inject

internal class HomeRepositoryImpl @Inject constructor(
    private val homeLocalDataSource: HomeLocalDataSource
) : HomeRepository {

    override suspend fun setFirstOpenDateOfHomeFreePassPopup(openDate: Long) {
        homeLocalDataSource.setFirstOpenDateOfHomeFreePassPopup(openDate)
    }

    override suspend fun getFirstOpenDateOfHomeFreePassPopup(): Long =
        homeLocalDataSource.getFirstOpenDateOfHomeFreePassPopup()

    override suspend fun setCloseTimeOfHomeFreePassPopup(closeTime: Long) {
        homeLocalDataSource.setCloseTimeOfHomeFreePassPopup(closeTime)
    }

    override suspend fun getCloseTimeOfHomeFreePassPopup(): Long =
        homeLocalDataSource.getCloseTimeOfHomeFreePassPopup()
}

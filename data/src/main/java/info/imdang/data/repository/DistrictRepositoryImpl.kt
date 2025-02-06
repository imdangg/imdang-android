package info.imdang.data.repository

import androidx.paging.PagingData
import info.imdang.data.datasource.remote.DistrictRemoteDataSource
import info.imdang.data.pagingsource.getPagingFlow
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.district.DistrictDto
import info.imdang.domain.repository.DistrictRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class DistrictRepositoryImpl @Inject constructor(
    private val districtRemoteDataSource: DistrictRemoteDataSource
) : DistrictRepository {

    override suspend fun getSiGunGu(
        page: Int?,
        size: Int?
    ): PagingDto<DistrictDto> = districtRemoteDataSource.getSiGunGu(
        page = page,
        size = size
    ).mapper()

    override suspend fun getEupMyeonDong(
        siGunGu: String,
        page: Int?,
        size: Int?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<DistrictDto>> = getPagingFlow(
        initialPage = page ?: 0,
        pageSize = size ?: 30,
        loadData = { currentPage, pageSize ->
            districtRemoteDataSource.getEupMyeonDong(
                siGunGu = siGunGu,
                page = currentPage,
                size = pageSize
            ).mapper()
        },
        totalCountListener = totalCountListener
    )
}

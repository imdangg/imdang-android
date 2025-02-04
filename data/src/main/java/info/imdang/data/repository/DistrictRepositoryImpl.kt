package info.imdang.data.repository

import info.imdang.data.datasource.remote.DistrictRemoteDataSource
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.district.DistrictDto
import info.imdang.domain.repository.DistrictRepository
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
        size: Int?
    ): PagingDto<DistrictDto> = districtRemoteDataSource.getEupMyeonDong(
        siGunGu = siGunGu,
        page = page,
        size = size
    ).mapper()
}

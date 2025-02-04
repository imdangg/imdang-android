package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.DistrictRemoteDataSource
import info.imdang.data.model.district.DistrictResponse
import info.imdang.data.model.response.common.PagingResponse
import info.imdang.domain.model.district.DistrictDto
import info.imdang.remote.service.DistrictService
import javax.inject.Inject

internal class DistrictRemoteDataSourceImpl @Inject constructor(
    private val districtService: DistrictService
) : DistrictRemoteDataSource {

    override suspend fun getSiGunGu(
        page: Int?,
        size: Int?
    ): PagingResponse<DistrictResponse, DistrictDto> = districtService.getSiGunGu(
        page = page,
        size = size
    )

    override suspend fun getEupMyeonDong(
        siGunGu: String,
        page: Int?,
        size: Int?
    ): PagingResponse<DistrictResponse, DistrictDto> = districtService.getEupMyeonDong(
        siGunGu = siGunGu,
        page = page,
        size = size
    )
}

package info.imdang.data.datasource.remote

import info.imdang.data.model.district.DistrictResponse
import info.imdang.data.model.response.common.PagingResponse
import info.imdang.domain.model.district.DistrictDto

interface DistrictRemoteDataSource {

    suspend fun getSiGunGu(
        page: Int?,
        size: Int?
    ): PagingResponse<DistrictResponse, DistrictDto>

    suspend fun getEupMyeonDong(
        siGunGu: String,
        page: Int?,
        size: Int?
    ): PagingResponse<DistrictResponse, DistrictDto>
}

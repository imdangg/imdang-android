package info.imdang.domain.repository

import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.district.DistrictDto

interface DistrictRepository {

    suspend fun getSiGunGu(
        page: Int?,
        size: Int?
    ): PagingDto<DistrictDto>

    suspend fun getEupMyeonDong(
        siGunGu: String,
        page: Int?,
        size: Int?
    ): PagingDto<DistrictDto>
}

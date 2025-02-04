package info.imdang.domain.repository

import androidx.paging.PagingData
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.district.DistrictDto
import kotlinx.coroutines.flow.Flow

interface DistrictRepository {

    suspend fun getSiGunGu(
        page: Int?,
        size: Int?
    ): PagingDto<DistrictDto>

    suspend fun getEupMyeonDong(
        siGunGu: String,
        page: Int?,
        size: Int?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<DistrictDto>>
}

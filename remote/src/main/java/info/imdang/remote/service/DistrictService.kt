package info.imdang.remote.service

import info.imdang.data.model.district.DistrictResponse
import info.imdang.data.model.response.common.PagingResponse
import info.imdang.domain.model.district.DistrictDto
import retrofit2.http.GET
import retrofit2.http.Query

internal interface DistrictService {

    /** 지역구 조회 */
    @GET("districts/si-gun-gu")
    suspend fun getSiGunGu(
        @Query("pageNumber") page: Int?,
        @Query("pageSize") size: Int?
    ): PagingResponse<DistrictResponse, DistrictDto>

    /** 읍면동 조회 */
    @GET("districts/eup-myeon-dong")
    suspend fun getEupMyeonDong(
        @Query("siGunGu") siGunGu: String,
        @Query("pageNumber") page: Int?,
        @Query("pageSize") size: Int?
    ): PagingResponse<DistrictResponse, DistrictDto>
}

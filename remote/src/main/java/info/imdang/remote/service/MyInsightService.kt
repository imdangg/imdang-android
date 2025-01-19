package info.imdang.remote.service

import info.imdang.data.model.response.aptcomplex.AptComplexResponse
import info.imdang.data.model.response.myinsight.MyInsightAddressResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

internal interface MyInsightService {

    /** 보관함 주소 목록 조회 */
    @GET("my-insights/districts")
    suspend fun getAddresses(): List<MyInsightAddressResponse>

    /** 보관함 단지 목록 조회 */
    @GET("my-insights/by-district/apartment-complexes")
    suspend fun getComplexesByAddress(
        @QueryMap queries: Map<String, String>
    ): List<AptComplexResponse>
}

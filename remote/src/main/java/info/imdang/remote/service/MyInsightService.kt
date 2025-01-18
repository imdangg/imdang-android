package info.imdang.remote.service

import info.imdang.data.model.response.myinsight.MyInsightAddressResponse
import retrofit2.http.GET

internal interface MyInsightService {

    /** 보관함 주소 목록 조회 */
    @GET("my-insights/districts")
    suspend fun getAddresses(): List<MyInsightAddressResponse>
}

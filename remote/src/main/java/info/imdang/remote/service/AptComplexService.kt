package info.imdang.remote.service

import info.imdang.data.model.response.insight.VisitAptComplexResponse
import retrofit2.http.GET

internal interface AptComplexService {

    /** 아파트 단지 이름 목록 조회 */
    @GET("apartment-complexes/my-visited")
    suspend fun getVisitedAptComplexes(): List<VisitAptComplexResponse>
}

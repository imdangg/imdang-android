package info.imdang.data.datasource.remote

import info.imdang.data.model.response.insight.VisitAptComplexResponse

interface AptComplexRemoteDataSource {

    suspend fun getVisitedAptComplexes(): List<VisitAptComplexResponse>
}

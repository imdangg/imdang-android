package info.imdang.data.datasource.remote

import info.imdang.data.model.response.aptcomplex.VisitedAptComplexResponse

interface AptComplexRemoteDataSource {

    suspend fun getVisitedAptComplexes(): List<VisitedAptComplexResponse>
}

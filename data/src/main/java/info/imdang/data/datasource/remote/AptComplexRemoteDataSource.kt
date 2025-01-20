package info.imdang.data.datasource.remote

import info.imdang.data.model.response.aptcomplex.VisitAptComplexResponse

interface AptComplexRemoteDataSource {

    suspend fun getVisitedAptComplexes(): List<VisitAptComplexResponse>
}

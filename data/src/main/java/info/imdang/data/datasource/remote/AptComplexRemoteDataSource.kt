package info.imdang.data.datasource.remote

import info.imdang.data.model.response.aptcomplex.AptComplexResponse

interface AptComplexRemoteDataSource {

    suspend fun getVisitedAptComplexes(): List<AptComplexResponse>
}

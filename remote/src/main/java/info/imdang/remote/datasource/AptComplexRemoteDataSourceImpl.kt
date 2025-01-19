package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.AptComplexRemoteDataSource
import info.imdang.data.model.response.aptcomplex.AptComplexResponse
import info.imdang.remote.service.AptComplexService
import javax.inject.Inject

internal class AptComplexRemoteDataSourceImpl @Inject constructor(
    private val aptComplexService: AptComplexService
) : AptComplexRemoteDataSource {

    override suspend fun getVisitedAptComplexes(): List<AptComplexResponse> =
        aptComplexService.getVisitedAptComplexes()
}

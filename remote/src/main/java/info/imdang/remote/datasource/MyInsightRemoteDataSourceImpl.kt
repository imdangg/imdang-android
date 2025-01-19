package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.MyInsightRemoteDataSource
import info.imdang.data.model.response.aptcomplex.AptComplexResponse
import info.imdang.data.model.response.myinsight.MyInsightAddressResponse
import info.imdang.remote.service.MyInsightService
import javax.inject.Inject

internal class MyInsightRemoteDataSourceImpl @Inject constructor(
    private val myInsightService: MyInsightService
) : MyInsightRemoteDataSource {

    override suspend fun getAddresses(): List<MyInsightAddressResponse> =
        myInsightService.getAddresses()

    override suspend fun getComplexesByAddress(
        queries: Map<String, String>
    ): List<AptComplexResponse> = myInsightService.getComplexesByAddress(queries)
}

package info.imdang.data.datasource.remote

import info.imdang.data.model.response.aptcomplex.AptComplexResponse
import info.imdang.data.model.response.myinsight.MyInsightAddressResponse

interface MyInsightRemoteDataSource {

    suspend fun getAddresses(): List<MyInsightAddressResponse>

    suspend fun getComplexesByAddress(queries: Map<String, String>): List<AptComplexResponse>
}

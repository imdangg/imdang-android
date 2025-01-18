package info.imdang.data.datasource.remote

import info.imdang.data.model.response.myinsight.MyInsightAddressResponse

interface MyInsightRemoteDataSource {

    suspend fun getAddresses(): List<MyInsightAddressResponse>
}

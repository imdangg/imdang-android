package info.imdang.data.repository

import com.google.gson.Gson
import info.imdang.data.datasource.remote.MyInsightRemoteDataSource
import info.imdang.data.mapper.mapper
import info.imdang.domain.model.aptcomplex.AptComplexDto
import info.imdang.domain.model.myinsight.MyInsightAddressDto
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.repository.MyInsightRepository
import javax.inject.Inject

internal class MyInsightRepositoryImpl @Inject constructor(
    private val myInsightRemoteDataSource: MyInsightRemoteDataSource
) : MyInsightRepository {

    override suspend fun getAddresses(): List<MyInsightAddressDto> =
        myInsightRemoteDataSource.getAddresses().mapper()

    override suspend fun getComplexesByAddress(
        address: AddressDto
    ): List<AptComplexDto> = myInsightRemoteDataSource.getComplexesByAddress(
        queries = Gson().fromJson<Map<String, String>>(Gson().toJson(address), Map::class.java)
    ).mapper()
}

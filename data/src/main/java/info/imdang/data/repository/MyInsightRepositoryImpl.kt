package info.imdang.data.repository

import info.imdang.data.datasource.remote.MyInsightRemoteDataSource
import info.imdang.data.mapper.mapper
import info.imdang.domain.model.myinsight.MyInsightAddressDto
import info.imdang.domain.repository.MyInsightRepository
import javax.inject.Inject

internal class MyInsightRepositoryImpl @Inject constructor(
    private val myInsightRemoteDataSource: MyInsightRemoteDataSource
) : MyInsightRepository {

    override suspend fun getAddresses(): List<MyInsightAddressDto> =
        myInsightRemoteDataSource.getAddresses().mapper()
}

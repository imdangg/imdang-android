package info.imdang.data.repository

import com.google.gson.Gson
import info.imdang.data.datasource.remote.MyInsightRemoteDataSource
import info.imdang.data.mapper.mapper
import info.imdang.domain.model.myinsight.AptComplexDto
import info.imdang.domain.model.myinsight.MyInsightAddressDto
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.insight.InsightDto
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

    override suspend fun getInsightsByAddress(
        address: AddressDto,
        aptComplexName: String?,
        onlyMine: Boolean?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<InsightDto> {
        return myInsightRemoteDataSource.getInsightsByAddress(
            addressQueries = Gson()
                .fromJson<Map<String, String>>(Gson().toJson(address), Map::class.java),
            aptComplexName = aptComplexName,
            onlyMine = onlyMine,
            page = page,
            size = size,
            direction = direction,
            properties = properties
        ).mapper()
    }
}

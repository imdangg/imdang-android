package info.imdang.data.repository

import androidx.paging.PagingData
import com.google.gson.Gson
import info.imdang.data.datasource.remote.MyInsightRemoteDataSource
import info.imdang.data.mapper.mapper
import info.imdang.data.pagingsource.getPagingFlow
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.myinsight.AptComplexDto
import info.imdang.domain.model.myinsight.MyInsightAddressDto
import info.imdang.domain.repository.MyInsightRepository
import kotlinx.coroutines.flow.Flow
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

    override suspend fun getMyInsightsByAddress(
        address: AddressDto,
        aptComplexName: String?,
        onlyMine: Boolean?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = getPagingFlow(
        initialPage = page ?: 0,
        pageSize = size ?: 20,
        loadData = { currentPage, pageSize ->
            myInsightRemoteDataSource.getMyInsightsByAddress(
                addressQueries = Gson()
                    .fromJson<Map<String, String>>(Gson().toJson(address), Map::class.java),
                aptComplexName = aptComplexName,
                onlyMine = onlyMine,
                page = currentPage,
                size = pageSize,
                direction = direction,
                properties = properties
            ).mapper()
        },
        totalCountListener = totalCountListener
    )

    override suspend fun getMyInsights(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<InsightDto> = myInsightRemoteDataSource.getMyInsights(
        page = page,
        size = size,
        direction = direction,
        properties = properties
    ).mapper()

    override suspend fun getMyInsightsWithPaging(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>> = getPagingFlow(
        initialPage = page ?: 0,
        pageSize = size ?: 20,
        loadData = { currentPage, pageSize ->
            myInsightRemoteDataSource.getMyInsights(
                page = currentPage,
                size = pageSize,
                direction = direction,
                properties = properties
            ).mapper()
        },
        totalCountListener = totalCountListener
    )
}

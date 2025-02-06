package info.imdang.domain.repository

import androidx.paging.PagingData
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.myinsight.AptComplexDto
import info.imdang.domain.model.myinsight.MyInsightAddressDto
import kotlinx.coroutines.flow.Flow

interface MyInsightRepository {

    suspend fun getAddresses(): List<MyInsightAddressDto>

    suspend fun getComplexesByAddress(address: AddressDto): List<AptComplexDto>

    suspend fun getMyInsightsByAddress(
        address: AddressDto,
        aptComplexName: String?,
        onlyMine: Boolean?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>>

    suspend fun getMyInsights(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<InsightDto>

    suspend fun getMyInsightsWithPaging(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?,
        totalCountListener: ((Int) -> Unit)?
    ): Flow<PagingData<InsightDto>>
}

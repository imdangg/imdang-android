package info.imdang.domain.repository

import info.imdang.domain.model.myinsight.AptComplexDto
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.model.myinsight.MyInsightAddressDto

interface MyInsightRepository {

    suspend fun getAddresses(): List<MyInsightAddressDto>

    suspend fun getComplexesByAddress(address: AddressDto): List<AptComplexDto>

    suspend fun getInsightsByAddress(
        address: AddressDto,
        aptComplexName: String?,
        onlyMine: Boolean?,
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<InsightDto>

    suspend fun getMyInsights(
        page: Int?,
        size: Int?,
        direction: String?,
        properties: List<String>?
    ): PagingDto<InsightDto>
}

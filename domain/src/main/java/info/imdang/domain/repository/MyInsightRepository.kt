package info.imdang.domain.repository

import info.imdang.domain.model.aptcomplex.AptComplexDto
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.myinsight.MyInsightAddressDto

interface MyInsightRepository {

    suspend fun getAddresses(): List<MyInsightAddressDto>

    suspend fun getComplexesByAddress(address: AddressDto): List<AptComplexDto>
}

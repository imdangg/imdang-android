package info.imdang.domain.repository

import info.imdang.domain.model.myinsight.MyInsightAddressDto

interface MyInsightRepository {

    suspend fun getAddresses(): List<MyInsightAddressDto>
}

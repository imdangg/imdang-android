package info.imdang.data.model.response.myinsight

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.myinsight.MyInsightAddressDto

data class MyInsightAddressResponse(
    val siDo: String,
    val siGunGu: String,
    val eupMyeonDong: String,
    val apartmentComplexCount: Int,
    val insightCount: Int
) : DataToDomainMapper<MyInsightAddressDto> {
    override fun mapper(): MyInsightAddressDto = MyInsightAddressDto(
        siDo = siDo,
        siGunGu = siGunGu,
        eupMyeonDong = eupMyeonDong,
        apartmentComplexCount = apartmentComplexCount,
        insightCount = insightCount
    )
}

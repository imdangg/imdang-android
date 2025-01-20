package info.imdang.imdang.model.myinsight

import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.myinsight.MyInsightAddressDto

data class MyInsightAddressVo(
    val siDo: String,
    val siGunGu: String,
    val eupMyeonDong: String,
    val aptComplexCount: Int,
    val insightCount: Int,
    val isSelected: Boolean
) {
    fun toSiGuDong() = "$siDo $siGunGu $eupMyeonDong"

    fun toAddressDto(): AddressDto = AddressDto(
        siDo = siDo,
        siGunGu = siGunGu,
        eupMyeonDong = eupMyeonDong,
        roadName = null,
        buildingNumber = null,
        detail = null
    )
}

fun MyInsightAddressDto.mapper(
    isSelected: Boolean = false
): MyInsightAddressVo = MyInsightAddressVo(
    siDo = siDo,
    siGunGu = siGunGu,
    eupMyeonDong = eupMyeonDong,
    aptComplexCount = apartmentComplexCount,
    insightCount = insightCount,
    isSelected = isSelected
)

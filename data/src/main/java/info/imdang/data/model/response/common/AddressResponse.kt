package info.imdang.data.model.response.common

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.common.AddressDto

data class AddressResponse(
    val siDo: String,
    val siGunGu: String,
    val eupMyeonDong: String,
    val roadName: String?,
    val buildingNumber: String?,
    val detail: String?
) : DataToDomainMapper<AddressDto> {
    override fun mapper(): AddressDto = AddressDto(
        siDo = siDo,
        siGunGu = siGunGu,
        eupMyeonDong = eupMyeonDong,
        roadName = roadName,
        buildingNumber = buildingNumber,
        detail = detail
    )
}

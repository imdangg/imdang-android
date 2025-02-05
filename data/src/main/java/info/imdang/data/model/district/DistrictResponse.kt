package info.imdang.data.model.district

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.district.DistrictDto

data class DistrictResponse(
    val siDo: String,
    val siGunGu: String,
    val eupMyeonDong: String?,
    val code: String
) : DataToDomainMapper<DistrictDto> {
    override fun mapper(): DistrictDto = DistrictDto(
        siDo = siDo,
        siGunGu = siGunGu,
        eupMyeonDong = eupMyeonDong,
        code = code
    )
}

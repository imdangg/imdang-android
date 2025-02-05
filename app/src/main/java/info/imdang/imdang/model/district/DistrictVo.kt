package info.imdang.imdang.model.district

import info.imdang.domain.model.district.DistrictDto

data class DistrictVo(
    val siDo: String,
    val siGunGu: String,
    val eupMyeonDong: String?,
    val code: String,
    val isSelected: Boolean
)

fun DistrictDto.mapper(): DistrictVo = DistrictVo(
    siDo = siDo,
    siGunGu = siGunGu,
    eupMyeonDong = eupMyeonDong,
    code = code,
    isSelected = false
)

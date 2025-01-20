package info.imdang.imdang.model.common

import android.os.Parcelable
import info.imdang.domain.model.common.AddressDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class AddressVo(
    val siDo: String,
    val siGunGu: String,
    val eupMyeonDong: String,
    val roadName: String,
    val buildingNumber: String,
    val detail: String,
    val latitude: Double?,
    val longitude: Double?
) : Parcelable {
    fun toSiGuDong() = "$siDo $siGunGu $eupMyeonDong"

    fun toGuDong() = "$siGunGu $eupMyeonDong"

    companion object {
        fun getSample(): AddressVo {
            return AddressVo(
                siDo = "서울",
                siGunGu = "강남구",
                eupMyeonDong = "신논현동",
                roadName = "",
                buildingNumber = "131",
                detail = "",
                latitude = 37.5304831048862,
                longitude = 126.902812773342
            )
        }
    }
}

fun AddressDto.mapper() = AddressVo(
    siDo = siDo,
    siGunGu = siGunGu,
    eupMyeonDong = eupMyeonDong,
    roadName = roadName ?: "",
    buildingNumber = buildingNumber ?: "",
    detail = detail ?: "",
    latitude = latitude,
    longitude = longitude
)

fun AddressVo.mapper() = AddressDto(
    siDo = siDo,
    siGunGu = siGunGu,
    eupMyeonDong = eupMyeonDong,
    roadName = roadName.takeIf { it.isNotEmpty() },
    buildingNumber = buildingNumber.takeIf { it.isNotEmpty() },
    detail = detail.takeIf { it.isNotEmpty() },
    latitude = latitude,
    longitude = longitude
)

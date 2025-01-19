package info.imdang.domain.model.common

data class AddressDto(
    val siDo: String,
    val siGunGu: String,
    val eupMyeonDong: String,
    val roadName: String?,
    val buildingNumber: String?,
    val detail: String?,
    val latitude: Double?,
    val longitude: Double?
)

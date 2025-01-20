package info.imdang.data.model.request.myinsight

data class AddressRequest(
    val siDo: String,
    val siGunGu: String,
    val eupMyeonDong: String,
    val roadName: String?,
    val buildingNumber: String?,
    val detail: String?
)

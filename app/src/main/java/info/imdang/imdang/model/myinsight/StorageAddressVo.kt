package info.imdang.imdang.model.myinsight

import info.imdang.imdang.model.common.AddressVo

data class StorageAddressVo(
    val address: AddressVo,
    val complexCount: Int,
    val insightCount: Int,
    val isSelected: Boolean
)

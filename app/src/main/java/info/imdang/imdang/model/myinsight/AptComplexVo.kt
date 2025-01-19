package info.imdang.imdang.model.myinsight

import info.imdang.domain.model.myinsight.AptComplexDto

data class AptComplexVo(
    val aptComplexName: String,
    val insightCount: Int,
    val isSelected: Boolean
)

fun AptComplexDto.mapper(isSelected: Boolean = false): AptComplexVo = AptComplexVo(
    aptComplexName = apartmentComplexName,
    insightCount = insightCount ?: 0,
    isSelected = isSelected
)

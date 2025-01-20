package info.imdang.imdang.model.aptcomplex

import info.imdang.domain.model.aptcomplex.VisitAptComplexDto

data class VisitAptComplexVo(
    val aptComplexName: String,
    val isSelected: Boolean
)

fun VisitAptComplexDto.mapper(isSelected: Boolean = false): VisitAptComplexVo = VisitAptComplexVo(
    aptComplexName = name,
    isSelected = isSelected
)

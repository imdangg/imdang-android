package info.imdang.imdang.model.aptcomplex

import info.imdang.domain.model.aptcomplex.VisitedAptComplexDto

data class VisitedAptComplexVo(
    val name: String,
    val isSelected: Boolean
)

fun VisitedAptComplexDto.mapper(isSelected: Boolean): VisitedAptComplexVo = VisitedAptComplexVo(
    name = name,
    isSelected = isSelected
)

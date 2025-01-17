package info.imdang.domain.repository

import info.imdang.domain.model.aptcomplex.VisitedAptComplexDto

interface AptComplexRepository {

    suspend fun getVisitedAptComplexes(): List<VisitedAptComplexDto>
}

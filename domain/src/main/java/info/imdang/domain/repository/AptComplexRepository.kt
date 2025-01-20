package info.imdang.domain.repository

import info.imdang.domain.model.aptcomplex.VisitAptComplexDto

interface AptComplexRepository {

    suspend fun getVisitedAptComplexes(): List<VisitAptComplexDto>
}

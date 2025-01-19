package info.imdang.domain.repository

import info.imdang.domain.model.aptcomplex.AptComplexDto

interface AptComplexRepository {

    suspend fun getVisitedAptComplexes(): List<AptComplexDto>
}

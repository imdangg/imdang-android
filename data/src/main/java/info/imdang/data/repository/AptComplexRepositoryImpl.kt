package info.imdang.data.repository

import info.imdang.data.datasource.remote.AptComplexRemoteDataSource
import info.imdang.data.mapper.mapper
import info.imdang.domain.model.aptcomplex.AptComplexDto
import info.imdang.domain.repository.AptComplexRepository
import javax.inject.Inject

internal class AptComplexRepositoryImpl @Inject constructor(
    private val aptComplexRemoteDataSource: AptComplexRemoteDataSource
) : AptComplexRepository {

    override suspend fun getVisitedAptComplexes(): List<AptComplexDto> =
        aptComplexRemoteDataSource.getVisitedAptComplexes().mapper()
}

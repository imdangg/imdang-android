package info.imdang.domain.usecase.district

import androidx.paging.PagingData
import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.district.DistrictDto
import info.imdang.domain.repository.DistrictRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEupMyeonDongUseCase @Inject constructor(
    private val districtRepository: DistrictRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetEupMyeonDongParams, Flow<PagingData<DistrictDto>>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: GetEupMyeonDongParams
    ): Flow<PagingData<DistrictDto>> = districtRepository.getEupMyeonDong(
        siGunGu = parameters.siGunGu,
        page = parameters.pagingParams.page - 1,
        size = parameters.pagingParams.size,
        totalCountListener = parameters.pagingParams.totalCountListener
    )
}

data class GetEupMyeonDongParams(
    val siGunGu: String,
    val pagingParams: PagingParams
)

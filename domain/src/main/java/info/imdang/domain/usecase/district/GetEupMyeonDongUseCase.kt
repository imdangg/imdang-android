package info.imdang.domain.usecase.district

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.district.DistrictDto
import info.imdang.domain.repository.DistrictRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetEupMyeonDongUseCase @Inject constructor(
    private val districtRepository: DistrictRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetEupMyeonDongParams, PagingDto<DistrictDto>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: GetEupMyeonDongParams
    ): PagingDto<DistrictDto> = districtRepository.getEupMyeonDong(
        siGunGu = parameters.siGunGu,
        page = parameters.pagingParams.page - 1,
        size = parameters.pagingParams.size
    )
}

data class GetEupMyeonDongParams(
    val siGunGu: String,
    val pagingParams: PagingParams
)

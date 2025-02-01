package info.imdang.domain.usecase.myinsight

import androidx.paging.PagingData
import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.model.common.PagingParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.repository.MyInsightRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInsightsByAddressUseCase @Inject constructor(
    private val myInsightRepository: MyInsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetInsightsByAddressParams, Flow<PagingData<InsightDto>>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: GetInsightsByAddressParams
    ): Flow<PagingData<InsightDto>> = myInsightRepository.getInsightsByAddress(
        address = parameters.address,
        aptComplexName = parameters.aptComplexName,
        onlyMine = parameters.onlyMine,
        page = parameters.pagingParams.page - 1,
        size = parameters.pagingParams.size,
        direction = parameters.pagingParams.direction,
        properties = parameters.pagingParams.properties,
        totalCountListener = parameters.pagingParams.totalCountListener
    )
}

data class GetInsightsByAddressParams(
    val address: AddressDto,
    val aptComplexName: String?,
    val onlyMine: Boolean?,
    val pagingParams: PagingParams
)

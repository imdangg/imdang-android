package info.imdang.domain.usecase.myexchange

import androidx.paging.PagingData
import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.MyExchangesParams
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.repository.MyExchangeRepository
import info.imdang.domain.usecase.UseCase
import info.imdang.domain.usecase.auth.GetMemberIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMyExchangeUseCase @Inject constructor(
    private val myExchangeRepository: MyExchangeRepository,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<MyExchangesParams, Flow<PagingData<InsightDto>>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: MyExchangesParams
    ): Flow<PagingData<InsightDto>> = myExchangeRepository.getRequestedMyExchanges(
        requestMemberId = getMemberIdUseCase(),
        exchangeRequestStatus = parameters.exchangeRequestStatus,
        page = parameters.pagingParams.page - 1,
        size = parameters.pagingParams.size,
        direction = parameters.pagingParams.direction,
        properties = parameters.pagingParams.properties,
        totalCountListener = parameters.pagingParams.totalCountListener
    )
}

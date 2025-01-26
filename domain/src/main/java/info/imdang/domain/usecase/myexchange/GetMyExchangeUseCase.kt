package info.imdang.domain.usecase.myexchange

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.common.MyExchangesParams
import info.imdang.domain.model.common.PagingDto
import info.imdang.domain.model.insight.InsightDto
import info.imdang.domain.repository.MyExchangeRepository
import info.imdang.domain.usecase.UseCase
import info.imdang.domain.usecase.auth.GetMemberIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetMyExchangeUseCase @Inject constructor(
    private val myExchangeRepository: MyExchangeRepository,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<MyExchangesParams, PagingDto<InsightDto>>(
        coroutineDispatcher = dispatcher
    ) {

    override suspend fun execute(
        parameters: MyExchangesParams
    ): PagingDto<InsightDto> = myExchangeRepository.getMyExchanges(
        requestMemberId = getMemberIdUseCase(),
        exchangeRequestStatus = parameters.exchangeRequestStatus,
        page = parameters.pagingParams.page - 1,
        size = parameters.pagingParams.size,
        direction = parameters.pagingParams.direction,
        properties = parameters.pagingParams.properties
    )
}

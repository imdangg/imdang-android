package info.imdang.domain.usecase.exchange

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.exchange.ExchangeDto
import info.imdang.domain.repository.ExchangeRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class AcceptExchangeUseCase @Inject constructor(
    private val exchangeRepository: ExchangeRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<ResponseExchangeParams, ExchangeDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(
        parameters: ResponseExchangeParams
    ): ExchangeDto = exchangeRepository.acceptExchange(
        exchangeRequestId = parameters.exchangeRequestId,
        memberId = parameters.memberId
    )
}

data class ResponseExchangeParams(
    val exchangeRequestId: String,
    val memberId: String
)

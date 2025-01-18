package info.imdang.domain.usecase.myinsight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.myinsight.MyInsightAddressDto
import info.imdang.domain.repository.MyInsightRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetAddressesUseCase @Inject constructor(
    private val myInsightRepository: MyInsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, List<MyInsightAddressDto>>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit): List<MyInsightAddressDto> =
        myInsightRepository.getAddresses()
}

package info.imdang.domain.usecase.myinsight

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.myinsight.AptComplexDto
import info.imdang.domain.model.common.AddressDto
import info.imdang.domain.repository.MyInsightRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetComplexesByAddressUseCase @Inject constructor(
    private val myInsightRepository: MyInsightRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<AddressDto, List<AptComplexDto>>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: AddressDto): List<AptComplexDto> =
        myInsightRepository.getComplexesByAddress(parameters)
}

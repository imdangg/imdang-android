package info.imdang.domain.usecase.google

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.google.GoogleAccessTokenDto
import info.imdang.domain.repository.GoogleRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetGoogleAccessTokenUseCase @Inject constructor(
    private val repository: GoogleRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, GoogleAccessTokenDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: String): GoogleAccessTokenDto =
        repository.getAccessToken(parameters)
}

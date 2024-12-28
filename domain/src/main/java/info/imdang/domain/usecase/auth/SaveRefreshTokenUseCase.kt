package info.imdang.domain.usecase.auth

import info.imdang.domain.IoDispatcher
import info.imdang.domain.repository.AuthRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class SaveRefreshTokenUseCase @Inject constructor(
    private val repository: AuthRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: String) = repository.saveRefreshToken(parameters)
}

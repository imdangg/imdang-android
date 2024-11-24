package info.imdang.domain.usecase.auth

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.auth.LoginDto
import info.imdang.domain.repository.AuthRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GoogleLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, LoginDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: String): LoginDto =
        authRepository.googleLogin(parameters)
}

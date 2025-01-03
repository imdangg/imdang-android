package info.imdang.domain.usecase.auth

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.auth.LoginDto
import info.imdang.domain.repository.AuthRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val saveAccessTokenUseCase: SaveAccessTokenUseCase,
    private val saveRefreshTokenUseCase: SaveRefreshTokenUseCase,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<String, LoginDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: String): LoginDto {
        val loginDto = authRepository.kakaoLogin(parameters)
        saveAccessTokenUseCase(loginDto.accessToken)
        saveRefreshTokenUseCase(loginDto.refreshToken)
        return loginDto
    }
}

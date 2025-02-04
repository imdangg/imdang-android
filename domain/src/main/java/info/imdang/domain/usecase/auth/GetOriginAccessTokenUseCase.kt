package info.imdang.domain.usecase.auth

import info.imdang.domain.repository.AuthRepository
import javax.inject.Inject

class GetOriginAccessTokenUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    operator fun invoke(): String = repository.getOriginAccessToken()
}

package info.imdang.domain.usecase.mypage

import info.imdang.domain.IoDispatcher
import info.imdang.domain.repository.MyPageRepository
import info.imdang.domain.usecase.UseCase
import info.imdang.domain.usecase.auth.GetOriginAccessTokenUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class WithdrawalKakaoUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val getOriginAccessTokenUseCase: GetOriginAccessTokenUseCase,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, Unit>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(parameters: Unit) {
        val accessToken = getOriginAccessTokenUseCase()
        myPageRepository.withdrawalKakao(accessToken)
    }
}

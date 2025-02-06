package info.imdang.domain.usecase.mypage

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.mypage.MyPageDto
import info.imdang.domain.repository.MyPageRepository
import info.imdang.domain.usecase.UseCase
import info.imdang.domain.usecase.auth.GetMemberIdUseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetMyPageInfoUseCase @Inject constructor(
    private val myPageRepository: MyPageRepository,
    private val getMemberIdUseCase: GetMemberIdUseCase,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<Unit, MyPageDto>(coroutineDispatcher = dispatcher) {

    override suspend fun execute(
        parameters: Unit
    ): MyPageDto = myPageRepository.getMyPageInfo(
        memberId = getMemberIdUseCase()
    )
}

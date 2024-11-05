package info.imdang.domain.usecase.user

import info.imdang.domain.IoDispatcher
import info.imdang.domain.model.UserDto
import info.imdang.domain.repository.UserRepository
import info.imdang.domain.usecase.UseCase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository,
    @IoDispatcher dispatcher: CoroutineDispatcher
) : UseCase<GetUsersParams, UserDto>(coroutineDispatcher = dispatcher) {
    override suspend fun execute(parameters: GetUsersParams): UserDto = repository.getUsers(
        parameters.q,
        parameters.page,
        parameters.perPage
    )
}

data class GetUsersParams(
    val q: String,
    val page: Int = 1,
    val perPage: Int = 20
)

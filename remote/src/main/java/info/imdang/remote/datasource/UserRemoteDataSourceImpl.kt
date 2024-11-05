package info.imdang.remote.datasource

import info.imdang.data.datasource.UserRemoteDataSource
import info.imdang.data.model.user.UserResponse
import info.imdang.remote.service.UserService
import javax.inject.Inject

internal class UserRemoteDataSourceImpl @Inject constructor(
    private val testService: UserService
) : UserRemoteDataSource {

    override suspend fun getUsers(
        q: String,
        page: Int,
        perPage: Int
    ): UserResponse = testService.getUsers(q, page, perPage)
}

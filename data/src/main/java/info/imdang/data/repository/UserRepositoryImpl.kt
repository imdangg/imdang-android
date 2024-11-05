package info.imdang.data.repository

import info.imdang.data.datasource.UserRemoteDataSource
import info.imdang.domain.model.UserDto
import info.imdang.domain.repository.UserRepository
import javax.inject.Inject

internal class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun getUsers(
        q: String,
        page: Int,
        perPage: Int
    ): UserDto = userRemoteDataSource.getUsers(q, page, perPage).mapper()
}

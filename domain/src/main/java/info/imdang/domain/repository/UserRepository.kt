package info.imdang.domain.repository

import info.imdang.domain.model.UserDto

interface UserRepository {

    suspend fun getUsers(
        q: String,
        page: Int = 1,
        perPage: Int = 20
    ): UserDto
}

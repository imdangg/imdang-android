package info.imdang.data.datasource

import info.imdang.data.model.user.UserResponse

interface UserRemoteDataSource {

    suspend fun getUsers(
        q: String,
        page: Int = 1,
        perPage: Int = 20
    ): UserResponse
}

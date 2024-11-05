package info.imdang.remote.service

import info.imdang.data.model.user.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface UserService {

    /** Github 유저 목록 요청 */
    @GET("search/users")
    suspend fun getUsers(
        @Query("q") q: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 20
    ): UserResponse
}

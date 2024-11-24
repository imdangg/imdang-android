package info.imdang.data.datasource

import info.imdang.data.model.request.auth.LoginRequest
import info.imdang.data.model.response.auth.LoginResponse

interface AuthRemoteDataSource {

    suspend fun kakaoLogin(loginRequest: LoginRequest): LoginResponse

    suspend fun googleLogin(loginRequest: LoginRequest): LoginResponse
}

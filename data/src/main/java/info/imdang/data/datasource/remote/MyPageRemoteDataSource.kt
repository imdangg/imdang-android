package info.imdang.data.datasource.remote

import info.imdang.data.model.request.auth.WithdrawRequest
import info.imdang.data.model.response.mypage.MyPageResponse
import retrofit2.Response

interface MyPageRemoteDataSource {

    suspend fun getMyPageInfo(): MyPageResponse

    suspend fun withdrawalKakao(withdrawRequest: WithdrawRequest): Response<Unit>

    suspend fun withdrawalGoogle(withdrawRequest: WithdrawRequest): Response<Unit>
}

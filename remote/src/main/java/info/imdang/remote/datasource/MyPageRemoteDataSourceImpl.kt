package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.MyPageRemoteDataSource
import info.imdang.data.model.request.auth.WithdrawRequest
import info.imdang.data.model.response.mypage.MyPageResponse
import info.imdang.remote.service.MyPageService
import retrofit2.Response
import javax.inject.Inject

internal class MyPageRemoteDataSourceImpl @Inject constructor(
    private val myPageService: MyPageService
) : MyPageRemoteDataSource {

    override suspend fun getMyPageInfo(): MyPageResponse = myPageService.getMyPageInfo()

    override suspend fun withdrawalKakao(withdrawRequest: WithdrawRequest): Response<Unit> =
        myPageService.withdrawalKakao(withdrawRequest)

    override suspend fun withdrawalGoogle(withdrawRequest: WithdrawRequest): Response<Unit> =
        myPageService.withdrawalGoogle(withdrawRequest)
}

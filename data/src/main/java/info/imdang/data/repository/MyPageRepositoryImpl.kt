package info.imdang.data.repository

import info.imdang.data.datasource.remote.MyPageRemoteDataSource
import info.imdang.data.model.request.auth.WithdrawRequest
import info.imdang.domain.model.mypage.MyPageDto
import info.imdang.domain.repository.MyPageRepository
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myPageRemoteDataSource: MyPageRemoteDataSource
) : MyPageRepository {

    override suspend fun getMyPageInfo(): MyPageDto =
        myPageRemoteDataSource.getMyPageInfo().mapper()

    override suspend fun withdrawalKakao(accessToken: String) {
        myPageRemoteDataSource.withdrawalKakao(
            WithdrawRequest(token = accessToken)
        )
    }

    override suspend fun withdrawalGoogle(accessToken: String) {
        myPageRemoteDataSource.withdrawalGoogle(
            WithdrawRequest(token = accessToken)
        )
    }
}

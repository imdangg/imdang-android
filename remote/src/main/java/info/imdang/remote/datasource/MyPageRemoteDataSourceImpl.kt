package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.MyPageRemoteDataSource
import info.imdang.data.model.response.mypage.MyPageResponse
import info.imdang.remote.service.MyPageService
import javax.inject.Inject

internal class MyPageRemoteDataSourceImpl @Inject constructor(
    private val myPageService: MyPageService
) : MyPageRemoteDataSource {

    override suspend fun getMyPageInfo(
        memberId: String
    ): MyPageResponse = myPageService.getMyPageInfo(
        memberId = memberId
    )
}

package info.imdang.data.repository

import info.imdang.data.datasource.remote.MyPageRemoteDataSource
import info.imdang.domain.model.mypage.MyPageDto
import info.imdang.domain.repository.MyPageRepository
import javax.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myPageRemoteDataSource: MyPageRemoteDataSource
) : MyPageRepository {

    override suspend fun getMyPageInfo(
        memberId: String
    ): MyPageDto = myPageRemoteDataSource.getMyPageInfo(
        memberId = memberId
    ).mapper()
}

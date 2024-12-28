package info.imdang.data.repository

import info.imdang.data.datasource.remote.GoogleRemoteDataSource
import info.imdang.domain.model.google.GoogleAccessTokenDto
import info.imdang.domain.repository.GoogleRepository
import javax.inject.Inject

internal class GoogleRepositoryImpl @Inject constructor(
    private val googleRemoteDataSource: GoogleRemoteDataSource
) : GoogleRepository {

    override suspend fun getAccessToken(code: String): GoogleAccessTokenDto {
        return googleRemoteDataSource.getAccessToken(code).mapper()
    }
}

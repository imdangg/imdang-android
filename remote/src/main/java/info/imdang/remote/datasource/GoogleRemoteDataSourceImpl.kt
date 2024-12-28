package info.imdang.remote.datasource

import info.imdang.data.datasource.remote.GoogleRemoteDataSource
import info.imdang.data.model.response.google.GoogleAccessTokenResponse
import info.imdang.remote.service.GoogleService
import javax.inject.Inject

internal class GoogleRemoteDataSourceImpl @Inject constructor(
    private val googleService: GoogleService
) : GoogleRemoteDataSource {

    override suspend fun getAccessToken(code: String): GoogleAccessTokenResponse =
        googleService.getAccessToken(code = code)
}

package info.imdang.data.datasource.remote

import info.imdang.data.model.response.google.GoogleAccessTokenResponse

interface GoogleRemoteDataSource {

    suspend fun getAccessToken(code: String): GoogleAccessTokenResponse
}

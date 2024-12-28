package info.imdang.domain.repository

import info.imdang.domain.model.google.GoogleAccessTokenDto

interface GoogleRepository {

    suspend fun getAccessToken(code: String): GoogleAccessTokenDto
}

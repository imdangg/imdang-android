package info.imdang.data.model.response.google

import com.google.gson.annotations.SerializedName
import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.google.GoogleAccessTokenDto

data class GoogleAccessTokenResponse(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("expires_in") val expiresIn: Int,
    val scope: String,
    @SerializedName("token_type") val tokenType: Boolean,
    @SerializedName("id_token") val idToken: String
) : DataToDomainMapper<GoogleAccessTokenDto> {
    override fun mapper(): GoogleAccessTokenDto = GoogleAccessTokenDto(
        accessToken = accessToken,
        expiresIn = expiresIn,
        scope = scope,
        tokenType = tokenType,
        idToken = idToken
    )
}

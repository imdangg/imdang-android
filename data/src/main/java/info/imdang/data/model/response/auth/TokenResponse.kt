package info.imdang.data.model.response.auth

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.auth.TokenDto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
) : DataToDomainMapper<TokenDto> {
    override fun mapper(): TokenDto = TokenDto(
        accessToken = accessToken,
        refreshToken = refreshToken,
        expiresIn = expiresIn
    )
}

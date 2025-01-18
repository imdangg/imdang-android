package info.imdang.data.model.response.auth

import info.imdang.data.mapper.DataToDomainMapper
import info.imdang.domain.model.auth.LoginDto

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val memberId: String,
    val joined: Boolean
) : DataToDomainMapper<LoginDto> {
    override fun mapper(): LoginDto = LoginDto(
        accessToken = accessToken,
        refreshToken = refreshToken,
        expiresIn = expiresIn,
        memberId = memberId,
        joined = joined
    )
}

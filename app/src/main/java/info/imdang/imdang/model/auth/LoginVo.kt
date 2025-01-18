package info.imdang.imdang.model.auth

import info.imdang.domain.model.auth.LoginDto

data class LoginVo(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val memberId: String,
    val isJoined: Boolean
)

fun LoginDto.mapper(): LoginVo = LoginVo(
    accessToken = accessToken,
    refreshToken = refreshToken,
    expiresIn = expiresIn,
    memberId = memberId,
    isJoined = joined
)

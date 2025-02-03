package info.imdang.domain.model.auth

data class TokenDto(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
)

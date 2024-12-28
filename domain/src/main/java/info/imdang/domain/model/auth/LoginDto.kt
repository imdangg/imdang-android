package info.imdang.domain.model.auth

data class LoginDto(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val joined: Boolean
)

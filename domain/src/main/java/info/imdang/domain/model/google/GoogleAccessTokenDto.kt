package info.imdang.domain.model.google

data class GoogleAccessTokenDto(
    val accessToken: String,
    val expiresIn: Int,
    val scope: String,
    val tokenType: Boolean,
    val idToken: String
)

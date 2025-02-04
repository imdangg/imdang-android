package info.imdang.data.model.request.auth

data class ReissueTokenRequest(
    val memberId: String,
    val refreshToken: String
)

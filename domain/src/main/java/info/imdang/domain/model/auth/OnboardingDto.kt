package info.imdang.domain.model.auth

data class OnboardingDto(
    val nickname: String,
    val birthDate: String,
    val gender: String,
    val deviceToken: String
)

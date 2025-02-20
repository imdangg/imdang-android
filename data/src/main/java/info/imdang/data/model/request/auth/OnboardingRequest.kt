package info.imdang.data.model.request.auth

import info.imdang.domain.model.auth.OnboardingDto

data class OnboardingRequest(
    val nickname: String,
    val birthDate: String,
    val gender: String?,
    val deviceToken: String
) {
    companion object {
        fun fromDomain(onboardingDto: OnboardingDto): OnboardingRequest = OnboardingRequest(
            nickname = onboardingDto.nickname,
            birthDate = onboardingDto.birthDate,
            gender = onboardingDto.gender,
            deviceToken = onboardingDto.deviceToken
        )
    }
}

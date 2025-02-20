package info.imdang.imdang.ui.join

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import info.imdang.domain.model.auth.OnboardingDto
import info.imdang.domain.usecase.auth.OnboardingJoinUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasicInformationViewModel @Inject constructor(
    private val onboardingJoinUseCase: OnboardingJoinUseCase
) : ViewModel() {

    private val _isNicknameValid = MutableStateFlow(false)
    val isNicknameValid = _isNicknameValid.asStateFlow()

    private val _isBirthDateValid = MutableStateFlow(false)
    val isBirthDateValid = _isBirthDateValid.asStateFlow()

    private val finalNickNameValid = MutableStateFlow(false)

    val finalBirthDateValid = MutableStateFlow(false)

    private val finalGenderValid = MutableStateFlow(false)

    val isFinalButtonEnabled = finalNickNameValid
//    todo : 추후에 기존꺼 쓸 수 도 있음
//    val isFinalButtonEnabled =
//        combine(
//            finalNickNameValid,
//            finalBirthDateValid,
//            finalGenderValid
//        ) { nicknameValid, birthDateValid, genderValid ->
//            nicknameValid && birthDateValid && genderValid
//        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    private val _isRequireTermChecked = MutableStateFlow(false)
    val isRequireTermChecked = _isRequireTermChecked.asStateFlow()

    private val _isRequirePrivacyTermChecked = MutableStateFlow(false)
    val isRequirePrivacyTermChecked = _isRequirePrivacyTermChecked.asStateFlow()

    private val _isOptionalMarketingAndNotificationsChecked = MutableStateFlow(false)
    val isOptionalMarketingAndNotificationsChecked =
        _isOptionalMarketingAndNotificationsChecked.asStateFlow()

    private val _isTotallyAgreeChecked = MutableStateFlow(false)
    val isTotallyAgreeChecked = _isTotallyAgreeChecked.asStateFlow()

    private val requiredTermsState = listOf(_isRequireTermChecked, _isRequirePrivacyTermChecked)

    val isAgreeAndContinueButtonEnabled = combine(
        _isRequireTermChecked,
        _isRequirePrivacyTermChecked,
        _isTotallyAgreeChecked
    ) { isRequireTermChecked, isRequirePrivacyTermChecked, isTotallyAgreeChecked ->
        (isRequireTermChecked && isRequirePrivacyTermChecked) || isTotallyAgreeChecked
    }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun updateNicknameValid(isValid: Boolean) {
        _isNicknameValid.value = isValid
    }

    fun updateFinalNickNameValid(isValid: Boolean) {
        finalNickNameValid.value = isValid
    }

    fun updateBirthDateValid(isValid: Boolean) {
        _isBirthDateValid.value = isValid
    }

    fun updateFinalBirthDateValid(isValid: Boolean) {
        finalBirthDateValid.value = isValid
    }

    fun updateFinalGenderValid(isValid: Boolean) {
        finalGenderValid.value = isValid
    }

    fun onboardingJoin(nickname: String, birthDate: String, gender: String?, deviceToken: String) {
        val onboardingDto = OnboardingDto(
            nickname = nickname,
            birthDate = birthDate,
            gender = gender,
            deviceToken = deviceToken
        )

        viewModelScope.launch {
            onboardingJoinUseCase(onboardingDto)
        }
    }

    fun updateRequireTermChecked(isChecked: Boolean) {
        _isRequireTermChecked.value = isChecked
        updateTotallyAgreeStatus()
    }

    fun updateRequirePrivacyTermChecked(isChecked: Boolean) {
        _isRequirePrivacyTermChecked.value = isChecked
        updateTotallyAgreeStatus()
    }

    fun updateOptionalMarketingAndNotificationsChecked(isChecked: Boolean) {
        _isOptionalMarketingAndNotificationsChecked.value = isChecked
        updateTotallyAgreeStatus()
    }

    fun updateTotallyAgreeChecked(isChecked: Boolean) {
        _isTotallyAgreeChecked.value = isChecked
        _isRequireTermChecked.value = isChecked
        _isRequirePrivacyTermChecked.value = isChecked
        _isOptionalMarketingAndNotificationsChecked.value = isChecked
    }

    private fun updateTotallyAgreeStatus() {
        _isTotallyAgreeChecked.value = requiredTermsState.all { it.value } &&
            _isOptionalMarketingAndNotificationsChecked.value
    }
}

package info.imdang.imdang.ui.join

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class BasicInformationViewModel @Inject constructor() : ViewModel() {

    private val _isNicknameValid = MutableStateFlow(false)
    val isNicknameValid = _isNicknameValid.asStateFlow()

    private val _isBirthDateValid = MutableStateFlow(false)
    val isBirthDateValid = _isBirthDateValid.asStateFlow()

    private val finalNickNameValid = MutableStateFlow(false)

    private val finalBirthDateValid = MutableStateFlow(false)

    private val finalGenderValid = MutableStateFlow(false)

    val isFinalButtonEnabled =
        combine(
            finalNickNameValid,
            finalBirthDateValid,
            finalGenderValid
        ) { nicknameValid, birthDateValid, genderValid ->
            nicknameValid && birthDateValid && genderValid
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

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
}

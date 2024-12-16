package info.imdang.imdang.ui.join

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.ext.birthDateValidation
import info.imdang.imdang.common.ext.hideKeyboard
import info.imdang.imdang.common.ext.nicknameValidation
import info.imdang.imdang.common.ext.setMargin
import info.imdang.imdang.common.ext.startAndFinishActivity
import info.imdang.imdang.databinding.ActivityBasicInformationBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasicInformationActivity :
    BaseActivity<ActivityBasicInformationBinding>(R.layout.activity_basic_information) {

    private val basicInformationViewModel by viewModels<BasicInformationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        init()
    }

    override fun onShowKeyboard(keyboardHeight: Int) {
        super.onShowKeyboard(keyboardHeight)

        with(binding.btnComplete) {
            setMargin(left = 0, right = 0, top = 0, bottom = 0)
            text = getString(info.imdang.component.R.string.confirm)
        }
    }

    override fun onHideKeyboard() {
        super.onHideKeyboard()

        with(binding.btnComplete) {
            setMargin(left = 20, right = 20, top = 0, bottom = 40)
            text = getString(info.imdang.component.R.string.complete)
            binding.etNickName.clearFocus()
            binding.etBirthDate.clearFocus()
        }
    }

    private fun init() {
        with(binding) {
            viewModel = basicInformationViewModel
            ivBack.setOnClickListener {
                finish()
            }

            etNickName.nicknameValidation(
                maxLength = 10,
                targetTextView = tvNicknameCount,
                errorTextView = tvNicknameError,
                statusImageView = ivNickName,
                invalidIconResId = info.imdang.component.R.drawable.ic_exclamation_mark_red,
                parentLayout = tlNickname,
                onValidStateChanged = { isValid ->
                    basicInformationViewModel.updateNicknameValid(isValid)
                    if (!isValid) {
                        basicInformationViewModel.updateFinalNickNameValid(false)
                    }
                }
            )
            etBirthDate.birthDateValidation(
                errorTextView = tvBirthDateError,
                statusImageView = ivBirthDate,
                invalidIconResId = info.imdang.component.R.drawable.ic_exclamation_mark_red,
                parentLayout = tlBirthDate,
                onValidStateChanged = { isValid ->
                    basicInformationViewModel.updateBirthDateValid(isValid)
                    if (!isValid) {
                        basicInformationViewModel.updateFinalBirthDateValid(false)
                    }
                }
            )

            etNickName.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    lifecycleScope.launch {
                        basicInformationViewModel.isNicknameValid.collect { isValid ->
                            btnComplete.isEnabled = isValid
                            checkButtonEnabled()
                        }
                    }
                } else {
                    lifecycleScope.launch {
                        basicInformationViewModel.isFinalButtonEnabled.collect { isValid ->
                            btnComplete.isEnabled = isValid
                            checkButtonEnabled()
                        }
                    }
                }
            }

            etBirthDate.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    lifecycleScope.launch {
                        basicInformationViewModel.isBirthDateValid.collect { isValid ->
                            btnComplete.isEnabled = isValid
                            checkButtonEnabled()
                        }
                    }
                } else {
                    lifecycleScope.launch {
                        basicInformationViewModel.isFinalButtonEnabled.collect { isValid ->
                            btnComplete.isEnabled = isValid
                            checkButtonEnabled()
                        }
                    }
                }
            }

            btnGenderMan.setOnClickListener {
                updateGenderSelection(true)
            }

            btnGenderWoman.setOnClickListener {
                updateGenderSelection(false)
            }

            lifecycleScope.launch {
                basicInformationViewModel.isFinalButtonEnabled.collect { isValid ->
                    btnComplete.isEnabled = isValid
                    checkButtonEnabled()
                }
            }

            btnComplete.setOnClickListener {
                if (isVisibleKeyboard) {
                    when {
                        etNickName.isFocused -> {
                            val isNicknameValid = basicInformationViewModel.isNicknameValid.value

                            if (isNicknameValid) {
                                ivNickName.setImageResource(
                                    info.imdang.component.R.drawable.ic_check
                                )
                                basicInformationViewModel.updateFinalNickNameValid(true)
                                hideKeyboard()
                            } else {
                                ivNickName.setImageResource(
                                    info.imdang.component.R.drawable.ic_exclamation_mark_red
                                )
                                basicInformationViewModel.updateFinalNickNameValid(false)
                            }
                        }

                        etBirthDate.isFocused -> {
                            val isBirthDateValid = basicInformationViewModel.isBirthDateValid.value
                            if (isBirthDateValid) {
                                ivBirthDate.setImageResource(
                                    info.imdang.component.R.drawable.ic_check
                                )
                                basicInformationViewModel.updateFinalBirthDateValid(true)
                                hideKeyboard()
                            } else {
                                ivBirthDate.setImageResource(
                                    info.imdang.component.R.drawable.ic_exclamation_mark_red
                                )
                                basicInformationViewModel.updateFinalBirthDateValid(false)
                            }
                        }
                    }
                } else {
                    setResult(RESULT_OK)
                    startAndFinishActivity<JoinCompleteActivity>()
                }
            }
        }
    }

    private fun checkButtonEnabled() {
        binding.btnComplete.setTextColor(
            if (binding.btnComplete.isEnabled) {
                getColor(info.imdang.component.R.color.white)
            } else {
                getColor(info.imdang.component.R.color.gray_500)
            }
        )
    }

    private fun updateGenderSelection(isManSelected: Boolean) {
        with(binding) {
            btnGenderMan.background = ContextCompat.getDrawable(
                this@BasicInformationActivity,
                if (isManSelected) {
                    info.imdang.component.R.drawable.sr_orange50_orange500_all8
                } else {
                    info.imdang.component.R.drawable.sr_white_gray100_all8
                }
            )
            btnGenderMan.setTextColor(
                if (isManSelected) {
                    getColor(info.imdang.component.R.color.orange_500)
                } else {
                    getColor(info.imdang.component.R.color.gray_400)
                }
            )

            btnGenderWoman.background = ContextCompat.getDrawable(
                this@BasicInformationActivity,
                if (!isManSelected) {
                    info.imdang.component.R.drawable.sr_orange50_orange500_all8
                } else {
                    info.imdang.component.R.drawable.sr_white_gray100_all8
                }
            )
            btnGenderWoman.setTextColor(
                if (!isManSelected) {
                    getColor(info.imdang.component.R.color.orange_500)
                } else {
                    getColor(info.imdang.component.R.color.gray_400)
                }
            )

            ivGender.setImageResource(info.imdang.component.R.drawable.ic_check)
            basicInformationViewModel.updateFinalGenderValid(true)
        }
    }
}

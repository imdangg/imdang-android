package info.imdang.imdang.ui.join

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseActivity
import info.imdang.imdang.common.ext.birthDateValidation
import info.imdang.imdang.common.ext.hideKeyboard
import info.imdang.imdang.common.ext.nicknameValidation
import info.imdang.imdang.common.ext.setMargin
import info.imdang.imdang.common.ext.startAndFinishActivity
import info.imdang.imdang.databinding.ActivityBasicInformationBinding
import info.imdang.imdang.ui.join.bottomsheet.ServiceTermBottomSheet
import info.imdang.imdang.ui.join.bottomsheet.ServiceTermBottomSheetListener
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BasicInformationActivity :
    BaseActivity<ActivityBasicInformationBinding>(R.layout.activity_basic_information) {

    private val basicInformationViewModel by viewModels<BasicInformationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setName("기본정보입력")
        init()
        showServiceTermBottomSheet()
    }

    override fun onShowKeyboard(keyboardHeight: Int) {
        super.onShowKeyboard(keyboardHeight)

        with(binding.btnComplete) {
            setMargin(left = 0, right = 0, top = 0, bottom = 0)
            text = getString(info.imdang.component.R.string.confirm)

            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(
                    if (isEnabled) {
                        getColor(info.imdang.component.R.color.orange_500)
                    } else {
                        getColor(
                            info.imdang.component.R.color.gray_100
                        )
                    }
                )
                cornerRadius = 0f
            }
            background = drawable
        }
    }

    override fun onHideKeyboard() {
        super.onHideKeyboard()

        with(binding.btnComplete) {
            setMargin(left = 20, right = 20, top = 0, bottom = 40)
            text = getString(info.imdang.component.R.string.complete)

            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(
                    if (isEnabled) {
                        getColor(info.imdang.component.R.color.orange_500)
                    } else {
                        getColor(
                            info.imdang.component.R.color.gray_100
                        )
                    }
                )
                cornerRadius =
                    resources.getDimension(info.imdang.component.R.dimen.default_corner_radius)
            }
            background = drawable

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
                            updateButtonState(isValid)
                            checkButtonEnabled()
                        }
                    }
                } else {
                    lifecycleScope.launch {
                        basicInformationViewModel.isFinalButtonEnabled.collect { isValid ->
                            updateButtonState(isValid)
                            checkButtonEnabled()
                        }
                    }
                }
            }

            tlBirthDate.setEndIconOnClickListener {
                etBirthDate.text?.clear()
                if (etBirthDate.isFocused) {
                    updateButtonState(basicInformationViewModel.isBirthDateValid.value)
                    checkButtonEnabled()
                } else {
                    updateButtonState(basicInformationViewModel.isFinalButtonEnabled.value)
                    checkButtonEnabled()
                }
            }

            etBirthDate.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    lifecycleScope.launch {
                        basicInformationViewModel.isBirthDateValid.collect { isValid ->
                            updateButtonState(isValid)
                            checkButtonEnabled()
                        }
                    }
                } else {
                    lifecycleScope.launch {
                        basicInformationViewModel.isFinalButtonEnabled.collect { isValid ->
                            updateButtonState(isValid)
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
                    val nickname = binding.etNickName.text.toString()
                    var birthDate = binding.etBirthDate.text.toString()
                    val gender = when {
                        binding.btnGenderMan.background.constantState ==
                            ContextCompat.getDrawable(
                                this@BasicInformationActivity,
                                info.imdang.component.R.drawable.sr_orange50_orange500_all8
                            )?.constantState -> "MALE"

                        binding.btnGenderWoman.background.constantState ==
                            ContextCompat.getDrawable(
                                this@BasicInformationActivity,
                                info.imdang.component.R.drawable.sr_orange50_orange500_all8
                            )?.constantState -> "FEMALE"

                        else -> null
                    }

                    val isBirthDateValid = basicInformationViewModel.finalBirthDateValid.value

                    if (!isBirthDateValid) {
                        birthDate = ""
                    }

                    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val deviceToken = task.result
                            basicInformationViewModel.onboardingJoin(
                                nickname,
                                birthDate,
                                gender,
                                deviceToken
                            )

                            setResult(RESULT_OK)
                            startAndFinishActivity<JoinCompleteActivity>()
                        } else {
                            Log.e("##", "Fetching FCM registration token failed", task.exception)
                        }
                    }
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

    private fun updateButtonState(isEnabled: Boolean) {
        with(binding.btnComplete) {
            this.isEnabled = isEnabled
            val drawable = GradientDrawable().apply {
                shape = GradientDrawable.RECTANGLE
                setColor(
                    if (isEnabled) {
                        getColor(info.imdang.component.R.color.orange_500)
                    } else {
                        getColor(
                            info.imdang.component.R.color.gray_100
                        )
                    }
                )
                cornerRadius =
                    if (isVisibleKeyboard) {
                        0f
                    } else {
                        resources.getDimension(info.imdang.component.R.dimen.default_corner_radius)
                    }
            }
            background = drawable
        }
    }

    private fun showServiceTermBottomSheet() {
        ServiceTermBottomSheet.instance(
            listener = object : ServiceTermBottomSheetListener {
                override fun onClickAgreeContinueButton() {}
            }
        ).show(
            supportFragmentManager,
            ServiceTermBottomSheet::class.java.simpleName
        )
    }
}

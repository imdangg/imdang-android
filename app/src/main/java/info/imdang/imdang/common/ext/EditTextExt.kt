package info.imdang.imdang.common.ext

import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.textfield.TextInputLayout
import java.util.Calendar

fun EditText.nicknameValidation(
    maxLength: Int,
    targetTextView: TextView,
    errorTextView: TextView,
    statusImageView: ImageView,
    invalidIconResId: Int,
    parentLayout: TextInputLayout,
    onValidStateChanged: (Boolean) -> Unit
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val currentLength = s?.length ?: 0
            targetTextView.apply {
                text = " ($currentLength/$maxLength)"
                visibility = if (currentLength > 0) View.VISIBLE else View.GONE
            }
        }

        override fun afterTextChanged(s: Editable?) {
            val currentLength = s?.length ?: 0

            statusImageView.visibility = View.VISIBLE

            when (currentLength) {
                0 -> {
                    errorTextView.text =
                        context.getString(info.imdang.component.R.string.nickname_error_type_empty)
                    errorTextView.visibility = View.VISIBLE
                    statusImageView.setImageResource(invalidIconResId)
                    this@nicknameValidation.setBackgroundResource(
                        info.imdang.component.R.drawable.sr_white_red_all8
                    )
                    parentLayout.cursorColor = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context,
                            info.imdang.component.R.color.red
                        )
                    )
                    onValidStateChanged(false)
                }

                1 -> {
                    errorTextView.text =
                        context.getString(info.imdang.component.R.string.nickname_error_type_count)
                    errorTextView.visibility = View.VISIBLE
                    statusImageView.setImageResource(invalidIconResId)
                    this@nicknameValidation.setBackgroundResource(
                        info.imdang.component.R.drawable.sr_white_red_all8
                    )
                    parentLayout.cursorColor = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context,
                            info.imdang.component.R.color.red
                        )
                    )
                    onValidStateChanged(false)
                }

                else -> {
                    errorTextView.visibility = View.GONE
                    statusImageView.setImageDrawable(null)
                    this@nicknameValidation.setBackgroundResource(
                        info.imdang.component.R.drawable.bg_basic_information_edittext
                    )
                    parentLayout.cursorColor = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context,
                            info.imdang.component.R.color.orange_500
                        )
                    )
                    onValidStateChanged(true)
                }
            }
        }
    })
}

fun EditText.birthDateValidation(
    errorTextView: TextView,
    statusImageView: ImageView,
    invalidIconResId: Int,
    parentLayout: TextInputLayout,
    onValidStateChanged: (Boolean) -> Unit
) {
    this.addTextChangedListener(object : TextWatcher {
        private var isEditing = false
        private var deletingDot = false

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            deletingDot = count == 1 && s?.getOrNull(start) == '.'
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            statusImageView.visibility = View.VISIBLE

            if (s.isNullOrEmpty()) {
                errorTextView.text =
                    context.getString(info.imdang.component.R.string.birth_date_error_type_empty)
                errorTextView.visibility = View.VISIBLE
                statusImageView.setImageResource(invalidIconResId)
                this@birthDateValidation.setBackgroundResource(
                    info.imdang.component.R.drawable.sr_white_red_all8
                )
                parentLayout.cursorColor = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        context,
                        info.imdang.component.R.color.red
                    )
                )
                onValidStateChanged(false)
                return
            }

            if (isEditing) return

            isEditing = true
            val input = s.toString().replace(".", "")

            val formatted = when {
                input.length > 8 -> input.substring(0, 8)
                input.length >= 6 -> "${input.substring(0, 4)}.${
                    input.substring(
                        4,
                        6
                    )
                }.${input.substring(6)}"

                input.length >= 4 -> "${input.substring(0, 4)}.${input.substring(4)}"
                else -> input
            }

            if (deletingDot) {
                isEditing = false
                return
            }

            if (s.toString() != formatted) {
                this@birthDateValidation.setText(formatted)
                this@birthDateValidation.setSelection(formatted.length)
            }

            val validationResult = validateBirthDate(formatted)

            when (validationResult) {
                ValidationResult.VALID -> {
                    errorTextView.visibility = View.GONE
                    statusImageView.setImageDrawable(null)
                    this@birthDateValidation.setBackgroundResource(
                        info.imdang.component.R.drawable.bg_basic_information_edittext
                    )
                    parentLayout.cursorColor = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context,
                            info.imdang.component.R.color.orange_500
                        )
                    )
                    onValidStateChanged(true)
                }

                ValidationResult.INVALID_DATE -> {
                    errorTextView.text =
                        context.getString(
                            info.imdang.component.R.string.birth_date_error_type_erroneous
                        )
                    errorTextView.visibility = View.VISIBLE
                    statusImageView.setImageResource(invalidIconResId)
                    this@birthDateValidation.setBackgroundResource(
                        info.imdang.component.R.drawable.sr_white_red_all8
                    )
                    parentLayout.cursorColor = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context,
                            info.imdang.component.R.color.red
                        )
                    )
                    onValidStateChanged(false)
                }

                ValidationResult.FUTURE_DATE -> {
                    errorTextView.text =
                        context.getString(
                            info.imdang.component.R.string.birth_date_error_type_future
                        )
                    errorTextView.visibility = View.VISIBLE
                    statusImageView.setImageResource(invalidIconResId)
                    this@birthDateValidation.setBackgroundResource(
                        info.imdang.component.R.drawable.sr_white_red_all8
                    )
                    parentLayout.cursorColor = ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context,
                            info.imdang.component.R.color.red
                        )
                    )
                    onValidStateChanged(false)
                }
            }

            isEditing = false
        }

        private fun validateBirthDate(date: String): ValidationResult {
            if (!date.matches(
                    Regex("\\d{4}\\.\\d{2}\\.\\d{2}")
                )
            ) {
                return ValidationResult.INVALID_DATE
            }
            val parts = date.split(".")
            val year = parts[0].toIntOrNull() ?: return ValidationResult.INVALID_DATE
            val month = parts[1].toIntOrNull() ?: return ValidationResult.INVALID_DATE
            val day = parts[2].toIntOrNull() ?: return ValidationResult.INVALID_DATE

            val calendar = Calendar.getInstance()
            val currentYear = calendar.get(Calendar.YEAR)
            val currentMonth = calendar.get(Calendar.MONTH) + 1
            val currentDay = calendar.get(Calendar.DAY_OF_MONTH)

            if (year > currentYear) return ValidationResult.FUTURE_DATE

            if (year == currentYear) {
                if (month > currentMonth || (month == currentMonth && day > currentDay)) {
                    return ValidationResult.FUTURE_DATE
                }
            }

            if (month !in 1..12) return ValidationResult.INVALID_DATE
            val maxDays = when (month) {
                1, 3, 5, 7, 8, 10, 12 -> 31
                4, 6, 9, 11 -> 30
                2 -> if (isLeapYear(year)) 29 else 28
                else -> return ValidationResult.INVALID_DATE
            }
            if (day !in 1..maxDays) return ValidationResult.INVALID_DATE

            return ValidationResult.VALID
        }

        private fun isLeapYear(year: Int): Boolean {
            return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
        }
    })
}

private enum class ValidationResult {
    VALID,
    INVALID_DATE,
    FUTURE_DATE
}

package info.imdang.imdang.ui.common

import android.content.Context
import androidx.annotation.DrawableRes
import info.imdang.imdang.R
import info.imdang.imdang.base.BaseDialog
import info.imdang.imdang.common.bindingadapter.bindVisible
import info.imdang.imdang.databinding.DialogCommonBinding

fun Context.showCommonDialog(
    message: String,
    positiveButtonText: String,
    @DrawableRes iconDrawableResource: Int? = null,
    negativeButtonText: String? = null,
    subButtonText: String? = null,
    onClickPositiveButton: () -> Unit = {},
    onClickNegativeButton: () -> Unit = {},
    onClickSubButton: () -> Unit = {}
) {
    BaseDialog<DialogCommonBinding>(this, R.layout.dialog_common).onShow { binding ->
        with(binding) {
            ivDialogInfo.setImageResource(
                iconDrawableResource ?: info.imdang.component.R.drawable.ic_check_for_dialog
            )

            tvMessage.text = message

            tvNegativeButton.text = negativeButtonText
            tvNegativeButton.bindVisible(negativeButtonText != null)
            tvNegativeButton.setOnClickListener {
                dismiss()
                onClickNegativeButton()
            }

            tvPositiveButton.text = positiveButtonText
            tvPositiveButton.setOnClickListener {
                dismiss()
                onClickPositiveButton()
            }

            tvSubButton.text = subButtonText
            tvSubButton.bindVisible(subButtonText != null)
            tvSubButton.setOnClickListener {
                dismiss()
                onClickSubButton()
            }
        }
    }.show()
}

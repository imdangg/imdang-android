package info.imdang.imdang.common.bindingadapter

import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip

@BindingAdapter("bindChipBackground")
fun Chip.bindChipBackground(isSelected: Boolean) {
    val backgroundColor = if (isSelected) {
        context.getColor(info.imdang.component.R.color.orange_500)
    } else {
        context.getColor(info.imdang.component.R.color.white)
    }
    this.chipBackgroundColor = android.content.res.ColorStateList.valueOf(backgroundColor)

    val strokeColor = if (isSelected) {
        context.getColor(info.imdang.component.R.color.orange_500)
    } else {
        context.getColor(info.imdang.component.R.color.gray_100)
    }
    this.chipStrokeColor = android.content.res.ColorStateList.valueOf(strokeColor)

    val textColor = if (isSelected) {
        context.getColor(info.imdang.component.R.color.white)
    } else {
        context.getColor(info.imdang.component.R.color.gray_500)
    }
    this.setTextColor(textColor)
}

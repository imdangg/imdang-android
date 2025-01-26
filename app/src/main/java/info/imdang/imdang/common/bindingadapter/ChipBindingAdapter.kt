package info.imdang.imdang.common.bindingadapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import info.imdang.imdang.R
import info.imdang.imdang.model.insight.ExchangeRequestStatus

@BindingAdapter("bindInsightDetailChips")
fun ChipGroup.bindInsightDetailChips(chips: List<String>) {
    removeAllViews()
    chips.forEach {
        bindInsightDetailChip(it, false)
    }
}

@BindingAdapter(
    value = ["bindInsightDetailChip", "bindIsRemoveAllView"],
    requireAll = false
)
fun ChipGroup.bindInsightDetailChip(chip: String, isRemoveAllView: Boolean) {
    if (isRemoveAllView) removeAllViews()
    addView(createInsightDetailChip(context, chip))
}

@SuppressLint("InflateParams")
private fun createInsightDetailChip(context: Context, text: String): Chip {
    val chip = LayoutInflater
        .from(context)
        .inflate(R.layout.chip_insight_detail, null, false) as Chip
    return chip.apply {
        this.text = text
        if (text == "해당 없음" || text == "잘 모르겠어요") {
            setTextColor(context.getColor(info.imdang.component.R.color.gray_500))
            setChipStrokeColorResource(info.imdang.component.R.color.gray_100)
            chipBackgroundColor = android.content.res.ColorStateList.valueOf(
                context.getColor(info.imdang.component.R.color.gray_50)
            )
        }
    }
}

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

@BindingAdapter(
    value = ["bindChipStatus", "bindChipCount", "bindChipSelected"],
    requireAll = true
)
fun Chip.bindChipStatus(status: ExchangeRequestStatus, count: Int?, isSelected: Boolean) {
    val statusText = when (status) {
        ExchangeRequestStatus.PENDING -> {
            context.getString(info.imdang.component.R.string.waiting)
        }

        ExchangeRequestStatus.REJECTED -> {
            context.getString(info.imdang.component.R.string.refusal)
        }

        ExchangeRequestStatus.ACCEPTED -> {
            context.getString(info.imdang.component.R.string.exchange_completed)
        }
    }

    val text = if (isSelected) {
        "$statusText (${count ?: 0})"
    } else {
        statusText
    }
    this.text = text
}

package info.imdang.imdang.common.bindingadapter

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter
import info.imdang.imdang.model.insight.ExchangeRequestStatus

@BindingAdapter(
    value = ["bindText", "bindSpanText"],
    requireAll = true
)
fun TextView.bindSpan(text: String, spanText: String) {
    if (text.isEmpty() || text.indexOf(spanText) == -1) return

    this.text = SpannableStringBuilder(text).apply {
        setSpan(
            ForegroundColorSpan(
                context.getColor(info.imdang.component.R.color.orange_500)
            ),
            text.indexOf(spanText),
            text.indexOf(spanText) + spanText.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
}

@BindingAdapter(
    value = ["bindChipSelectedId", "bindChipSelectedCounts"],
    requireAll = true
)
fun TextView.bindChipAlarmDescription(chipId: Int, chipCounts: Map<ExchangeRequestStatus, Int>?) {
    val status = when (chipId) {
        1 -> ExchangeRequestStatus.PENDING
        2 -> ExchangeRequestStatus.REJECTED
        3 -> ExchangeRequestStatus.ACCEPTED
        else -> null
    }

    val count = status?.let { chipCounts?.get(it) } ?: 0

    val description = when (status) {
        ExchangeRequestStatus.PENDING -> if (count > 0) {
            context.getString(info.imdang.component.R.string.waiting_details_existence)
        } else {
            context.getString(info.imdang.component.R.string.waiting_details_nonexistence)
        }

        ExchangeRequestStatus.REJECTED -> if (count > 0) {
            context.getString(info.imdang.component.R.string.refusal_details_existence)
        } else {
            context.getString(info.imdang.component.R.string.refusal_details_nonexistence)
        }

        ExchangeRequestStatus.ACCEPTED -> if (count > 0) {
            context.getString(info.imdang.component.R.string.exchange_details_existence)
        } else {
            context.getString(info.imdang.component.R.string.exchange_details_nonexistence)
        }

        null -> "상태를 알 수 없습니다."
    }

    this.text = description
}

@BindingAdapter("bindIntToString")
fun TextView.bindIntToString(value: Int) {
    this.text = "${value}개"
}

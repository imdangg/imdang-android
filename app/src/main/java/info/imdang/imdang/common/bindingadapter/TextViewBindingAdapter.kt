package info.imdang.imdang.common.bindingadapter

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.databinding.BindingAdapter

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

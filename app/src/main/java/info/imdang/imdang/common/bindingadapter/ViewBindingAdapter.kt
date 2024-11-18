package info.imdang.imdang.common.bindingadapter

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("bindVisible")
fun View.bindVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

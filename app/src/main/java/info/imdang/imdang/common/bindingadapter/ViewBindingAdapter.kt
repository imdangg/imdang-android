package info.imdang.imdang.common.bindingadapter

import android.view.View
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.databinding.BindingAdapter
import info.imdang.imdang.common.ext.setMargin

@BindingAdapter("bindVisible")
fun View.bindVisible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

@BindingAdapter("bindMarginBottom")
fun View.bindMarginBottom(margin: Int) {
    setMargin(marginLeft, marginTop, marginRight, margin)
}

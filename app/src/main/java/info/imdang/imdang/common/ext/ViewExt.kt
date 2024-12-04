package info.imdang.imdang.common.ext

import android.view.View
import android.view.ViewGroup

fun View.setMargin(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
    layoutParams = (layoutParams as ViewGroup.MarginLayoutParams).apply {
        setMargins(
            context.dpToPx(left),
            context.dpToPx(top),
            context.dpToPx(right),
            context.dpToPx(bottom)
        )
    }
}

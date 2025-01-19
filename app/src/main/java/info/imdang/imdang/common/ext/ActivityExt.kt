package info.imdang.imdang.common.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let {
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

@SuppressLint("DiscouragedApi", "InternalInsetResource")
fun Activity.screenHeight(): Int {
    val statusBarId = resources.getIdentifier(
        "status_bar_height",
        "dimen",
        "android"
    )
    val statusBarHeight = if (statusBarId > 0) {
        resources.getDimensionPixelSize(statusBarId)
    } else {
        0
    }
    val screenHeight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        windowManager.currentWindowMetrics.bounds.height()
    } else {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getRealMetrics(displayMetrics)
        displayMetrics.heightPixels
    }
    return screenHeight - statusBarHeight
}

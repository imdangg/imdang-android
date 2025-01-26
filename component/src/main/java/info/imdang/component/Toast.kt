package info.imdang.component

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast

@SuppressLint("ServiceCast", "InflateParams")
fun Context.showToast(message: String) {
    val inflater = LayoutInflater.from(this)
    val layout = inflater.inflate(R.layout.toast_common, null).apply {
        findViewById<TextView>(R.id.tv_toast_message).apply {
            text = message

            val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val screenWidth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                windowManager.currentWindowMetrics.bounds.width()
            } else {
                val displayMetrics = DisplayMetrics()
                @Suppress("DEPRECATION")
                windowManager.defaultDisplay.getRealMetrics(displayMetrics)
                displayMetrics.widthPixels
            }
            val layoutParams = layoutParams
            layoutParams.width = screenWidth - Math.round(40 * resources.displayMetrics.density)
            this.layoutParams = layoutParams
        }
    }

    Toast(this).apply {
        duration = Toast.LENGTH_SHORT
        view = layout
        setGravity(
            Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL,
            0,
            Math.round(108 * resources.displayMetrics.density)
        )
    }.show()
}

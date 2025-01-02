package info.imdang.imdang.common.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle

inline fun <reified T : Activity> Context.startActivity(
    bundle: Bundle? = null,
    transitionBundle: Bundle? = null,
    flags: Int? = null
) {
    val intent = Intent(this, T::class.java)
    bundle?.let(intent::putExtras)
    flags?.let(intent::addFlags)
    startActivity(intent, transitionBundle)
}

inline fun <reified T : Activity> Context.startAndFinishActivity(
    bundle: Bundle? = null,
    transitionBundle: Bundle? = null
) {
    val intent = Intent(this, T::class.java)
    bundle?.let(intent::putExtras)
    startActivity(intent, transitionBundle)
    if (this is Activity) finish()
}

fun Context.dpToPx(dp: Int): Int {
    return Math.round(dp * resources.displayMetrics.density)
}

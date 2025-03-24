package info.imdang.imdang.common.util

import android.util.Log
import androidx.core.os.bundleOf
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent

private const val CATEGORY = "category"
private const val ACTION = "action"
private const val LABEL = "label"

fun logScreen(screenName: String, screenClass: String) {
    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
        param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
    }
    Log.i("EventTracker", "$screenClass($screenName)")
}

fun logEvent(
    event: String,
    category: String,
    action: String? = null,
    label: String? = null
) {
    Firebase.analytics.logEvent(
        event,
        bundleOf(
            CATEGORY to category,
            ACTION to action,
            LABEL to label
        )
    )
    Log.i("EventTracker", "$event($category, $action, $label)")
}

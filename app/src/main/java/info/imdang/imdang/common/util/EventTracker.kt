package info.imdang.imdang.common.util

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import com.google.firebase.analytics.logEvent

private const val CATEGORY = "category"
private const val ACTION = "action"
private const val LABEL = "label"

fun logScreen(screenName: String, screenClass: String) {
    Log.i("EventTracker", "$screenClass($screenName)")
    Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
        param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
        param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
    }
}

fun logEvent(
    event: String,
    category: String,
    action: String? = null,
    label: String? = null
) {
    Firebase.analytics.logEvent(event) {
        param(CATEGORY, category)
        action?.let { param(ACTION, it) }
        label?.let { param(LABEL, it) }
    }
}

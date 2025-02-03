package info.imdang.imdang

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application.ActivityLifecycleCallbacks
import android.os.Bundle

@SuppressLint("StaticFieldLeak")
object ActivityTracker : ActivityLifecycleCallbacks {

    private var _currentActivity: Activity? = null
    val currentActivity get() = _currentActivity

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        _currentActivity = activity
    }

    override fun onActivityStarted(activity: Activity) {
        _currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
        _currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, p1: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {
        if (currentActivity == activity) _currentActivity = null
    }
}

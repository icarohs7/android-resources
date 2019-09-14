package base.firebasecoreresources

import android.app.Activity
import base.coreresources.toplevel.onActivity
import com.google.firebase.analytics.FirebaseAnalytics
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object FirebaseRes {
    fun getAnalytics(act: Activity): FirebaseAnalytics = FirebaseAnalytics.getInstance(act)
    suspend fun getAnalytics(): FirebaseAnalytics {
        return FirebaseAnalytics.getInstance(suspendCoroutine<Activity> { cont ->
            onActivity { cont.resume(this) }
        })
    }
}
package base.firebasecoreresources

import android.app.Activity
import androidx.core.os.bundleOf
import base.corextresources.Corelibrary
import base.coreresources.UnoxAndroidArch
import base.coreresources.toplevel.onActivity
import com.github.icarohs7.unoxcore.UnoxCore
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber
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
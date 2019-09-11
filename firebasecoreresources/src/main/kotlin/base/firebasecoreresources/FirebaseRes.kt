package base.firebasecoreresources

import android.app.Activity
import androidx.core.os.bundleOf
import base.corextresources.Corelibrary
import base.coreresources.UnoxAndroidArch
import com.github.icarohs7.unoxcore.UnoxCore
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber

object FirebaseRes {
    fun init(activity: Activity) {
        val firebaseAnalytics = FirebaseAnalytics.getInstance(activity)
        UnoxCore.logger = {
            Timber.tag("UnoxCore").i("$it")
            firebaseAnalytics.logEvent("UnoxCore", bundleOf(
                    "message" to "$it".take(99),
                    "debug" to "${UnoxAndroidArch.isDebug}"
            ))
        }
        Corelibrary.log = { tag, msg ->
            Timber.tag(tag).i("$msg")
            firebaseAnalytics.logEvent(tag, bundleOf(
                    "message" to "$msg".take(99),
                    "debug" to "${UnoxAndroidArch.isDebug}"
            ))
        }
    }
}
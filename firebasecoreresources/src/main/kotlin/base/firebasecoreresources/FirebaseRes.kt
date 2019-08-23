package base.firebasecoreresources

import android.app.Activity
import androidx.core.os.bundleOf
import com.github.icarohs7.unoxcore.UnoxCore
import com.google.firebase.analytics.FirebaseAnalytics
import timber.log.Timber

object FirebaseRes {
    fun init(activity: Activity) {
        val firebaseAnalytics = FirebaseAnalytics.getInstance(activity)
        UnoxCore.logger = {
            Timber.tag("UnoxCore").i("$it")
            firebaseAnalytics.logEvent("UnoxCore", bundleOf(
                    "message" to "$it"
            ))
        }
    }
}
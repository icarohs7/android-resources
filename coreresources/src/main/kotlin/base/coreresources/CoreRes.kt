package base.coreresources

import com.github.icarohs7.unoxcore.UnoxCore
import com.github.icarohs7.unoxcore.delegates.mutableLazy
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

object CoreRes {
    /**
     * Initialize the library
     */
    fun init() {
        with(UnoxCore) {
            foregroundDispatcher = Dispatchers.Main
            logger = { Timber.tag("UnoxCore").i("$it") }
        }
    }

    /**
     * Animation used at the transition between activities
     */
    var defaultActivityTransition: ActivityTransitionAnimation by mutableLazy { ActivityTransitionAnimation() }

    /**
     * When set to true, every call to startActivity from
     * an activity will also finish it after the navigation
     */
    var finishActivityOnNavigate: Boolean by mutableLazy { false }

    /**
     * Ip used to check if the app has connection to
     * the internet
     */
    var connectionCheckAddress: String = "1.1.1.1:53"

    /**
     * Represent a set of animations used
     * when transitioning from an activity
     * to another
     */
    class ActivityTransitionAnimation(
            val enterRes: Int = R.anim.activity_transition_enter,
            val exitRes: Int = R.anim.activity_transition_exit
    )
}
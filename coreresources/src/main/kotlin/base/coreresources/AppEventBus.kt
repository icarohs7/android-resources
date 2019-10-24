package base.coreresources

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import arrow.core.Try
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

/**
 * Event buses used to comunicate with an activity
 * or fragment anywhere on the application
 */
object AppEventBus {
    private val activityOpStream = BroadcastChannel<FragmentActivity.() -> Unit>(1)
    private val activityOpFlow get() = activityOpStream.asFlow()

    /**
     * Object aggregating event emmiters
     */
    object In {

        /**
         * Run an operation within the scope of an activity
         */
        fun enqueueActivityOperation(fn: FragmentActivity.() -> Unit) {
            activityOpStream.offer(fn)
        }
    }

    /**
     * Object aggregating event output channels
     */
    object Out {
        /**
         * Subscribe the given activity to the stream of actions
         * being delegated to an activity on the application
         */
        fun subscribeActivity(activity: FragmentActivity): Unit = with(activity) {
            activityOpFlow
                .onEach { action -> Try { action() }.fold(Timber::e) {} }
                .launchIn(activity.lifecycleScope)
        }
    }
}
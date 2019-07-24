package base.appupdateresources.domain.extensions

import com.google.android.play.core.tasks.Task
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Suspend until the given task return
 */
suspend fun <T> Task<T>.await(): T {
    return suspendCoroutine { cont ->
        val resumeResult = {
            when (isSuccessful) {
                true -> cont.resume(result)
                false -> cont.resumeWithException(exception)
            }
        }

        if (isComplete) resumeResult()
        else addOnCompleteListener { resumeResult() }
    }
}
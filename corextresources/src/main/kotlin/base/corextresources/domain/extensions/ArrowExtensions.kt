package base.corextresources.domain.extensions

import arrow.core.Failure
import arrow.core.Success
import arrow.core.Try
import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Fail
import com.github.icarohs7.unoxcore.extensions.orThrow

/**
 * Intermediary operator used to enable double
 * bang on try instances
 */
operator fun <T> Try<T>.not(): TryWrapper<T> {
    return TryWrapper(this)
}

/**
 * Unwrap the given TryWrapper instance, throwing
 * an exception if it's a [Failure] or returning
 * the value if it's a [Success]
 */
operator fun <T> TryWrapper<T>.not(): T {
    return this.t.orThrow()
}

class TryWrapper<T>(val t: Try<T>)

/**
 * Unwrap all Try instances inside the list,
 * returning a list with the contained values
 * or throwing the first encountered exception
 */
fun <T> List<Try<T>>.unwrapAll(): List<T> {
    return map { !!it }
}

/**
 * Convert the given Try instance to
 * a MvRx Async
 */
fun <T> Try<T>.toAsync(): Async<T> {
    return when (this) {
        is Try.Failure -> Fail(exception)
        is Try.Success -> com.airbnb.mvrx.Success(value)
    }
}
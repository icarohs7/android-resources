package base.dataresources.data.db

import com.github.icarohs7.unoxandroidarch.extensions.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Get a flow emitting the
 * latest values from the given table
 */
fun <T> BaseDao<T>.flow(): Flow<List<T>> {
    return liveData().asFlow()
}

/**
 * [flow] with a transformation using a receiver
 */
fun <T, R> BaseDao<T>.flow(transform: suspend List<T>.() -> R): Flow<R> {
    return flow().map(transform)
}

package base.dataresources.data.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * [flow] with a transformation using a receiver
 */
fun <T, R> BaseDao<T>.flow(transform: suspend List<T>.() -> R): Flow<R> {
    return flow().map(transform)
}

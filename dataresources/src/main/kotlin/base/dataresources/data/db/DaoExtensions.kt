package base.dataresources.data.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.flow.asFlow

//TODO remove when room adds Flow support
/**
 * Flow converted from [BaseDao.flowable]
 */
fun <T> BaseDao<T>.flow(): Flow<List<T>> {
    return flowable().asFlow()
}


/**
 * [flow] with a transformation using a receiver
 */
fun <T, R> BaseDao<T>.flow(transform: suspend List<T>.() -> R): Flow<R> {
    return flowable().asFlow().map(transform)
}

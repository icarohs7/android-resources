package base.dataresources.data.db

import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.flow.asFlow

/**
 * Embed a mapping transformer to the flowable
 * of a given [BaseDao]
 */
fun <T, R : Any> BaseDao<T>.flowable(transformer: List<T>.() -> R): Flowable<R> {
    return flowable().map(transformer)
}

//TODO remove when room adds Flow support
/**
 * Flow converted from [BaseDao.flowable]
 */
fun <T> BaseDao<T>.flow(): Flow<List<T>> {
    return flowable().asFlow()
}
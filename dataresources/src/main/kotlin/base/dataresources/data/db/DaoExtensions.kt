package base.dataresources.data.db

import com.github.icarohs7.unoxandroidarch.extensions.asFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

//TODO remove when room adds Flow support
/**
 * Flow converted from [BaseDao.liveData]
 */
fun <T> BaseDao<T>.flow(): Flow<List<T>> {
    return liveData().asFlow(null)
}

/**
 * [flow] with a transformation using a receiver
 */
fun <T, R> BaseDao<T>.flow(transform: suspend List<T>.() -> R): Flow<R> {
    return liveData().asFlow(null).map(transform)
}

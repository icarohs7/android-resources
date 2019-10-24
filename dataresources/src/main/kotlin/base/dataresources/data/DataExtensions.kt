package base.dataresources.data

import arrow.core.Tuple2
import arrow.core.Tuple3
import arrow.core.Tuple4
import arrow.core.Tuple5
import arrow.core.Tuple6
import base.dataresources.data.db.BaseDao
import com.github.icarohs7.unoxcore.extensions.coroutines.plus
import kotlinx.coroutines.flow.Flow

/**
 * [kotlinx.coroutines.flow.combineLatest] using
 * the flowables of 2 repositories
 */
fun <A, B> combineDaoFlows(
    r1: BaseDao<A>,
    r2: BaseDao<B>
): Flow<Tuple2<List<A>, List<B>>> {
    return f(r1) + f(r2)
}

/**
 * [kotlinx.coroutines.flow.combineLatest] using
 * the flowables of 3 repositories
 */
fun <A, B, C> combineDaoFlows(
    r1: BaseDao<A>,
    r2: BaseDao<B>,
    r3: BaseDao<C>
): Flow<Tuple3<List<A>, List<B>, List<C>>> {
    return f(r1) + f(r2) + f(r3)
}

/**
 * [kotlinx.coroutines.flow.combineLatest] using
 * the flowables of 4 repositories
 */
fun <A, B, C, D> combineDaoFlows(
    r1: BaseDao<A>,
    r2: BaseDao<B>,
    r3: BaseDao<C>,
    r4: BaseDao<D>
): Flow<Tuple4<List<A>, List<B>, List<C>, List<D>>> {
    return f(r1) + f(r2) + f(r3) + f(r4)
}

/**
 * [kotlinx.coroutines.flow.combineLatest] using
 * the flowables of 5 repositories
 */
fun <A, B, C, D, E> combineDaoFlows(
    r1: BaseDao<A>,
    r2: BaseDao<B>,
    r3: BaseDao<C>,
    r4: BaseDao<D>,
    r5: BaseDao<E>
): Flow<Tuple5<List<A>, List<B>, List<C>, List<D>, List<E>>> {
    return f(r1) + f(r2) + f(r3) + f(r4) + f(r5)
}

/**
 * [kotlinx.coroutines.flow.combineLatest] using
 * the flowables of 6 repositories
 */
fun <A, B, C, D, E, F> combineDaoFlows(
    r1: BaseDao<A>,
    r2: BaseDao<B>,
    r3: BaseDao<C>,
    r4: BaseDao<D>,
    r5: BaseDao<E>,
    r6: BaseDao<F>
): Flow<Tuple6<List<A>, List<B>, List<C>, List<D>, List<E>, List<F>>> {
    return f(r1) + f(r2) + f(r3) + f(r4) + f(r5) + f(r6)
}

/**
 * Short hand syntax to get the liveData of the
 * given repository
 */
private fun <T> f(dao: BaseDao<T>): Flow<List<T>> = dao.flow()
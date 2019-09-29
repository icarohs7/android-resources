package base.graphqlresources.domain.extensions

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred

suspend fun <T> ApolloCall<T>.await(): Response<T> = toDeferred().await()
suspend fun <T> ApolloCall<T>.awaitData(): T? = toDeferred().await().data()
suspend fun <T> ApolloCall<T>.awaitDataThrowing(): T = with(await()) {
    if (hasErrors()) {
        val aggregatedErrors = errors().joinToString("\n") { it.message() ?: "${it.locations()}" }
        error(aggregatedErrors)
    }

    data()!!
}
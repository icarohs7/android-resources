package base.graphqlresources.domain.toplevel

import base.okhttpresources.OkHttpRes
import com.apollographql.apollo.ApolloClient

/**
 * Create an ApolloClient with the given serverUrl
 * as baseUrl of the server
 */
fun createApolloClient(serverUrl: String): ApolloClient? {
    return ApolloClient.builder()
        .okHttpClient(OkHttpRes.getDefaultHttpClient())
        .serverUrl(serverUrl)
        .build()
}
package base.retrofitresources.domain.toplevel

import base.okhttpresources.OkHttpRes
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.create

/**
 * Short hand version to create a retrofit instance
 * with the given [baseUrl] and with that create
 * an instance of a given service, which also
 * modifies the response, adding or setting the
 * "Content-Type" header to "application/json"
 */
inline fun <reified T> createRetrofitServiceJsonResponse(
        baseUrl: String,
        noinline clientExtraConfig: OkHttpClient.Builder.() -> Unit = {},
        builderExtraConfig: Retrofit.Builder.() -> Unit = {}
): T {
    val clientSetup: OkHttpClient.Builder.() -> Unit = {
        addInterceptor {
            val req = it.request()
            val res = it.proceed(req)
                    .newBuilder()
                    .header("Content-Type", "application/json")
                    .build()
            res
        }
    }

    return createRetrofitService(baseUrl, {
        clientSetup()
        clientExtraConfig()
    }, builderExtraConfig)
}

/**
 * Short hand version to create a retrofit instance
 * with the given [baseUrl] and with that create
 * an instance of a given service
 */
inline fun <reified T> createRetrofitService(
        baseUrl: String,
        noinline clientExtraConfig: OkHttpClient.Builder.() -> Unit = {},
        builderExtraConfig: Retrofit.Builder.() -> Unit = {}
): T {
    return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(RetrofitExtensions.getKotlinxSerializationConverter())
            .client(OkHttpRes.getDefaultHttpClient(clientExtraConfig))
            .apply(builderExtraConfig)
            .build()
            .create()
}

object RetrofitExtensions {
    fun getKotlinxSerializationConverter(): Converter.Factory {
        val contentType = "application/json".toMediaType()
        return Json
                .nonstrict
                .asConverterFactory(contentType)
    }
}
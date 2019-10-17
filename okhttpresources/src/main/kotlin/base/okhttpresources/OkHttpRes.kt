package base.okhttpresources

import android.app.Application
import base.coreresources.extensions.registerSingletonInDiContainer
import base.corextresources.domain.extensions.coroutines.PublishSubjectFlow
import base.corextresources.domain.toplevel.kget
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import kotlinx.coroutines.flow.Flow
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import splitties.init.appCtx
import timber.log.Timber
import java.util.concurrent.TimeUnit

object OkHttpRes {
    private val uncaughtExceptionsDataFlow = PublishSubjectFlow<UncaughtExceptionWrapper>()

    fun init(application: Application) {
        registerSingletonInDiContainer { ChuckerCollector(application) }

        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            uncaughtExceptionsDataFlow.offer(UncaughtExceptionWrapper(thread, throwable))
            Timber.tag("UncaughtException").e("""
                    Uncaught exception on thread ${thread.name}:
                    $throwable
                    ${throwable.stackTrace.joinToString(separator = "\n")}
                """.trimIndent())
            kget<ChuckerCollector>().onError("UncaughtException", throwable)
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }

    fun getDefaultHttpClient(clientExtraConfig: OkHttpClient.Builder.() -> Unit = {}): OkHttpClient {
        return OkHttpClient
                .Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(getHttpInspectorInterceptor())
                .apply(clientExtraConfig)
                .build()
    }

    fun uncaughtExceptionsFlow(): Flow<UncaughtExceptionWrapper> = uncaughtExceptionsDataFlow.flow()

    private fun getLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("OkHttp").d(message)
            }
        }).apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    private fun getHttpInspectorInterceptor(): Interceptor {
        val collector: ChuckerCollector = kget()
        return ChuckerInterceptor(appCtx, collector)
    }

    data class UncaughtExceptionWrapper(val thread: Thread, val throwable: Throwable)
}
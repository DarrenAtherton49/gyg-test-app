package com.gyg.common.injection.module

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.GsonBuilder
import com.gyg.BuildConfig
import com.gyg.common.threading.AndroidUiThread
import com.gyg.common.threading.IoExecutor
import com.gyg.common.threading.RxIoExecutor
import com.gyg.common.threading.UiThread
import com.gyg.data.DataManager
import com.gyg.data.GygDataManager
import com.gyg.data.cache.Cache
import com.gyg.data.cache.DiskCache
import com.gyg.data.config.Config
import com.gyg.data.config.InMemoryConfig
import com.gyg.data.service.ReviewService
import com.gyg.util.AndroidNetworkManager
import com.gyg.util.NetworkManager
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



/**
 * Dagger module that provides objects which last for the entire app lifecycle
 */
@Module
class AppModule(private val application: Application) {

    @Provides @Singleton internal fun provideApplicationContext(): Context {
        return application
    }

    @Provides @Singleton internal fun provideUiThread(androidUiThread: AndroidUiThread): UiThread {
        return androidUiThread
    }

    @Provides @Singleton internal fun provideIoExecutor(
            rxIoExecutor: RxIoExecutor): IoExecutor {
        return rxIoExecutor
    }

    @Provides @Singleton internal fun provideDataManager(reviewService: ReviewService,
                                                         cache: Cache,
                                                         config: Config,
                                                         networkManager: NetworkManager): DataManager {
        return GygDataManager(reviewService, cache, config, networkManager)
    }

    @Provides @Singleton internal fun provideCache(): Cache = DiskCache()

    @Provides @Singleton internal fun provideConfig(): Config = InMemoryConfig()

    @Provides @Singleton internal fun provideNetworkManager(context: Context): NetworkManager {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return AndroidNetworkManager(manager)
    }

    @Provides @Singleton internal fun provideReviewService(): ReviewService {

        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        val headersInterceptor = Interceptor { chain ->
            val original = chain.request()

            val request = original.newBuilder()
                    .addHeader("User-Agent", "GYG-Android")
                    .method(original.method(), original.body())
                    .build()

            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(headersInterceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.getyourguide.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        return retrofit.create(ReviewService::class.java)
    }
}

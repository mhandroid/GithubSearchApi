package com.mh.android.githubsearch.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mh.android.githubsearch.BuildConfig
import com.mh.android.githubsearch.api.ApiInterface
import com.mh.android.githubsearch.utils.ConnectivityInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Dagger module Class to provide net dependency
 * Created by @author Mubarak Hussain.
 */
@Module
class NetModule {
    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor?, cache: Cache?, context: Application?): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.addInterceptor(httpLoggingInterceptor).cache(cache)
        client.addInterceptor(ConnectivityInterceptor(context!!))
        return client.build()
    }

    /**
     * Dagger provide method for Gson object
     *
     * @return
     */
    @Singleton
    @Provides
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    /**
     * Dagger provide method for Rxjava adapter factory
     *
     * @return
     */
    @Singleton
    @Provides
    fun provideRxJavaAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    /**
     * Dagger provide method for Retrofit object
     *
     * @param okHttpClient
     * @param gson
     * @param rxJava2CallAdapterFactory
     * @return
     */
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient?, gson: Gson?, rxJava2CallAdapterFactory: RxJava2CallAdapterFactory?): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).addCallAdapterFactory(rxJava2CallAdapterFactory).build()
    }

    /**
     * Dagger provide method for http cache
     *
     * @param application
     * @return
     */
    @Singleton
    @Provides
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    /**
     * Dagger provide method for api interface
     *
     * @param retrofit
     * @return
     */
    @Singleton
    @Provides
    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    /**
     * Dagger provide method for logging interceptor
     *
     * @return
     */
    @Singleton
    @Provides
    fun provideHttpLoggingIntercepter(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    companion object {
        private const val BASE_URL = BuildConfig.BASE_URL
    }
}
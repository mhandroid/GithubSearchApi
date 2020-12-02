package com.mh.android.githubsearch.utils

import android.content.Context
import com.mh.android.githubsearch.exception.NoNetworkException
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Okhttp interceptor class to detect no network connection
 * Created by @author Mubarak Hussain.
 */
class ConnectivityInterceptor
/**
 * Constructor to initialize ConnectivityInterceptor
 *
 * @param context
 */(private val mContext: Context) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!Utils.isNetworkAvailable(mContext)) {
            throw NoNetworkException()
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}
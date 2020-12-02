package com.mh.android.githubsearch.utils

import android.content.Context
import android.net.ConnectivityManager
import android.widget.Toast

/**
 * Utility class for application utility method
 * Created by @author Mubarak Hussain.
 */
object Utils {
    const val ALBUM_URL = "ALBUM_URL"
    const val ALBUM_TITLE = "ALBUM_TITLE"

    /**
     * Method to check network is available or not
     *
     * @param context
     * @return
     */
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    fun showToastMsg(context: Context?, msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }
}
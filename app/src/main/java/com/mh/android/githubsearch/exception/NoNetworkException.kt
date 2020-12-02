package com.mh.android.githubsearch.exception

import java.io.IOException

/**
 * Exception class to get no network exception
 * Class for network exception
 * Created by @author Mubarak Hussain.
 */
class NoNetworkException : IOException() {
    override val message: String
        get() = "Please check your internet connection."
}
package com.mh.android.githubsearch.model

/**
 * Created by mubarak.hussain on 30/11/20.
 */
sealed class ApiResult<out T : Any> {
    data class Success<out T : Any>(val data: T?) : ApiResult<T>()
    data class Error(val error: String?) : ApiResult<Nothing>()
    data class Retry(val error: String?) : ApiResult<Nothing>()
}
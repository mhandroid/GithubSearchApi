package com.mh.android.githubsearch.ui.fragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mh.android.githubsearch.GitHubRepository
import com.mh.android.githubsearch.exception.NoNetworkException
import com.mh.android.githubsearch.model.ApiResult
import com.mh.android.githubsearch.model.User
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

class UserProfileViewModel @Inject constructor(val gitHubRepository: GitHubRepository) : ViewModel() {

    private val TAG: String = "MUB"
    private val sortedProducts = MutableLiveData<ApiResult<User>>()

    fun getUserProfile(loginName: String): LiveData<ApiResult<User>> {
        Log.d(TAG, "getUserProfile called $loginName")
        viewModelScope.launch {
            try {
                val response = gitHubRepository.getUserProfile(loginName)
                Log.d(TAG, "getUserProfile received result called $response")

                if (response.isSuccessful && response.body() != null) {
                    sortedProducts.value = ApiResult.Success(response.body()!!)
                } else {
                    if (response.code() in (500..599)) {
                        // try again if there is a server error
                        Log.d(TAG, "Retry response.message:${response.message()}, ${response.errorBody().toString()}")
                        sortedProducts.value = ApiResult.Retry(response.message())
                    }
                    sortedProducts.value = ApiResult.Error(response.message())
                }
            } catch (ex: Exception) {
                handelError(ex)
            }

        }
        return sortedProducts
    }

    private fun handelError(ex: Exception) {
        Log.i("MUB", "Network error occurs ${ex.localizedMessage}")
        when (ex) {
            is IOException -> {
                sortedProducts.value = ApiResult.Error("Network error!!!")
            }
            is NoNetworkException -> {
                sortedProducts.value = ApiResult.Error("No internet connection!!!")
            }
            else -> {
                sortedProducts.value = ApiResult.Error("Something went wrong please try again!!")
            }
        }
    }
}
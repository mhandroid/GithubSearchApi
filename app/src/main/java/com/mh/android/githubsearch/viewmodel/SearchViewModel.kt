package com.mh.android.githubsearch.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mh.android.githubsearch.GitHubRepository
import com.mh.android.githubsearch.exception.NoNetworkException
import com.mh.android.githubsearch.model.ApiResult
import com.mh.android.githubsearch.model.User
import com.mh.android.githubsearch.model.UserList
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by mubarak.hussain on 30/11/20.
 */
class SearchViewModel @Inject constructor(val gitHubRepository: GitHubRepository) : ViewModel() {

    private val TAG: String = SearchViewModel::class.toString()
    private val sortedProducts = MutableLiveData<ApiResult<List<User>>>()

    fun getListOfUser(queryName: String): LiveData<ApiResult<List<User>>> {
        Log.d(TAG, "getListOfUser called $queryName")
        viewModelScope.launch {
            try {
                val response = gitHubRepository.getUsers(queryName)
                loadData(response)
            }
            catch (ex: Exception) {
                handelError(ex)
            }
        }
        return sortedProducts
    }

    fun getFollowingList(queryName: String): LiveData<ApiResult<List<User>>> {
        Log.d(TAG, "getFollowingList called $queryName")
        viewModelScope.launch {
            try {
                val response = gitHubRepository.getFollowingList(queryName)
                loadListData(response)
            } catch (ex: Exception) {
                handelError(ex)
            }
        }
        return sortedProducts
    }

    private fun loadListData(response: Response<List<User>>) {
        Log.d(TAG, "getListOfUser received result called $response")
        if (response.isSuccessful && response.body() != null) {
            sortedProducts.value = ApiResult.Success(response.body())
        } else {
            if (response.code() in (500..599)) {
                // try again if there is a server error
                Log.d(TAG, "Retry response.message:${response.message()}, ${response.errorBody().toString()}")
                sortedProducts.value = ApiResult.Retry(response.message())
            }
            sortedProducts.value = ApiResult.Error(response.message())
        }
    }

    fun getFollowersList(queryName: String): LiveData<ApiResult<List<User>>> {
        Log.d(TAG, "getFollowersList called $queryName")
        viewModelScope.launch {
            try {
                val response = gitHubRepository.getFollowersList(queryName)
                loadListData(response)
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

    private fun loadData(response: Response<UserList>) {
        Log.d(TAG, "getListOfUser received result called $response")
        if (response.isSuccessful && response.body() != null) {
            sortedProducts.value = ApiResult.Success(response.body()!!.items)
        } else {
            if (response.code() in (500..599)) {
                // try again if there is a server error
                Log.d(TAG, "Retry response.message:${response.message()}, ${response.errorBody().toString()}")
                sortedProducts.value = ApiResult.Retry(response.message())
            }
            sortedProducts.value = ApiResult.Error(response.message())
        }
    }
}
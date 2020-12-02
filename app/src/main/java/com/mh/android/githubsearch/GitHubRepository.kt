package com.mh.android.githubsearch

import com.mh.android.githubsearch.api.ApiInterface
import com.mh.android.githubsearch.model.User
import com.mh.android.githubsearch.model.UserList
import retrofit2.Response

/**
 * Created by mubarak.hussain on 30/11/20.
 */
class GitHubRepository(private val apiInterface: ApiInterface) {

    suspend fun getUsers(queryName: String): Response<UserList> = apiInterface.searchUsers(queryName)

    suspend fun getUserProfile(userName: String): Response<User> = apiInterface.getUserProfile(userName)
    suspend fun getFollowingList(userName: String): Response<List<User>> = apiInterface.getUserFollowingList(userName)
    suspend fun getFollowersList(userName: String): Response<List<User>> = apiInterface.getUserFollowersList(userName)
}
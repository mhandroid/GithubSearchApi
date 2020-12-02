package com.mh.android.githubsearch.api

import com.mh.android.githubsearch.model.User
import com.mh.android.githubsearch.model.UserList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface of api resource
 * Created by @author Mubarak Hussain.
 */
interface ApiInterface {
    /**
     * Interface method to of get request for github search list
     * @return
     */
    @GET("/search/users")
    suspend fun searchUsers(@Query("q") queryName: String): Response<UserList>

    @GET("/users/{login}")
    suspend fun getUserProfile(@Path("login") userName: String): Response<User>

    @GET("/users/{login}/following")
    suspend fun getUserFollowingList(@Path("login") userName: String): Response<List<User>>

    @GET("/users/{login}/followers")
    suspend fun getUserFollowersList(@Path("login") userName: String): Response<List<User>>

}
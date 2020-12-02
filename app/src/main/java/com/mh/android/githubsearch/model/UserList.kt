package com.mh.android.githubsearch.model

import com.google.gson.annotations.SerializedName

/**
 * Created by mubarak.hussain on 30/11/20.
 */
data class UserList(@SerializedName("items") val items:List<User>, @SerializedName("total_count") val totalCount:Int)
data class UserFollowList(@SerializedName("items") val items:List<User>, @SerializedName("total_count") val totalCount:Int)
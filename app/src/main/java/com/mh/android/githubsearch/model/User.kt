package com.mh.android.githubsearch.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by mubarak.hussain on 30/11/20.
 */
class User(@SerializedName("login") val login: String?,

           @SerializedName("id") val id: Int? = null,

           @SerializedName("avatar_url") val avatarUrl: String?,

           @SerializedName("following_url") val followingUrl: String?,

           @SerializedName("url") val url: String?,

           @SerializedName("followers_url") val followersUrl: String?, @SerializedName("name") val name: String?, @SerializedName("bio") val bio: String?
) : Serializable
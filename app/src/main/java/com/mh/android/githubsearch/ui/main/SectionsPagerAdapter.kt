package com.mh.android.githubsearch.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mh.android.githubsearch.R
import com.mh.android.githubsearch.ui.fragment.PlaceholderFragment
import com.mh.android.githubsearch.ui.fragment.UserListFragment
import com.mh.android.githubsearch.ui.fragment.UserProfileFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, private val profileMap: Map<String, String?>) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        if (position == 0) {
            return UserProfileFragment.newInstance(profileMap[LOGIN_NAME] ?: error("Login name is empty"))
        } else if (position == 1) {
            return  UserListFragment.newInstance(1,profileMap[USER_FOLLOWING] ?: error("Login name is empty"),UserListFragment.ListType.FOLLOWING)
        }
        return UserListFragment.newInstance(1,profileMap[USER_FOLLOWING] ?: error("Login name is empty"),UserListFragment.ListType.FOLLOWERS)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return profileMap.size
    }

    companion object {
        val LOGIN_NAME: String = "login_name"
        val USER_FOLLOWERS: String = "user_followers"
        val USER_FOLLOWING: String = "user_following"
    }
}
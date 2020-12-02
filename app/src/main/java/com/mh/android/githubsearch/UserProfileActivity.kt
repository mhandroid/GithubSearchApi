package com.mh.android.githubsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mh.android.githubsearch.model.User
import com.mh.android.githubsearch.ui.main.SectionsPagerAdapter

class UserProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager, createUserMap())
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }

    private fun createUserMap(): MutableMap<String, String?> {
        val user = intent.getSerializableExtra("USER_DATA") as User

        return mutableMapOf<String, String?>().apply {
            this[SectionsPagerAdapter.LOGIN_NAME] = user.login
            this[SectionsPagerAdapter.USER_FOLLOWING] = user.login
            this[SectionsPagerAdapter.USER_FOLLOWERS] = user.followersUrl
        }
    }
}
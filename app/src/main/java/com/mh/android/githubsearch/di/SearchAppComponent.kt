package com.mh.android.githubsearch.di

import com.mh.android.githubsearch.GithubSearchApplication
import com.mh.android.githubsearch.MainActivity
import com.mh.android.githubsearch.ui.fragment.UserListFragment
import com.mh.android.githubsearch.ui.fragment.UserProfileFragment
import dagger.Component
import javax.inject.Singleton

/**
 * Created by @author Mubarak Hussain.
 */
@Singleton
@Component(modules = [ViewModelModule::class, NetModule::class, AppModule::class])
interface SearchAppComponent {
    fun inject(activity: MainActivity)
    fun inject(app: GithubSearchApplication)
    fun inject(app: UserProfileFragment)
    fun inject(app: UserListFragment)
}
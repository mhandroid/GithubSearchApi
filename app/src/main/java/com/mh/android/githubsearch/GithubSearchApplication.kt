package com.mh.android.githubsearch

import android.app.Application
import com.mh.android.githubsearch.di.AppModule
import com.mh.android.githubsearch.di.DaggerSearchAppComponent
import com.mh.android.githubsearch.di.SearchAppComponent

/**
 * Custom application class
 * Created by @author Mubarak Hussain.
 */
class GithubSearchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    /**
     * Initialize the dagger
     */
    private fun initDagger() {
        searchAppComponent = DaggerSearchAppComponent.builder().appModule(AppModule(this)).build()
    }

    lateinit var searchAppComponent: SearchAppComponent

    /**
     * Getter for  App component
     * @return
     */
    fun getAppComponent(): SearchAppComponent = searchAppComponent
}
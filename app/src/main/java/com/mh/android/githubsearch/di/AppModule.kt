package com.mh.android.githubsearch.di

import android.app.Application
import com.mh.android.githubsearch.GitHubRepository
import com.mh.android.githubsearch.api.ApiInterface
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger class to provide app dependency
 * Created by @author Mubarak Hussain.
 */
@Module
class AppModule(val mApplication: Application) {
    @Singleton
    @Provides
    fun provideApplication(): Application  = mApplication

    @Singleton
    @Provides
    fun provideUserRepository(apiInterface: ApiInterface): GitHubRepository = GitHubRepository(apiInterface)
}


package com.mh.android.githubsearch.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mh.android.githubsearch.ui.fragment.UserProfileViewModel
import com.mh.android.githubsearch.viewmodel.SearchViewModel
import com.mh.android.githubsearch.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * View model class to provide android view model class instance
 * Created by @author Mubarak Hussain.
 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    internal abstract fun bindSearchViewModel(albumViewModel: SearchViewModel): ViewModel
    @Binds
    @IntoMap
    @ViewModelKey(UserProfileViewModel::class)
    internal abstract fun bindUserProfileViewModel(albumViewModel: UserProfileViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
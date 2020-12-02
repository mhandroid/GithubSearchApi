package com.mh.android.githubsearch.di

import dagger.MapKey
import kotlin.reflect.KClass
import androidx.lifecycle.ViewModel
import java.lang.annotation.Documented

/**
 * Created by @author Mubarak Hussain.
 */
@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
package com.lifehacktestapp.android.di.component

import com.lifehacktestapp.android.App
import com.lifehacktestapp.android.di.module.ApiModule
import com.lifehacktestapp.android.di.module.RepositoryModule
import com.lifehacktestapp.android.presentation.detail.CompanyDetailViewModel
import com.lifehacktestapp.android.presentation.main.MainViewModel
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [],
    modules = [RepositoryModule::class, ApiModule::class]
)
interface AppComponent : AndroidInjector<App> {

    fun inject(mainViewModel: MainViewModel)
    fun inject(mainViewModel: CompanyDetailViewModel)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        fun networkModule(networkModule: ApiModule): Builder

        fun roomModule(repositoryModule: RepositoryModule): Builder
    }
}
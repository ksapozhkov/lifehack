package com.lifehacktestapp.android.presentation

import androidx.lifecycle.ViewModel
import com.lifehacktestapp.android.di.component.AppComponent
import com.lifehacktestapp.android.di.component.DaggerAppComponent
import com.lifehacktestapp.android.di.module.ApiModule
import com.lifehacktestapp.android.di.module.RepositoryModule
import com.lifehacktestapp.android.presentation.detail.CompanyDetailViewModel
import com.lifehacktestapp.android.presentation.main.MainViewModel

abstract class BaseViewModel : ViewModel() {

    private val injector: AppComponent = DaggerAppComponent
        .builder()
        .networkModule(ApiModule())
        .roomModule(RepositoryModule())
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MainViewModel -> injector.inject(this)
            is CompanyDetailViewModel -> injector.inject(this)
        }
    }

}
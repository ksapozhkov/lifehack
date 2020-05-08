package com.lifehacktestapp.android.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lifehacktestapp.android.presentation.detail.CompanyDetailViewModel
import com.lifehacktestapp.android.presentation.main.MainViewModel

class ViewModelFactory() : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel() as T
        }
        if (modelClass.isAssignableFrom(CompanyDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CompanyDetailViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
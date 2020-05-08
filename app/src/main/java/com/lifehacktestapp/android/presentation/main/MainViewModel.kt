package com.lifehacktestapp.android.presentation.main

import androidx.lifecycle.MutableLiveData
import com.lifehacktestapp.android.data.repository.CompanyRepository
import com.lifehacktestapp.android.domain.Company
import com.lifehacktestapp.android.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel() : BaseViewModel() {

    @Inject
    lateinit var repository: CompanyRepository
    val message = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()
    val companies = MutableLiveData<List<Company>>()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    init {
        getCompany()
    }

    private fun getCompany() {
        isLoading.value = true
        compositeDisposable.add(
            repository.getCompany(null)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn { error ->
                    println(error.message)
                    isLoading.value = false
                    message.value = error.localizedMessage
                    ArrayList()
                }
                .subscribe {
                    isLoading.value = false
                    companies.value = it
                }
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}
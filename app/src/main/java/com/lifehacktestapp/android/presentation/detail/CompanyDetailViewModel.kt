package com.lifehacktestapp.android.presentation.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.lifehacktestapp.android.R
import com.lifehacktestapp.android.data.repository.CompanyRepository
import com.lifehacktestapp.android.domain.Company
import com.lifehacktestapp.android.presentation.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CompanyDetailViewModel() : BaseViewModel() {

    @Inject
    lateinit var repository: CompanyRepository
    val message = MutableLiveData<Int>()
    val isLoading = MutableLiveData<Boolean>()
    val company = MutableLiveData<Company>()
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun getCompanyById(companyId: String) {
        isLoading.value = true
        compositeDisposable.add(
            repository.getCompany(companyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    company.value = null
                    isLoading.value = false
                    message.value = R.string.unable_to_load_companies
                }
                .subscribe({
                    if (it != null) {
                        company.value = it[0]
                    } else {
                        isLoading.value = false
                        message.value = R.string.company_not_found
                    }
                }, {
                    Log.e("", it.message)
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

}
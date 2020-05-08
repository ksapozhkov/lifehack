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

    fun getCompanyById(companyId: String) {
        isLoading.value = true
        compositeDisposable.add(
            repository.getCompany(companyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError {
                    isLoading.value = false
                    message.value = R.string.unable_to_load_companies
                    company.value = null
                }
                .subscribe({
                    isLoading.value = false
                    if (it != null) {
                        company.value = it[0]
                    } else {
                        message.value = R.string.company_not_found
                    }
                }, {
                    Log.e("", it.message!!)
                })
        )
    }

}
package com.lifehacktestapp.android.data.repository

import com.lifehacktestapp.android.App
import com.lifehacktestapp.android.data.db.CompanyDao
import com.lifehacktestapp.android.data.network.CompanyApi
import com.lifehacktestapp.android.domain.Company
import io.reactivex.Observable
import javax.inject.Inject

class CompanyRepository @Inject constructor(
    private var companyApi: CompanyApi,
    private var companyDao: CompanyDao
) {

    fun getCompany(companyId: String?): Observable<List<Company>> {
        return if (!App.isNetworkAvailable()) {
            if (companyId != null) (companyDao.getById(companyId)) else companyDao.getAll()
        } else {
            Observable.concatArrayEager(
                companyApi.getCompany(companyId)
                    .doOnNext {
                        companyDao.insertAll(it)
                    },
                if (companyId != null) (companyDao.getById(companyId)) else companyDao.getAll()
            )
        }
    }

}
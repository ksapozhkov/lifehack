package com.lifehacktestapp.android.data.network

import com.lifehacktestapp.android.domain.Company
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CompanyApi {

    @GET("test.php")
    fun getCompany(@Query("id") id : String?): Observable<List<Company>>

}
package com.lifehacktestapp.android.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lifehacktestapp.android.domain.Company
import io.reactivex.Observable

@Dao
interface CompanyDao {
    @Query("SELECT * FROM company")
    fun getAll(): Observable<List<Company>>

    @Query("SELECT * FROM Company WHERE Id=:id")
    fun getById(id: String): Observable<List<Company>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(companies: List<Company>)

}
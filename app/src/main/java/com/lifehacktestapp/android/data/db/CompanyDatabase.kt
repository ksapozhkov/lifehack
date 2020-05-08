package com.lifehacktestapp.android.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lifehacktestapp.android.domain.Company

@Database(entities = [Company::class], version = 1)
abstract class CompanyDatabase : RoomDatabase() {
    abstract fun companyDao(): CompanyDao
}
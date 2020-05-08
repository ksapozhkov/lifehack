package com.lifehacktestapp.android.di.module

import androidx.room.Room
import com.lifehacktestapp.android.App
import com.lifehacktestapp.android.data.db.CompanyDao
import com.lifehacktestapp.android.data.db.CompanyDatabase
import com.lifehacktestapp.android.data.network.CompanyApi
import com.lifehacktestapp.android.data.repository.CompanyRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule() {

    @Singleton
    @Provides
    fun providesRoomDatabase(): CompanyDatabase {
        return Room.databaseBuilder(App.instance, CompanyDatabase::class.java, "companies").build()
    }

    @Singleton
    @Provides
    fun providesProductDao(companyDatabase: CompanyDatabase): CompanyDao {
        return companyDatabase.companyDao()
    }

    @Singleton
    @Provides
    fun provideCompanyRepository(
        companyApi: CompanyApi,
        companyDao: CompanyDao
    ): CompanyRepository {
        return CompanyRepository(companyApi, companyDao)
    }

}
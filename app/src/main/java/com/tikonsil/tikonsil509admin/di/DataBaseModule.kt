package com.tikonsil.tikonsil509admin.di

import android.content.Context
import androidx.room.Room
import com.tikonsil.tikonsil509admin.data.local.dao.CostInnoveritDao
import com.tikonsil.tikonsil509admin.data.local.dao.SalesDao
import com.tikonsil.tikonsil509admin.data.local.dao.SalesErrorDao
import com.tikonsil.tikonsil509admin.data.local.db.TikonsilDatabase
import com.tikonsil.tikonsil509admin.domain.repository.costInnoverit.PriceCostInnoveritRepository
import com.tikonsil.tikonsil509admin.domain.repository.historysales.HistorySalesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, TikonsilDatabase::class.java,
        "database"
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: TikonsilDatabase) = database.salesDao()

    @Singleton
    @Provides
    fun provideSalesErrorDao(database: TikonsilDatabase) = database.salesErrorDao()

    @Singleton
    @Provides
    fun provideCostDao(database: TikonsilDatabase) = database.costInnoveritDao()


    @Singleton
    @Provides
    fun providePriceCostRepository(costInnoveritDao: CostInnoveritDao): PriceCostInnoveritRepository {
        return PriceCostInnoveritRepository(costInnoveritDao)
    }

}

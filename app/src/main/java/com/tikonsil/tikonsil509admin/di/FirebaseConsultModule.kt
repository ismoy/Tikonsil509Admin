package com.tikonsil.tikonsil509admin.di

import com.tikonsil.tikonsil509admin.data.remote.provider.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseConsultModule {

    @Provides
    @Singleton
    fun provideTotalUserProvider():TotalUserProvider = TotalUserProvider()

    @Provides
    @Singleton
    fun provideTotalProviderSales(): TotalSalesProvider = TotalSalesProvider()

    @Provides
    @Singleton
    fun provideHistorySalesProvider():HistorySalesProvider =HistorySalesProvider()

    @Provides
    @Singleton
    fun provideErrorSalesProvider():ErrorSalesProvider = ErrorSalesProvider()

    @Provides
    @Singleton
    fun provideTokenProvider():TokenProvider = TokenProvider()

    @Provides
    @Singleton
    fun provideAuthProvider():AuthProvider =AuthProvider()

    @Provides
    @Singleton
    fun provideUpdateStatusSalesProvider():UpdateStatusSalesProvider = UpdateStatusSalesProvider()

    @Provides
    @Singleton
    fun provideCountryPriceProvider():PriceCountryProvider = PriceCountryProvider()

    @Provides
    @Singleton
    fun provideUpdateProvider():UpdateUserProvider = UpdateUserProvider()

    @Provides
    @Singleton
    fun provideCostProvider():CostInnoveritProvider = CostInnoveritProvider()

}
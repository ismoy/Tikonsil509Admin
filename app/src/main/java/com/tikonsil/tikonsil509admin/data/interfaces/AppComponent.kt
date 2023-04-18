package com.tikonsil.tikonsil509admin.data.interfaces

import com.tikonsil.tikonsil509admin.data.remote.api.TikonsilApi
import com.tikonsil.tikonsil509admin.di.NetworkModule
import dagger.Component

@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(service:TikonsilApi)
}
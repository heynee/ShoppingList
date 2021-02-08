package com.silver.shopping.di

import com.silver.shopping.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ShoppingListRepositoryModule::class]
)
interface AppComponent {
    fun inject(mainViewModel: MainViewModel)
}
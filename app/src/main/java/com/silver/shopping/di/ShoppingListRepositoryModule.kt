package com.silver.shopping.di

import com.silver.shopping.repository.ShoppingListRepository
import com.silver.shopping.repository.ShoppingListRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ShoppingListRepositoryModule {

    @Provides
    @Singleton
    fun providePostRepository(): ShoppingListRepository = ShoppingListRepositoryImpl()
}
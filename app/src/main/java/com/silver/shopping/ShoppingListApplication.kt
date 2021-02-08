package com.silver.shopping

import android.app.Application
import com.silver.shopping.di.ComponentInjector

class ShoppingListApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ComponentInjector.init()
    }
}
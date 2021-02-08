package com.silver.shopping.di

class ComponentInjector {
    companion object {
        lateinit var component: AppComponent

        fun init() {
            component = DaggerAppComponent.builder()
                    .webServiceModule(WebServiceModule())
                    .shoppingListRepositoryModule(ShoppingListRepositoryModule())
                    .build()
        }
    }
}
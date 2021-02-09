package com.silver.shopping.repository

import com.silver.shopping.model.ShoppingListItem

interface ShoppingListRepository {
    fun getShoppingList(successHandler: (MutableList<ShoppingListItem>?) -> Unit, failureHandler: (Throwable?) -> Unit)
    fun addItem(item: ShoppingListItem, successHandler: (String) -> Unit, failureHandler: (Throwable?) -> Unit)
    fun removeItem(id: String, successHandler: () -> Unit, failureHandler: (Throwable?) -> Unit)
    fun updateCheckedStatus(id: String, item: ShoppingListItem, successHandler: () -> Unit, failureHandler: (Throwable?) -> Unit)
}
package com.silver.shopping.repository

import com.silver.shopping.model.ShoppingListItem

interface ShoppingListRepository {
    fun getShoppingList()
    fun addItem(item: ShoppingListItem)
    fun removeItem(id: String)
    fun updateCheckedStatus(id: String, isChecked: Boolean)
}
package com.silver.shopping.repository

import com.silver.shopping.model.ShoppingListItem

class ShoppingListRepositoryImpl : ShoppingListRepository {
    override fun getShoppingList() {}
    override fun addItem(item: ShoppingListItem) {}
    override fun removeItem(id: String) {}
    override fun updateCheckedStatus(id: String, isChecked: Boolean) {}
}
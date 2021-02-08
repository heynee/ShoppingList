package com.silver.shopping.model

data class ShoppingListItem(
        var name: String,
        var isChecked: Boolean,
        var id: String? = null
)
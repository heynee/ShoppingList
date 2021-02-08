package com.silver.shopping.api

import com.silver.shopping.model.ShoppingListItem
import retrofit2.Call
import retrofit2.http.*

interface WebService {
    /**
     * Returns all shopping list items
     */
    @GET("list.json")
    fun getShoppingList(): Call<HashMap<String, ShoppingListItem>?>

    /**
     * Adds item to shopping list
     */
    @POST("list.json")
    fun addItem(@Body item: ShoppingListItem): Call<ShoppingListItem>

    /**
     * Removes item from shopping list
     */
    @DELETE("list/{id}.json")
    fun removeItem(@Path("id") id: String): Call<Void>

    /**
     * Updates shopping list item's checked status
     */
    @PATCH("list/{id}.json")
    fun updateCheckedStatus(@Path("id") id: String, @Body item: ShoppingListItem): Call<ShoppingListItem>
}
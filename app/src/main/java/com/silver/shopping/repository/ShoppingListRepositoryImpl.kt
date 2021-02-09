package com.silver.shopping.repository

import com.silver.shopping.api.WebService
import com.silver.shopping.model.ShoppingListItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShoppingListRepositoryImpl(private val webService: WebService) : ShoppingListRepository {
    override fun getShoppingList(
        successHandler: (MutableList<ShoppingListItem>?) -> Unit,
        failureHandler: (Throwable?) -> Unit
    ) {
        webService.getShoppingList().enqueue(object : Callback<HashMap<String, ShoppingListItem>?> {
            override fun onResponse(
                call: Call<HashMap<String, ShoppingListItem>?>,
                response: Response<HashMap<String, ShoppingListItem>?>
            ) {
                response.body()?.let {
                    successHandler(it.map { x ->
                        ShoppingListItem(
                            x.value.name,
                            x.value.isChecked,
                            x.key
                        )
                    } as MutableList<ShoppingListItem>)
                }
            }

            override fun onFailure(call: Call<HashMap<String, ShoppingListItem>?>, t: Throwable?) {
                failureHandler(t)
            }
        })
    }

    override fun addItem(
        item: ShoppingListItem,
        successHandler: (String) -> Unit,
        failureHandler: (Throwable?) -> Unit
    ) {
        webService.addItem(item).enqueue(object : Callback<ShoppingListItem> {
            override fun onResponse(
                call: Call<ShoppingListItem>,
                response: Response<ShoppingListItem>
            ) {
                response.body()?.let {
                    successHandler(it.name)
                }
            }

            override fun onFailure(call: Call<ShoppingListItem>, t: Throwable?) {
                failureHandler(t)
            }
        })
    }

    override fun removeItem(
        id: String,
        successHandler: () -> Unit,
        failureHandler: (Throwable?) -> Unit
    ) {
        webService.removeItem(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                successHandler()
            }

            override fun onFailure(call: Call<Void>, t: Throwable?) {
                failureHandler(t)
            }
        })
    }

    override fun updateCheckedStatus(
        id: String,
        item: ShoppingListItem,
        successHandler: () -> Unit,
        failureHandler: (Throwable?) -> Unit
    ) {
        webService.updateCheckedStatus(id, item).enqueue(object : Callback<ShoppingListItem> {
            override fun onResponse(
                call: Call<ShoppingListItem>,
                response: Response<ShoppingListItem>
            ) {
                successHandler()
            }

            override fun onFailure(call: Call<ShoppingListItem>, t: Throwable?) {
                failureHandler(t)
            }
        })
    }
}
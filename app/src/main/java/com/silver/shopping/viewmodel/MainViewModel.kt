package com.silver.shopping.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.silver.shopping.model.ShoppingListItem
import com.silver.shopping.repository.ShoppingListRepository
import com.silver.shopping.util.notifyObserver
import javax.inject.Inject

class MainViewModel : ViewModel() {
    @Inject
    lateinit var repository: ShoppingListRepository

    var apiError = MutableLiveData<Throwable>()

    private var _shoppingList = MutableLiveData<MutableList<ShoppingListItem>>()
    val shoppingList = _shoppingList as LiveData<MutableList<ShoppingListItem>>

    init {
        _shoppingList.value = ArrayList()
    }

    fun getShoppingList() {
        repository.getShoppingList(
            {
                _shoppingList.value = it?.map { x ->
                    ShoppingListItem(
                        x.value.name,
                        x.value.isChecked,
                        x.key
                    )
                } as MutableList<ShoppingListItem>
            },
            {
                apiError.value = it
            })
    }

    fun onItemAdded(name: String) {
        val item = ShoppingListItem(name, false)
        _shoppingList.value?.add(item)
        _shoppingList.notifyObserver()
        repository.addItem(item,
            {
                item.id = it
            },
            {
                //Maybe show toast "Data sync failed"
                apiError.value = it
            })
    }

    fun onItemRemoved(index: Int) {
        val id = shoppingList.value?.get(index)?.id
        _shoppingList.value?.removeAt(index)
        _shoppingList.notifyObserver()
        id?.let {
            repository.removeItem(id) {
                //Maybe show toast "Data sync failed"
                apiError.value = it
            }
        }
    }

    fun onItemChecked(index: Int, isChecked: Boolean) {
        val item = shoppingList.value?.get(index)
        item?.isChecked = isChecked
        _shoppingList.notifyObserver()
        item?.id?.let { id ->
            repository.updateCheckedStatus(id, ShoppingListItem(item.name, item.isChecked)) {
                //Maybe show toast "Data sync failed"
                apiError.value = it
            }
        }
    }

    /**
     * Adapter callbacks
     */
    fun getShoppingListItem(position: Int): ShoppingListItem? {
        return if (position < getShoppingListSize()) {
            _shoppingList.value?.get(position)
        } else {
            null
        }
    }

    fun getShoppingListSize(): Int {
        _shoppingList.value?.let {
            return it.size
        }
        return 0
    }
}
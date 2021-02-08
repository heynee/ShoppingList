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

    private var _shoppingList = MutableLiveData<MutableList<ShoppingListItem>>()
    val shoppingList = _shoppingList as LiveData<MutableList<ShoppingListItem>>

    init {
        _shoppingList.value = ArrayList()
    }

    fun getShoppingList() {
        repository.getShoppingList()
    }

    fun onItemAdded(name: String) {
        val item = ShoppingListItem(name, false)
        _shoppingList.value?.add(item)
        _shoppingList.notifyObserver()
        repository.addItem(item)
    }

    fun onItemRemoved(index: Int) {
        _shoppingList.value?.removeAt(index)
        _shoppingList.notifyObserver()
        repository.removeItem("id")
    }

    fun onItemChecked(index: Int, isChecked: Boolean) {
        _shoppingList.value?.get(index)?.isChecked = isChecked
        _shoppingList.notifyObserver()
        repository.updateCheckedStatus("id", isChecked)
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
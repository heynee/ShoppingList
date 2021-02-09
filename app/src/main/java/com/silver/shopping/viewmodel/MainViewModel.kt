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

    private val _apiError = MutableLiveData<Action.ApiError>()
    val apiError = _apiError as LiveData<Action.ApiError>

    private val _isLoading = MutableLiveData<Action.Loading>()
    val isLoading = _isLoading as LiveData<Action.Loading>

    init {
        _shoppingList.value = ArrayList()
    }

    fun getShoppingList() {
        _isLoading.value = Action.Loading(true)

        repository.getShoppingList(
            {
                _shoppingList.value = it
                _isLoading.value = Action.Loading(false)
            },
            {
                _apiError.value = Action.ApiError
                _isLoading.value = Action.Loading(false)
            })
    }

    fun onItemAdded(name: String) {
        _isLoading.value = Action.Loading(true)

        val item = ShoppingListItem(name, false)
        _shoppingList.value?.add(item)
        _shoppingList.notifyObserver()

        repository.addItem(item,
            {
                item.id = it
                _isLoading.value = Action.Loading(false)
            },
            {
                _apiError.value = Action.ApiError
                _isLoading.value = Action.Loading(false)
            })
    }

    fun onItemRemoved(index: Int) {
        _isLoading.value = Action.Loading(true)

        val id = _shoppingList.value?.get(index)?.id
        _shoppingList.value?.removeAt(index)
        _shoppingList.notifyObserver()

        if (id != null) {
            repository.removeItem(id,
                {
                    _isLoading.value = Action.Loading(false)
                },
                {
                    _apiError.value = Action.ApiError
                    _isLoading.value = Action.Loading(false)
                })
        } else {
            _apiError.value = Action.ApiError
            _isLoading.value = Action.Loading(false)
        }
    }

    fun onItemChecked(index: Int, isChecked: Boolean) {
        _isLoading.value = Action.Loading(true)

        val item = _shoppingList.value?.get(index)
        item?.isChecked = isChecked
        _shoppingList.notifyObserver()
        val id = item?.id

        if (id != null) {
            repository.updateCheckedStatus(id, ShoppingListItem(item.name, item.isChecked),
                {
                    _isLoading.value = Action.Loading(false)
                },
                {
                    _apiError.value = Action.ApiError
                    _isLoading.value = Action.Loading(false)
                })
        } else {
            _apiError.value = Action.ApiError
            _isLoading.value = Action.Loading(false)
        }
    }

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

    sealed class Action {
        data class Loading(val show: Boolean) : Action()
        object ApiError : Action()
    }
}
package com.silver.shopping

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.silver.shopping.model.ShoppingListItem
import com.silver.shopping.repository.ShoppingListRepository
import com.silver.shopping.viewmodel.MainViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ShoppingListUnitTests {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var mainViewModel: MainViewModel

    private val mockShoppingList = mutableListOf(
        ShoppingListItem("Apples", true),
        ShoppingListItem("Sugar", false),
        ShoppingListItem("Milk", false)
    )

    private val shoppingListObserver: Observer<List<ShoppingListItem>> = mock()
    private val loadingObserver: Observer<MainViewModel.Action.Loading> = mock()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel().apply {
            repository = object : ShoppingListRepository {
                override fun getShoppingList(
                    successHandler: (MutableList<ShoppingListItem>?) -> Unit,
                    failureHandler: (Throwable?) -> Unit
                ) {
                    successHandler(mockShoppingList)
                }

                override fun addItem(
                    item: ShoppingListItem,
                    successHandler: (String) -> Unit,
                    failureHandler: (Throwable?) -> Unit
                ) {
                    successHandler("id4")
                }

                override fun removeItem(
                    id: String,
                    successHandler: () -> Unit,
                    failureHandler: (Throwable?) -> Unit
                ) {
                    failureHandler(null)
                }

                override fun updateCheckedStatus(
                    id: String,
                    item: ShoppingListItem,
                    successHandler: () -> Unit,
                    failureHandler: (Throwable?) -> Unit
                ) {
                    failureHandler(null)
                }
            }

            shoppingList.observeForever(shoppingListObserver)
            isLoading.observeForever(loadingObserver)
        }
    }

    @Test
    fun getShoppingList() {
        mainViewModel.getShoppingList()

        val captor = ArgumentCaptor.forClass(ShoppingListItem::class.java)
        captor.run {
            verify(shoppingListObserver, times(2)).onChanged(listOf(capture()))
            assertEquals(mockShoppingList, value)
        }

        val isLoading = MainViewModel.Action.Loading(false)

        val loadingCaptor = ArgumentCaptor.forClass(MainViewModel.Action.Loading::class.java)
        loadingCaptor.run {
            verify(loadingObserver, times(2)).onChanged(capture())
            assertEquals(isLoading, value)
        }
    }

    @Test
    fun onItemAdded() {
        mainViewModel.onItemAdded("Beer")
        val mockShoppingList = listOf(ShoppingListItem("Beer", false, "id4"))

        val captor = ArgumentCaptor.forClass(ShoppingListItem::class.java)
        captor.run {
            verify(shoppingListObserver, times(2)).onChanged(listOf(capture()))
            assertEquals(mockShoppingList, value)
        }

        val isLoading = MainViewModel.Action.Loading(false)

        val loadingCaptor = ArgumentCaptor.forClass(MainViewModel.Action.Loading::class.java)
        loadingCaptor.run {
            verify(loadingObserver, times(2)).onChanged(capture())
            assertEquals(isLoading, value)
        }
    }

    @Test
    fun onItemRemoved() {
        mainViewModel.getShoppingList()
        mainViewModel.onItemRemoved(1)

        val mockShoppingList = listOf(
            ShoppingListItem("Apples", true),
            ShoppingListItem("Milk", false)
        )

        val shoppingListItemCaptor = ArgumentCaptor.forClass(ShoppingListItem::class.java)
        shoppingListItemCaptor.run {
            verify(shoppingListObserver, times(3)).onChanged(listOf(capture()))
            assertEquals(mockShoppingList, value)
        }

        val isLoading = MainViewModel.Action.Loading(false)

        val loadingCaptor = ArgumentCaptor.forClass(MainViewModel.Action.Loading::class.java)
        loadingCaptor.run {
            verify(loadingObserver, times(4)).onChanged(capture())
            assertEquals(isLoading, value)
        }
    }

    @Test
    fun onItemChecked() {
        mainViewModel.getShoppingList()
        mainViewModel.onItemChecked(1, true)

        val mockShoppingList = listOf(
            ShoppingListItem("Apples", true),
            ShoppingListItem("Sugar", true),
            ShoppingListItem("Milk", false)
        )

        val captor = ArgumentCaptor.forClass(ShoppingListItem::class.java)
        captor.run {
            verify(shoppingListObserver, times(3)).onChanged(listOf(capture()))
            assertEquals(mockShoppingList, value)
        }

        val isLoading = MainViewModel.Action.Loading(false)

        val loadingCaptor = ArgumentCaptor.forClass(MainViewModel.Action.Loading::class.java)
        loadingCaptor.run {
            verify(loadingObserver, times(4)).onChanged(capture())
            assertEquals(isLoading, value)
        }
    }

    @Test
    fun getShoppingListSize() {
        mainViewModel.getShoppingList()
        assertEquals(3, mainViewModel.getShoppingListSize())
    }

    @Test
    fun getShoppingListItem() {
        mainViewModel.getShoppingList()
        assertEquals(ShoppingListItem("Sugar", false), mainViewModel.getShoppingListItem(1))
    }
}
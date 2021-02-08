package com.silver.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.silver.shopping.adapter.ShoppingListItemAdapter
import com.silver.shopping.di.ComponentInjector
import com.silver.shopping.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_bottom.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: ShoppingListItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = createViewModel()
        adapter = ShoppingListItemAdapter(viewModel)
        applyAdapter()
        attachObserver()
        viewModel.getShoppingList()
        setOnNewItemListener()
    }

    private fun createViewModel(): MainViewModel =
        ViewModelProviders.of(this).get(MainViewModel::class.java).also {
            ComponentInjector.component.inject(it)
        }

    private fun applyAdapter() {
        shopping_list_item_rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun attachObserver() {
        viewModel.shoppingList.observe(this, {
            it?.let { adapter.notifyDataSetChanged() }
        })
    }

    private fun setOnNewItemListener() {
        shopping_list_item_add_btn.setOnClickListener {
            val newItemName = shopping_list_item_add_et.text.toString().trim()
            if (newItemName.isNotBlank()) {
                viewModel.onItemAdded(newItemName)
                shopping_list_item_add_et.text.clear()
            }
        }
    }
}
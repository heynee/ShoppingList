package com.silver.shopping

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.silver.shopping.adapter.ShoppingListItemAdapter
import com.silver.shopping.model.ShoppingListItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ShoppingListItemAdapter.ItemActionInterface {

    private lateinit var adapter: ShoppingListItemAdapter
    private var mockList = arrayListOf<ShoppingListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        adapter = ShoppingListItemAdapter(mockList, this)
        applyAdapter()
        setOnNewItemListener()
    }

    private fun applyAdapter() {
        shopping_list_item_rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }
    }

    private fun setOnNewItemListener() {
        shopping_list_item_add_btn.setOnClickListener {
            val newItemName = shopping_list_item_add_et.text.toString()
            if (newItemName.isNotBlank()) {
                mockList.add(ShoppingListItem(newItemName, false))
                shopping_list_item_add_et.text.clear()
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun checked(index: Int) {
        mockList[index].isChecked = true
        adapter.notifyDataSetChanged()
    }

    override fun unChecked(index: Int) {
        mockList[index].isChecked = false
        adapter.notifyDataSetChanged()
    }

    override fun deleted(index: Int) {
        mockList.remove(mockList[index])
        adapter.notifyDataSetChanged()
    }
}
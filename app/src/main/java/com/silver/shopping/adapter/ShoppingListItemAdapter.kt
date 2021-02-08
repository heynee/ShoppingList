package com.silver.shopping.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.silver.shopping.R
import com.silver.shopping.model.ShoppingListItem

class ShoppingListItemAdapter(
        private var shoppingListItems: List<ShoppingListItem>,
        private val itemActionInterface: ItemActionInterface,
) : RecyclerView.Adapter<ShoppingListItemAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        shoppingListItems[position].let { item ->
            holder.checkBox.apply {
                this.text = item.name
                if (item.isChecked) {
                    this.isChecked = true
                    this.paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    this.isChecked = false
                    this.paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }.also {
                it.setOnClickListener {
                    if (item.isChecked) {
                        itemActionInterface.unChecked(position)
                    } else {
                        itemActionInterface.checked(position)
                    }
                }
                it.setOnLongClickListener {
                    itemActionInterface.deleted(position)
                    return@setOnLongClickListener true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.shopping_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return shoppingListItems.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox by lazy { itemView.findViewById(R.id.shopping_list_item_check_btn) as CheckBox }
    }

    interface ItemActionInterface {
        fun deleted(index: Int)
        fun checked(index: Int)
        fun unChecked(index: Int)
    }
}
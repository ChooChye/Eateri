package com.example.eateri.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.eateri.R
import com.example.eateri.ui.datas.OrderList
import kotlinx.android.synthetic.main.cart_item_layout.view.*

class CartAdapter(private val cart : MutableList<OrderList>) : RecyclerView.Adapter<CartViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.cart_item_layout, parent, false)
        return CartViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return cart.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        //holder.view.cartFoodName.text = cart.get(position).foodItem.toString()
    }


}

class CartViewHolder(val view: View) : RecyclerView.ViewHolder(view){

}
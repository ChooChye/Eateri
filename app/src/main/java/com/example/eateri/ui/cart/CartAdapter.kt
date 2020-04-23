package com.example.eateri.ui.cart

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.FtsOptions
import androidx.room.Room
import com.example.eateri.MainActivity
import com.example.eateri.R
import com.example.eateri.helpers.CartDB
import com.example.eateri.ui.datas.OrderList
import kotlinx.android.synthetic.main.cart_item_layout.view.*

class CartAdapter(dataset : ArrayList<OrderList>) : RecyclerView.Adapter<CartViewHolder>() {

    private var entity = OrderList()
    var dataset = dataset
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.cart_item_layout, parent, false)

        return CartViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        //Toast.makeText(holder.view.context, "${dataset.size}", Toast.LENGTH_SHORT).show()
        Log.d("@BB", "${dataset.size}")
        holder.view.cartFoodName.text = dataset.get(position).foodName
        holder.view.cartFoodPrice.text = dataset.get(position).foodPrice
        holder.view.cart_deleteBtn.setOnClickListener {
            //Toast.makeText(holder.view.context, "Delete Order", Toast.LENGTH_SHORT).show()
            dataset.removeAt(position)

            if(dataset.size == 0){
                val intent = Intent(it.context, MainActivity::class.java)
                it.context.startActivity(intent)
            }
        }
    }


}

class CartViewHolder(val view: View) : RecyclerView.ViewHolder(view){
    var qtyChooser = view.cartQty
}
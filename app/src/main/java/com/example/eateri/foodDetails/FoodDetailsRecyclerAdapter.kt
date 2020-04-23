package com.example.eateri.foodDetails

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.eateri.DataSource
import com.example.eateri.R
import com.example.eateri.helpers.CartDB
import com.example.eateri.ui.cart.CartActivity
import com.example.eateri.ui.datas.OrderList
import com.example.eateri.ui.datas.Cart
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.food_details_container.view.*
import java.lang.Math.random


class FoodDetailsRecyclerAdapter(rid : Int, restPos: Int) : RecyclerView.Adapter<CustomerViewHolder>(){
    private var restId = rid
    private var restPos = restPos
    private val dataSource: DataSource = DataSource()

    override fun getItemCount(): Int {
        var size = 0
        for(i in dataSource.menu.indices){
            if(restId == dataSource.menu.get(i).restID){
                size +=1
            }
        }
        return size
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        var collectedRest = arrayListOf<collectionRest>()
        var index = 0
        for(i in dataSource.menu.indices){
            if(restId == dataSource.menu.get(i).restID){
                var fid         = dataSource.menu.get(index).foodID
                var foodName    = dataSource.menu.get(index).foodName
                var foodPrice   = String.format("%.2f", dataSource.menu.get(index).foodPrice)
                var image       = dataSource.menu.get(index).foodImageView

                collectedRest.add(collectionRest(fid, foodName, foodPrice, image))
            }
            index +=1
        }

        holder.view.foodContainer_imageView_food.setImageResource(collectedRest.get(position).image)
        holder.view.foodContainer_textView_foodName.text = collectedRest.get(position).foodName
        holder.view.foodContainer_textView_price.text = "RM ${collectedRest.get(position).price}"
        holder.view.foodDetails_container.setOnClickListener {
            val tapCount = 1
            val orderList = ArrayList<OrderList>()
            val col = collectedRest.get(position)
            val orderId = (10000..99999).shuffled().first()
            Snackbar.make(holder.view, "rid: $orderId | foodList : ${col.foodName}, ${col.price}", Snackbar.LENGTH_LONG).show()
            //orderList.add(OrderList(orderId, 1, Cart(col.foodName, col.price)))
            val db = Room.databaseBuilder(
                holder.view.context,
                CartDB::class.java, "CART.db"
            ).build()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.food_details_container, parent, false)
        return CustomerViewHolder(cellForRow)
    }



}

class CustomerViewHolder(val view : View) : RecyclerView.ViewHolder(view){}

class collectionRest (
    val foodID      : Int,
    val foodName    : String,
    val price       : String,
    val image       : Int
)


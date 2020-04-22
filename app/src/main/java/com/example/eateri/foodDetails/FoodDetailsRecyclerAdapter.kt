package com.example.eateri.foodDetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.eateri.DataSource
import com.example.eateri.R
import kotlinx.android.synthetic.main.food_details_container.view.*


class FoodDetailsRecyclerAdapter(rid : Int) : RecyclerView.Adapter<CustomerViewHolder>(){
    private var restId = rid
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
        val menu = dataSource.menu.get(position)
        var foodName    = menu.foodName
        var foodPrice   = String.format("%.2f", menu.foodPrice)
        var image       = menu.foodImageView

        if(restId == dataSource.menu.get(position).restID){
            holder.view.foodContainer_imageView_food.setImageResource(image)
            holder.view.foodContainer_textView_foodName.text = "rid: $restId | pos:$position"
            holder.view.foodContainer_textView_price.text = "RM ${foodPrice}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.food_details_container, parent, false)
        return CustomerViewHolder(cellForRow)
    }

}

class CustomerViewHolder(val view : View) : RecyclerView.ViewHolder(view){}



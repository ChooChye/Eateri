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
                var foodName    = dataSource.menu.get(index).foodName
                var foodPrice   = String.format("%.2f", dataSource.menu.get(index).foodPrice)
                var image       = dataSource.menu.get(index).foodImageView

                collectedRest.add(collectionRest(foodName, foodPrice, image))
            }
            index +=1
        }

        holder.view.foodContainer_imageView_food.setImageResource(collectedRest.get(position).image)
        holder.view.foodContainer_textView_foodName.text = collectedRest.get(position).foodName
        holder.view.foodContainer_textView_price.text = "RM ${collectedRest.get(position).price}"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.food_details_container, parent, false)
        return CustomerViewHolder(cellForRow)
    }

}

class CustomerViewHolder(val view : View) : RecyclerView.ViewHolder(view){}

class collectionRest (
    val foodName    : String,
    val price       : String,
    val image       : Int
)

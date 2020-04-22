package com.example.eateri.ui.home


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.eateri.DataSource
import com.example.eateri.R
import com.example.eateri.foodDetails.FoodDetails
import kotlinx.android.synthetic.main.layout_card_list.view.*


class HomeAdapter: RecyclerView.Adapter<CustomViewHolder>() {

    var dataSource : DataSource = DataSource()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow      = layoutInflater.inflate(R.layout.layout_card_list, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return dataSource.list.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        holder.v.grid_rest_title.text = dataSource.list.get(position).restName
        holder.v.grid_rest_stat.text = dataSource.list.get(position).stat
        holder.v.cardView_grid.setOnClickListener {it: View ->
            val id = dataSource.list.get(position).id
            it.findNavController().navigate(HomeFragmentDirections.actionNavHomeToNavFoodDetails(id, position))
        }
    }

}
class CustomViewHolder (val v: View): RecyclerView.ViewHolder(v){

}




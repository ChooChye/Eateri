package com.example.eateri.foodDetails

import android.R.attr.name
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.eateri.DataSource
import com.example.eateri.R
import com.example.eateri.helpers.CartDB
import com.example.eateri.ui.datas.Cart
import com.example.eateri.ui.datas.OrderList
import com.example.eateri.ui.home.HomeFragmentArgs
import timber.log.Timber


/**
 * A simple [Fragment] subclass.
 */
class FoodDetails : Fragment() {

    private var dataSource: DataSource = DataSource()

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //GET POSITION SELECTED
        val args = FoodDetailsArgs.fromBundle(arguments!!)
        val restId = args.restID
        val position = args.position

        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_food_details, container, false)


        //Toast.makeText(context, "rest:$restId | pos:$position", Toast.LENGTH_LONG).show()
        v.findViewById<ImageView>(R.id.foodDetails_cover).setImageResource(dataSource.list.get(position).img)
        val rest = dataSource.list.get(position)
        v.findViewById<TextView>(R.id.foodDetails_restName).text = "${rest.restName} - Menu"
        recyclerView = v.findViewById(R.id.food_details_recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
        recyclerView.adapter = FoodDetailsRecyclerAdapter(restId, position, activity!!.applicationContext)

        return v
    }
}

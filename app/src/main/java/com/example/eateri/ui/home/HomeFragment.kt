package com.example.eateri.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eateri.DataSource
import com.example.eateri.R
import com.example.eateri.RestaurantList
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var HomeAdapter: HomeAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        var recyclerList : RecyclerView = root.findViewById(R.id.homeRecyclerView) as RecyclerView
        //recyclerList.layoutManager = LinearLayoutManager(context)
        recyclerList.layoutManager = GridLayoutManager(context,2)
        recyclerList.adapter = HomeAdapter()



        return root
    }
}

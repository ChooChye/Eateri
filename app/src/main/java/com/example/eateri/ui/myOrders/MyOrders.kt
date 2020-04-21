package com.example.eateri.ui.myOrders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.eateri.R

class MyOrders : Fragment() {

    private lateinit var MyOrdersViewModel: MyOrdersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_myorders, container, false)
    }
}

class MyOrdersViewModel {

}
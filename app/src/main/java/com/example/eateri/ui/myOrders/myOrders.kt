package com.example.eateri.ui.myOrders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.eateri.R

class myOrders : Fragment() {

    private lateinit var myOrdersViewModel: myOrdersViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myOrdersViewModel =
                ViewModelProviders.of(this).get(myOrdersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_myorders, container, false)
        val textView: TextView = root.findViewById(R.id.text_gallery)
        myOrdersViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}

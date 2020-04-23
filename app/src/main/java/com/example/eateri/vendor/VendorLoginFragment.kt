package com.example.eateri.vendor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.eateri.R
import kotlinx.android.synthetic.main.fragment_vendorlogin.*

class VendorLoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val vd = inflater.inflate(R.layout.fragment_vendorlogin, container, false)

        val linkToRegister : TextView = vd.findViewById(R.id.btnVdReg)

        linkToRegister.setOnClickListener {view : View->
            view.findNavController().navigate(R.id.nav_vendor)
        }

        return vd
    }


}

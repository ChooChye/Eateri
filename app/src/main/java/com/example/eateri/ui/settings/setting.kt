package com.example.eateri.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate

import com.example.eateri.R

/**
 * A simple [Fragment] subclass.
 */
class setting : Fragment() {
    private lateinit var sw : Switch
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        if(sw.isChecked){
            AppCompatDelegate.getDefaultNightMode()
            AppCompatDelegate.MODE_NIGHT_YES

        }else{
            AppCompatDelegate.MODE_NIGHT_NO
        }
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }


}

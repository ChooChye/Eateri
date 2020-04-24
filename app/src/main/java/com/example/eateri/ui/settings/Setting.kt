package com.example.eateri.ui.settings

import android.app.Notification
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Paint
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.appcompat.widget.ThemedSpinnerAdapter
import androidx.navigation.fragment.findNavController

import com.example.eateri.R
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 */
class Setting : Fragment() {
    private var sw:Switch?=null
    private lateinit var sd: SaveData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        view.findViewById<Switch>(R.id.darkMode).setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                //activity?.setTheme(R.style.darktheme)
                activity.apply { R.style.darktheme }
                Snackbar.make(view, "NIGHT YES", Snackbar.LENGTH_LONG).show()

                AppCompatDelegate.getDefaultNightMode()
                AppCompatDelegate.MODE_NIGHT_YES

            } else {
               // activity?.setTheme(R.style.AppTheme)
                activity.apply { R.style.AppTheme }
                Snackbar.make(view, "NIGHT NO", Snackbar.LENGTH_LONG).show()
                AppCompatDelegate.MODE_NIGHT_NO

            }
        }
        return view
    }


}

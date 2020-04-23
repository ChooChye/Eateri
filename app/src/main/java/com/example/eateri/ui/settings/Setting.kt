package com.example.eateri.ui.settings

import android.app.UiModeManager
import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.eateri.R
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.material.snackbar.Snackbar


/**
 * A simple [Fragment] subclass.
 */
class Setting : Fragment() {
    private lateinit var sw : Switch
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)
        var fm : FragmentManager= parentFragmentManager
        view.findViewById<Switch>(R.id.darkMode).setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {

                Snackbar.make(view, "NIGHT YES", Snackbar.LENGTH_LONG).show()
                UiModeManager.MODE_NIGHT_YES
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                super.onCreate(savedInstanceState)

                fm.beginTransaction()
                fm.
                fm.attach(LobbyFragment.this)
                fm.commit();
            } else {
                Snackbar.make(view, "NIGHT NO", Snackbar.LENGTH_LONG).show()
                AppCompatDelegate.MODE_NIGHT_NO
                R.style.AppTheme
            }
        }
        return view
    }
}

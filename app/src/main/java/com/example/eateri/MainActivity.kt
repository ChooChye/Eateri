package com.example.eateri

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.eateri.ui.cart.CartActivity
import com.google.firebase.auth.FirebaseAuth
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity(){


    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       setTheme(R.style.AppTheme)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        var navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        //Check if user exists
        if (FirebaseAuth.getInstance().currentUser != null) {
            navView.menu.setGroupVisible(
                R.id.loggedInDrawer,
                true
            )
            navView.menu.setGroupVisible(
                R.id.normal_drawer,
                false
            )
        }else{
            navView.menu.setGroupVisible(
                R.id.normal_drawer,
                true
            )
        }
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_myProfile,
                R.id.nav_myOrders,
                R.id.myPaymentFragment,
                R.id.nav_setting,
                R.id.nav_help,
                R.id.nav_register,
                R.id.loginFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        //Logout
        navView.menu.findItem(R.id.logoutUserBtn).setOnMenuItemClickListener {
            logout()
            return@setOnMenuItemClickListener true
        }


        navView.setupWithNavController(navController)


    }

    //Logout Methods
    private fun logout(){
        val fAuth: FirebaseAuth = FirebaseAuth.getInstance()
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(this, "Signed Out", Toast.LENGTH_SHORT).show()
        val intent : Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //Override Methods
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cartButton -> {
                val intent : Intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun setTheme(theme: Resources.Theme?) {
        val sw:Switch=findViewById(R.id.darkMode)
        if(sw.isChecked){
            setTheme(R.style.darktheme)
        }
        else{
            setTheme(R.style.AppTheme)
        }

       return super.setTheme(theme)
    }


}

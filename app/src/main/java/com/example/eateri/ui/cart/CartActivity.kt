package com.example.eateri.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Switch
import androidx.recyclerview.widget.*
import com.example.eateri.MainActivity
import com.example.eateri.R
import com.example.eateri.ui.datas.OrderList
import com.example.eateri.ui.help.CartItems
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity() : AppCompatActivity() {

    private var dataset : ArrayList<OrderList> = intent.getSerializableExtra("BASKET") as ArrayList<OrderList>
    private var cartAdapter: CartAdapter = CartAdapter(dataset)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "My Cart"

        Snackbar.make(this.currentFocus!!, dataset.toString(), Snackbar.LENGTH_LONG).show()
        cartRecyclerView.apply {
            layoutManager   = LinearLayoutManager(context)
            itemAnimator    = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(context,DividerItemDecoration.VERTICAL)
            )
            adapter         = cartAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return true
    }
}

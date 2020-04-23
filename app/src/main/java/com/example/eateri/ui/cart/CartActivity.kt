package com.example.eateri.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.*
import androidx.room.Room
import com.example.eateri.MainActivity
import com.example.eateri.R
import com.example.eateri.helpers.CartDB
import com.example.eateri.ui.datas.OrderList
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity() : AppCompatActivity() {

//    private var dataset : ArrayList<OrderList> = intent.getSerializableExtra("BASKET") as ArrayList<OrderList>

    private lateinit var cartAdapter: CartAdapter
    private var list : ArrayList<OrderList> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "My Cart"

        var db = Room.databaseBuilder(applicationContext,
            CartDB::class.java, "cartDB.db"
        ).build()

        Thread{
            db.CartDao().getCartList().forEach {
                //Log.d("@Basket","orderID: ${it.orderID} | qty : ${it.orderQty} | foodID : ${it.foodItemID} / ${it.foodItemID}")
                list.add(OrderList(it.orderID, it.foodItemID, it.foodName, it.foodPrice, it.orderQty, it.img))
            }
        }.start()

        cartAdapter = CartAdapter(list)
        //Snackbar.make(this.currentFocus!!, dataset.toString(), Snackbar.LENGTH_LONG).show()

        cartRecyclerView.apply {
            layoutManager   = LinearLayoutManager(context)
            itemAnimator    = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(context,DividerItemDecoration.VERTICAL)
            )
            adapter         = cartAdapter
        }
        db.close()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return true
    }

    override fun onStop() {
        super.onStop()

    }
}

package com.example.eateri.foodDetails

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.eateri.DataSource
import com.example.eateri.R
import com.example.eateri.helpers.CartDB
import com.example.eateri.ui.datas.OrderList
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.food_details_container.view.*
import timber.log.Timber


class FoodDetailsRecyclerAdapter(rid : Int, restPos: Int, private var appContext : Context) : RecyclerView.Adapter<CustomerViewHolder>(){
    private var restId = rid
    private var restPos = restPos
    private val dataSource: DataSource = DataSource()

    override fun getItemCount(): Int {
        var size = 0
        for(i in dataSource.menu.indices){
            if(restId == dataSource.menu.get(i).restID){
                size +=1
            }
        }
        return size
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        var collectedRest = arrayListOf<collectionRest>()
        var index = 0
        for(i in dataSource.menu.indices){
            if(restId == dataSource.menu.get(i).restID){
                var fid         = dataSource.menu.get(index).foodID
                var foodName    = dataSource.menu.get(index).foodName
                var foodPrice   = String.format("%.2f", dataSource.menu.get(index).foodPrice)
                var image       = dataSource.menu.get(index).foodImageView

                collectedRest.add(collectionRest(fid, foodName, foodPrice, image))
            }
            index +=1
        }

        holder.view.foodContainer_imageView_food.setImageResource(collectedRest.get(position).image)
        holder.view.foodContainer_textView_foodName.text = collectedRest.get(position).foodName
        holder.view.foodContainer_textView_price.text = "RM ${collectedRest.get(position).price}"
        holder.view.foodDetails_container.setOnClickListener {
            val col = collectedRest.get(position)
            //Snackbar.make(holder.view, "fid: ${collectedRest.get(position).foodID} | foodList : ${col.foodName}, ${col.price}", Snackbar.LENGTH_LONG).show()
            Snackbar.make(holder.view, "Item added to cart", Snackbar.LENGTH_LONG).show()
            //orderList.add(OrderList(orderId, 1, Cart(col.foodName, col.price)))
            Timber.d("Populating DB")
            var db = Room.databaseBuilder(appContext,
                CartDB::class.java, "cartDB.db"
            ).build()

            Thread{
                var entity = OrderList()
                entity.foodItemID       = collectedRest.get(position).foodID
                entity.foodName         = collectedRest.get(position).foodName
                entity.foodPrice        = collectedRest.get(position).price
                entity.orderQty = 1

                //Log.d("@Cart", "${db.CartDao().getCartList().size}")
                if(db.CartDao().getCartList().size == 0){
                    db.CartDao().insertCart(entity)
                    Log.d("@Cart","orderID: ${entity.orderID} | qty : ${entity.orderQty} | foodID : ${entity.foodItemID} / ${entity.foodItemID}")
                }else{
                    db.CartDao().getCartList().forEach{
                        if(it.foodItemID == entity.foodItemID){
                            entity.orderQty +=1
                            Log.d("@Cart", "Add same food")
                            db.CartDao().updateCart(entity)
                        }else{
                            Log.d("@Cart","orderID: ${it.orderID} | qty : ${it.orderQty} | foodID : ${it.foodItemID} / ${it.foodItemID}")
                            db.CartDao().insertCart(entity)
                        }
                    }
                }
            }.start()
            db.close() //close DB
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.food_details_container, parent, false)
        return CustomerViewHolder(cellForRow)
    }
}

class CustomerViewHolder(val view : View) : RecyclerView.ViewHolder(view){}

class collectionRest (
    val foodID      : Int,
    val foodName    : String,
    val price       : String,
    val image       : Int
)


package com.example.eateri.helpers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.eateri.ui.datas.OrderList

@Database(entities = arrayOf(OrderList::class), version = 1, exportSchema = false)
abstract class CartDB : RoomDatabase(){
    abstract fun CartDao() : CartDAO

    companion object {
        @Volatile private var instance: CartDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            CartDB::class.java, "CART.db")
            .build()
    }
}
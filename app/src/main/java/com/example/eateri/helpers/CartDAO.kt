package com.example.eateri.helpers

import androidx.room.*
import com.example.eateri.ui.datas.OrderList

@Dao
interface CartDAO {
    @Query("SELECT * FROM CART")
    fun getCartList() : List<OrderList>

    @Insert
    fun insertCart(cart : OrderList)

    @Update
    fun updateCart(cart : OrderList)

    @Delete
    fun deleteCart(cart : OrderList)
}
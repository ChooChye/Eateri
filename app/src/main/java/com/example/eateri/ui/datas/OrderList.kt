package com.example.eateri.ui.datas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CART")
class OrderList {
    @PrimaryKey
    var orderID: Int

    @ColumnInfo(name = "QTY")
    var qty: Int

    @ColumnInfo(name = "foodID")
    var foodItemID: Int

    constructor(oid : Int, qty : Int, fid : Int){
        this.orderID    = oid
        this.qty        = qty
        this.foodItemID = fid
    }
}
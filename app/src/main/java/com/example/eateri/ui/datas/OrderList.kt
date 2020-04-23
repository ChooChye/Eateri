package com.example.eateri.ui.datas

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CART")
class OrderList() {
    @PrimaryKey(autoGenerate = true)
    var orderID: Int = 0

    @ColumnInfo(name = "foodID")
    var foodItemID : Int = 0

    @ColumnInfo(name = "foodName")
    var foodName: String = ""

    @ColumnInfo(name = "foodPrice")
    var foodPrice: String = ""

    @ColumnInfo(name = "QTY")
    var orderQty : Int = 0

    @ColumnInfo(name = "IMG")
    var img : Int = 0

    constructor(orderID : Int, foodID : Int, foodName : String, foodPrice : String, qty : Int, img : Int) : this(){
        this.orderID        = orderID
        this.foodItemID     = foodID
        this.foodName       = foodName
        this.foodPrice      = foodPrice
        this.orderQty       = qty
        this.img            = img
    }
}
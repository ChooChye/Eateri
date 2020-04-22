package com.example.eateri

import com.example.eateri.helpers.MenuList


class DataSource {
    var restID : Int = 0

    val list = listOf<RestaurantList>(
        RestaurantList(1000, "Masakan Malaysia", "OPEN"),
        RestaurantList(1001, "Mixed Rice", "OPEN"),
        RestaurantList(1002, "Vegetarian Mix Rice", "OPEN"),
        RestaurantList(1000, "Chicken Rice", "OPEN"),
        RestaurantList(1000, "Noodles", "OPEN")
    )

    val menu = listOf<MenuList>(
        MenuList(1000, R.drawable.rest2menu1, "Chicken Rice Combo", 4.00),
        MenuList(1000, R.drawable.rest2menu1, "Quarter Combo", 6.00),
        MenuList(1001, R.drawable.rest2menu2, "Nasi Lemak", 2.50)

    )

    fun setRestId(id:Int){
        this.restID = id
    }

    fun getRestId() : Int{
        return this.restID
    }


}




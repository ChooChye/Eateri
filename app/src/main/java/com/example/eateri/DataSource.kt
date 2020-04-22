package com.example.eateri

import com.example.eateri.helpers.MenuList


class DataSource {

    val list = listOf<RestaurantList>(
        RestaurantList(1000, "Masakan Malaysia", "OPEN", R.drawable.rest_mm),
        RestaurantList(1001, "Chicken Rice", "OPEN", R.drawable.rest_chicken_rice),
        RestaurantList(1002, "Mix Rice", "OPEN", R.drawable.rest_mixed_rice),
        RestaurantList(1003, "Noodles", "OPEN", R.drawable.rest_noodles)
    )

    val menu = listOf<MenuList>(
        MenuList(1000, R.drawable.rest1menu1, "Nasi Lemak", 2.50),
        MenuList(1000, R.drawable.rest1menu2, "Mee Goreng", 5.00),
        MenuList(1000, R.drawable.rest1menu3, "Nasi Goreng", 5.50),

        //1001
        MenuList(1001, R.drawable.rest2menu1, "Chicken Rice Combo", 4.80),
        MenuList(1001, R.drawable.rest2menu1, "Quarter Combo", 6.00),

        //1002
        MenuList(1002, R.drawable.rest3menu1, "Thai Fried Rice", 5.80),
        MenuList(1002, R.drawable.rest3menu2, "Wan Tan Mee", 5.00),

        //1003
        MenuList(1003, R.drawable.rest4menu1, "Loh Su Fun", 5.50),
        MenuList(1003, R.drawable.rest4menu2, "Pan Mee", 6.00)

    )
}




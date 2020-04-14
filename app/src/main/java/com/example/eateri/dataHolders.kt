package com.example.eateri

import java.util.ArrayList

class UserInfo(
    var UserList: ArrayList<UserItem> = arrayListOf()
)

data class UserItem(
    var uid: String = "",
    var fName: String = "",
    var lName: String = "",
    var email: String = "",
    var num: Int = 0,
    var pass: String = ""
)
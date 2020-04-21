package com.example.eateri

import com.example.eateri.ui.datas.VendorItem
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


class VendorInfo(
    var VendorList: ArrayList<VendorItem> = arrayListOf()
)

data class VendorItem(
    var vdName: String = "",
    var vdEmail: String = "",
    var vdHP: Int = 0,
    var vdPass: String = ""
)
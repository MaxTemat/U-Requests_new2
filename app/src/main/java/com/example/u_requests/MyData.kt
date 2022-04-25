package com.example.u_requests

import android.view.MenuItem

class MyData {
    companion object{
        var info = ""
        var menuItems = arrayListOf<MenuItem>()
        var selectCount = 0
        var positionsSelected = arrayListOf<Int>()
        var listIdToDelete = arrayListOf<String>()
    }
}
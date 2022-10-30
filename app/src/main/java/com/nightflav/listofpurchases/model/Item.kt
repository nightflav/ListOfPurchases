package com.nightflav.listofpurchases.model

data class Item(
    var category: String,
    var itemTitle: String,
    var amount: Int,
    val id: String
)
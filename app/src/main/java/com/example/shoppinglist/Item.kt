package com.example.shoppinglist

enum class Category {
    food, electronic, book, clothing
}

class Item {
    //getters and setters
    var itemID = 0
    var category: Category? = null
    var name: String? = null
    var description: String? = null
    var priceInHUF = 0
    var isStatus = false
    var quantity = 0
    internal constructor() {}
    internal constructor(
        itemID: Int,
        category: Category?,
        name: String?,
        description: String?,
        priceInHUF: Int,
        status: Boolean,
        quantity: Int
    ) {
        this.itemID = itemID
        this.category = category
        this.name = name
        this.description = description
        this.priceInHUF = priceInHUF
        this.quantity = quantity
        isStatus = status
    }
}
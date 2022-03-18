package com.example.shoppinglist.UI

import com.example.shoppinglist.ItemData

interface DialogListener {
    fun onSaveButtonClicked(item: ItemData)
}
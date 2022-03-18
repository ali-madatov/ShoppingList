package com.example.shoppinglist.UI

import androidx.lifecycle.ViewModel
import com.example.shoppinglist.ItemData
import com.example.shoppinglist.ListRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListViewModel(private val repository: ListRepository): ViewModel() {
    fun insert(item: ItemData) = GlobalScope.launch {
        repository.insert(item)
    }

    fun update(item: ItemData) = GlobalScope.launch {
        repository.update(item)
    }
    fun delete(item: ItemData) = GlobalScope.launch {
        repository.delete(item)
    }



    fun allListItems() = repository.allListItems()
}
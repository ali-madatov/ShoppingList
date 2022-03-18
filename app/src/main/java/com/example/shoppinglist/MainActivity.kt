package com.example.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListAdapter
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppinglist.UI.DialogListener
import com.example.shoppinglist.UI.ListViewModel
import com.example.shoppinglist.UI.ListViewModelFactory
import com.example.shoppinglist.UI.NewItemDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel
    lateinit var list: List<ItemData>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbarID)

        val listRepository = ListRepository(ListDatabase(this))
        val factory = ListViewModelFactory(listRepository)

        viewModel = ViewModelProvider(this,factory).get(ListViewModel::class.java)
        val listAdapter = ListItemAdapter(listOf(), viewModel)
        recyclerViewID.layoutManager = LinearLayoutManager(this)
        recyclerViewID.adapter = listAdapter

        viewModel.allListItems().observe(this, Observer {
            listAdapter.newList = it
            list = it
            listAdapter.notifyDataSetChanged()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var selectedView = item.itemId
        when(selectedView){
            R.id.imAddItem -> NewItemDialog(this,object : DialogListener{
                override fun onSaveButtonClicked(item: ItemData) {
                    viewModel.insert(item)
                }
            }).show()
            R.id.imDeleteAll -> deleteAllItems()
        }
        return false
    }

    private fun deleteAllItems(): Boolean{
        for(dItem in list)
            viewModel.delete(dItem)
        return true
    }
}
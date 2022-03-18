package com.example.shoppinglist

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.API.CurrencyAPI
import com.example.shoppinglist.Data.CurrencyResult
import com.example.shoppinglist.Data.Rates
import com.example.shoppinglist.UI.*
import kotlinx.android.synthetic.main.item_view.*
import kotlinx.android.synthetic.main.item_view.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.coroutineContext

class ListItemAdapter(var newList : List<ItemData>, val viewModel: ListViewModel) : RecyclerView.Adapter<ListItemAdapter.ListItemViewHolder>() {

    inner class ListItemViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    lateinit var currentContext: Context
    lateinit var currencyAPI: CurrencyAPI

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view,parent,false)
        currentContext = parent.context
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.frankfurter.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        currencyAPI = retrofit.create(CurrencyAPI::class.java)
        if(currencyAPI==null)
            Log.i("API","api is null")
        else
            Log.i("API","api is not null")

        return ListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        var currentItem = newList[position]
        when (currentItem.category){
            Category.book -> holder.itemView.categoryView.setImageResource(R.drawable.ic_book)
            Category.clothing -> holder.itemView.categoryView.setImageResource(R.drawable.ic_clothing)
            Category.electronic -> holder.itemView.categoryView.setImageResource(R.drawable.ic_electronics)
            else -> holder.itemView.categoryView.setImageResource(R.drawable.ic_foodicon)
        }
        holder.itemView.statusView.isChecked = currentItem.isStatus
        holder.itemView.itemNameView.text = currentItem.name
        holder.itemView.itemDescView.text = currentItem.description
        var pricesIn3Curr = currentItem.priceInHUF.toString().plus(" HUF\n")
        val currencyCall = currencyAPI.getMoney("HUF")
        currencyCall.enqueue(object : Callback<CurrencyResult> {
            override fun onFailure(call: Call<CurrencyResult>, t: Throwable) {
                Log.e("API: ",t.message!!)
            }

            override fun onResponse(call: Call<CurrencyResult>, response: Response<CurrencyResult>) {
                var currencyResult = response.body()

                if(currencyResult==null)
                    Log.i("API: ", "result is null")

                val usdPrice: Double = currencyResult?.rates!!.USD!!.toDouble()*currentItem.priceInHUF.toDouble()
                val gbpPrice: Double = currencyResult?.rates!!.GBP!!.toDouble()*currentItem.priceInHUF.toDouble()
                val plnPrice: Double = currencyResult?.rates!!.PLN!!.toDouble()*currentItem.priceInHUF.toDouble()
                pricesIn3Curr = pricesIn3Curr.plus(String.format("%.2f",usdPrice))
                    .plus(" USD\n").plus(String.format("%.2f",gbpPrice))
                    .plus(" GBP\n").plus(String.format("%.2f",plnPrice))
                    .plus(" PLN")
                holder.itemView.itemPriceView.text = pricesIn3Curr
            }
        })

        holder.itemView.itemQuantityView.text = currentItem.quantity.toString()

//        holder.itemView.statusView.setOnCheckedChangeListener{buttonView, isChecked ->
//            if(isChecked){
//                ItemCompletedDialog(currentContext,object: DialogListener{
//                    override fun onSaveButtonClicked(item: ItemData) {
//                        viewModel.update(item)
//                    }
//                },currentItem,true).show()
//            }
//            else{
//                ItemCompletedDialog(currentContext,object: DialogListener{
//                    override fun onSaveButtonClicked(item: ItemData) {
//                        viewModel.update(item)
//                    }
//                },currentItem,false).show()
//            }
//        }

        holder.itemView.deleteButton.setOnClickListener{
            viewModel.delete(currentItem)
        }
        holder.itemView.editButton.setOnClickListener{
            EditItemDialog(currentContext,object : DialogListener {
                override fun onSaveButtonClicked(item: ItemData) {
                    viewModel.update(item)
                }
            },currentItem).show()
        }

    }

    private fun updateItem(updatedItem: ItemData): Boolean{
        viewModel.update(updatedItem)
        return true
    }

    fun getCurrencies(): Rates?{
        var rates: Rates? = null
        val currencyCall = currencyAPI.getMoney("EUR")
        currencyCall.enqueue(object : Callback<CurrencyResult> {
            override fun onFailure(call: Call<CurrencyResult>, t: Throwable) {
                Log.e("API: ",t.message!!)
            }

            override fun onResponse(call: Call<CurrencyResult>, response: Response<CurrencyResult>) {
                Log.e("API1: ", "result is  back null")
                var currencyResult = response.body()

                if(currencyResult==null)
                    Log.i("API2: ", "result is null")
                rates = currencyResult?.rates
            }
        })
        Log.i("Base",rates.toString())
        return rates
    }

    override fun getItemCount(): Int {
        return newList.size

    }
}
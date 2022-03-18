package com.example.shoppinglist.API

import com.example.shoppinglist.Data.CurrencyResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyAPI {
    @GET("/latest")
    fun getMoney(@Query("from") base: String) : Call<CurrencyResult>

    @GET("/{date}")
    fun getHistoric(@Path("date") date: String) : Call<CurrencyResult>
}
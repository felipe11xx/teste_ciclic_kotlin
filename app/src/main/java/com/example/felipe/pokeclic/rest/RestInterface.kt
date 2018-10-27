package com.example.felipe.pokeclic.rest

import com.example.felipe.pokeclic.domain.Card
import com.example.felipe.pokeclic.domain.CardDao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RestInterface {
    @GET("cards?count=20")
    fun listarCards() : Call<CardDao>

    @GET("cards/{id}")
    fun getCard(@Path("id")id:String) : Call<CardDao>
}
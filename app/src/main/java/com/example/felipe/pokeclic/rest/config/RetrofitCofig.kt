package com.example.felipe.pokeclic.rest.config

import com.example.felipe.pokeclic.rest.RestInterface
import com.example.felipe.pokeclic.rest.commons.AppUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitCofig{

    val gson:Gson = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

    val retrofit =
        Retrofit.Builder()
            .baseUrl(AppUtils.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


    fun initClient( client: OkHttpClient){
        Retrofit.Builder()
            .baseUrl(AppUtils.API_BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun restInterface(): RestInterface{
        return retrofit.create(RestInterface::class.java)
    }
}

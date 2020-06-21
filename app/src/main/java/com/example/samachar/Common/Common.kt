package com.example.samachar.Common

import com.example.samachar.Interface.NewService
import com.example.samachar.Remote.RetrofitClient
import java.lang.StringBuilder

object Common{
    val BASE_URL="https://newsapi.org/"
    val API_KEY="e09d05e330b1442ebfc02fb22614c193"

    val newsService:NewService
    get()=RetrofitClient.getClient(BASE_URL).create(NewService::class.java)
//To get News
    fun getNewsAPI(source:String):String{
        val apiUrl = StringBuilder("https://newsapi.org/v2/top-headlines?sources=")
            .append(source)
            .append("&apiKey=")
            .append(API_KEY)
            .toString()
        return apiUrl
    }
}
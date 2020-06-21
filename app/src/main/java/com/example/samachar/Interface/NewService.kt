package com.example.samachar.Interface

import com.example.samachar.Model.News
import com.example.samachar.Model.WebSite
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NewService {
    @get:GET("v2/sources?apiKey=e09d05e330b1442ebfc02fb22614c193")
    val  sources: Call<WebSite>
    @GET
    fun getnewsFromSource(@Url url:String):Call<News>
}
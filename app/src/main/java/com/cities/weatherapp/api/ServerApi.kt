package com.cities.weatherapp.api

import com.cities.weatherapp.ui.citylisting.model.WeatherResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerApi {

    @GET("current.json")
    suspend fun getCityWeatherAndImage(@Query("key")  key:String , @Query("q")  q:String ) : WeatherResponseModel


}
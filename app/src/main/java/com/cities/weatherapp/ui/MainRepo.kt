package com.cities.weatherapp.ui

import com.cities.weatherapp.api.GenericApiResponse
import com.cities.weatherapp.ui.citylisting.model.WeatherResponseModel
import javax.inject.Inject

class MainRepo @Inject constructor(
    private val remoteSource : MainRemoteDataSource
    ) {
    suspend fun getCityWeatherAndImage(cityName: String): GenericApiResponse<WeatherResponseModel> {
        return remoteSource.getCityWeatherAndImage(cityName)
    }
}




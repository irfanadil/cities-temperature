package com.cities.weatherapp.ui

import com.cities.weatherapp.BuildConfig
import com.cities.weatherapp.api.FailureStatus
import com.cities.weatherapp.api.GenericApiResponse
import com.cities.weatherapp.api.ServerApi
import com.cities.weatherapp.ui.citylisting.model.WeatherResponseModel
import javax.inject.Inject

class MainRemoteDataSource  @Inject constructor(private val serverApi: ServerApi) {

    suspend fun getCityWeatherAndImage(cityName: String): GenericApiResponse<WeatherResponseModel> {
        return try {
            val response = serverApi.getCityWeatherAndImage(BuildConfig.GOOGLE_PLACES_API_KEY, cityName)
            GenericApiResponse.Success(response)
        } catch (e: Throwable) {
            GenericApiResponse.Failure(FailureStatus.OTHER, 60,e.message)
        }
    }
}
package com.cities.weatherapp.ui


import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cities.weatherapp.api.GenericApiResponse
import com.cities.weatherapp.common.AppConstant
import com.cities.weatherapp.ui.citylisting.model.CityWeatherModel
import com.cities.weatherapp.ui.citylisting.model.WeatherResponseModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepo: MainRepo,
    private val citiesWeatherDetailList: ArrayList<CityWeatherModel>,
    private val gson: Gson,
    private val sharedPreferencesEditor: SharedPreferences.Editor) : ViewModel() {

    private val _weatherResponseModel = MutableLiveData<GenericApiResponse<WeatherResponseModel>>()
    val weatherResponseModel: LiveData<GenericApiResponse<WeatherResponseModel>> = _weatherResponseModel

    fun getCityWeatherAndImage(cityName: String){
        viewModelScope.launch(Dispatchers.IO) {
            _weatherResponseModel.postValue(mainRepo.getCityWeatherAndImage(cityName))
        }
    }

    fun getCitiesWeatherList():ArrayList<CityWeatherModel> = citiesWeatherDetailList

    fun saveCityToLocalList(selectedCityName:String, countryName: String):Boolean{
        for( d in citiesWeatherDetailList){
            if(d.cityName == selectedCityName)
             return false
        }
        citiesWeatherDetailList.add(CityWeatherModel(selectedCityName , countryName,"0 C"))
        val saveCustomObject = gson.toJson(citiesWeatherDetailList)
        sharedPreferencesEditor.putString(AppConstant.CITY_WEATHER_LIST, saveCustomObject).apply()
        return true
    }

}
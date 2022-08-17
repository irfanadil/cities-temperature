package com.cities.weatherapp.di

import android.content.SharedPreferences
import com.cities.weatherapp.common.AppConstant
import com.cities.weatherapp.ui.citylisting.model.CityWeatherModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    @ViewModelScoped
    fun provideViewModelScopeList(sharedPreferences:SharedPreferences, gson:Gson): ArrayList<CityWeatherModel> {

        val list = sharedPreferences.getString(AppConstant.CITY_WEATHER_LIST, null)
        return if(list.isNullOrEmpty())
            arrayListOf<CityWeatherModel>()
        else {
            gson.fromJson(list, object :TypeToken<ArrayList<CityWeatherModel>>(){}.type)
        }
    }





}
package com.cities.weatherapp

import android.app.Application
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CitiesWeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Places.initialize(this, "AIzaSyBzPEiQOTReBzy6W1UcIyHApPu7_5Die6w")
    }

    companion object {
        lateinit var instance: CitiesWeatherApp
    }


}
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
    }

    // To provide app context in the AppModule for non activity classes....
    companion object {
        lateinit var instance: CitiesWeatherApp
    }


}
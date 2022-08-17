package com.cute.connection.di



import android.content.Context
import android.content.SharedPreferences
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.cities.weatherapp.BuildConfig
import com.cities.weatherapp.CitiesWeatherApp
import com.cities.weatherapp.api.ServerApi
import com.cities.weatherapp.common.AppConstant
import com.cities.weatherapp.ui.city.GooglePlacesClass
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


        @Singleton
        @Provides
        fun provideSharedPrefsEditor( sharedPreferences: SharedPreferences): SharedPreferences.Editor {
            return sharedPreferences.edit()
        }

        @Singleton
        @Provides
        fun provideSharedPreferences(  @ApplicationContext context: Context): SharedPreferences {
            return context.getSharedPreferences(AppConstant.APP_PREFERENCES, Context.MODE_PRIVATE)
        }

        @Singleton
        @Provides
        fun provideGson( ): Gson {
            return Gson()
        }

        @Singleton
        @Provides
        fun provideGlideInstance(@ApplicationContext context: Context,  requestOptions: RequestOptions): RequestManager {
            return Glide.with(context)
                .setDefaultRequestOptions(requestOptions)
        }

        @Provides
        @Singleton
        fun provideOkHttp():OkHttpClient {
            return OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(60,TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY })
                //.addNetworkInterceptor(networkInterceptor)
                .build()
        }

        @Provides
        @Singleton
        fun retrofit(okHttpClient: OkHttpClient): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL) // Hide base url in local properties
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Provides
        @Singleton
        fun appServices(retrofit: Retrofit): ServerApi = retrofit.create(ServerApi::class.java)

        @Provides
        @Singleton
        fun provideGooglePlaces(applicationContext:Context): GooglePlacesClass = GooglePlacesClass(applicationContext)


        // For providing the application context to the Non activity and fragment class...
        @Provides
        @Singleton
        fun provideAppContext(): Context = CitiesWeatherApp.instance




}


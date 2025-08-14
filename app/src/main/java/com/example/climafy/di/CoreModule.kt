package com.example.climafy.di

import android.app.Application
import androidx.room.Room
import com.example.climafy.data.local.WeatherDatabase
import com.example.climafy.data.local.dao.FavoriteCityDao
import com.example.climafy.data.local.dao.LastWeatherDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideDatabase(app: Application): WeatherDatabase =
        Room.databaseBuilder(
            app,
            WeatherDatabase::class.java,
            "climafy_db"
        ).build()

    @Provides
    @Singleton
    fun provideFavoriteCityDao(db: WeatherDatabase): FavoriteCityDao =
        db.favoriteCityDao()

    @Provides
    @Singleton
    fun provideLastWeatherDao(db: WeatherDatabase): LastWeatherDao =
        db.lastWeatherDao()
}

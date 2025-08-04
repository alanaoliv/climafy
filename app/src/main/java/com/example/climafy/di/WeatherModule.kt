package com.example.climafy.di

import com.example.climafy.data.local.WeatherDatabase
import com.example.climafy.data.local.dao.LastWeatherDao
import com.example.climafy.data.remote.WeatherApi
import com.example.climafy.data.repository.WeatherRepositoryImpl
import com.example.climafy.domain.repository.WeatherRepository
import com.example.climafy.domain.usecase.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        api: WeatherApi,
        lastWeatherDao: LastWeatherDao
    ): WeatherRepository {
        return WeatherRepositoryImpl(api, lastWeatherDao)
    }

    @Provides
    @Singleton
    fun provideGetWeatherUseCase(repository: WeatherRepository): GetWeatherUseCase {
        return GetWeatherUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideLastWeatherDao(db: WeatherDatabase): LastWeatherDao {
        return db.lastWeatherDao()
    }
}

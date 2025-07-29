package com.example.climafy.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import android.app.Application
import androidx.room.Room
import com.example.climafy.data.local.WeatherDatabase
import com.example.climafy.data.local.dao.FavoriteCityDao
import com.example.climafy.data.repository.FavoriteCityRepositoryImpl
import com.example.climafy.domain.repository.FavoriteCityRepository

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): WeatherDatabase {
        return Room.databaseBuilder(
            app,
            WeatherDatabase::class.java,
            "climafy_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavoriteCityDao(db: WeatherDatabase): FavoriteCityDao {
        return db.favoriteCityDao()
    }

    @Provides
    @Singleton
    fun provideFavoriteCityRepository(
        dao: FavoriteCityDao
    ): FavoriteCityRepository {
        return FavoriteCityRepositoryImpl(dao)
    }

}

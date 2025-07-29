package com.example.climafy.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.climafy.data.local.entity.FavoriteCityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteCityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun inserir(city: FavoriteCityEntity)

    @Delete
    suspend fun deletar(city: FavoriteCityEntity)

    @Query("SELECT * FROM favorite_cities")
    fun listarTodas(): Flow<List<FavoriteCityEntity>>
}

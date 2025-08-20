package com.example.climafy.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

object WeatherMigrations {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE favorite_cities ADD COLUMN tempMax REAL")
            database.execSQL("ALTER TABLE favorite_cities ADD COLUMN tempMin REAL")
            database.execSQL("ALTER TABLE last_weather ADD COLUMN tempMax REAL")
            database.execSQL("ALTER TABLE last_weather ADD COLUMN tempMin REAL")
        }
    }
}
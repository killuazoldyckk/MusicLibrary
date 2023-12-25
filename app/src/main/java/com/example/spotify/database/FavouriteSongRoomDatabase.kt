package com.example.spotify.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavouriteSong::class], version = 1)
abstract class FavouriteSongRoomDatabase : RoomDatabase() {

    abstract fun favouriteSongDao(): FavouriteSongDao

    companion object {
        @Volatile
        private var INSTANCE: FavouriteSongRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context):FavouriteSongRoomDatabase{
            if(INSTANCE == null) {
                synchronized(FavouriteSongRoomDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavouriteSongRoomDatabase::class.java, "favourite_song_database")
                        .build()
                }
            }

            return INSTANCE as FavouriteSongRoomDatabase
        }
    }
}

package xyz.moratech.android.albotest.features.details.data.repositories.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BeersEntity::class], version = 1)
abstract class BeersDatabase : RoomDatabase() {
    abstract fun beersDao(): BeersDao

    companion object {
        private var instance: BeersDatabase? = null

        fun instance(context: Context): BeersDatabase {
            if (instance == null) {
                instance =
                    Room.databaseBuilder(context, BeersDatabase::class.java, "Beers.db")
                        .build()
            }
            return instance!!
        }
    }
}
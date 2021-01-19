package xyz.moratech.android.albotest.features.details.data.repositories.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BeersDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveBeer(beer: BeersEntity)

    @Query("SELECT * FROM BeersEntity")
    suspend fun getBeers(): List<BeersEntity>
}

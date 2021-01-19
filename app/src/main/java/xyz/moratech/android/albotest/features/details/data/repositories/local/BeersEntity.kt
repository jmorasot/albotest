package xyz.moratech.android.albotest.features.details.data.repositories.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BeersEntity(
    @PrimaryKey
    val attrs: String
)
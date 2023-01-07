package mrandroid.league.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AreaEntity(
    @PrimaryKey(autoGenerate = false) val id: Long,
    var name: String,
    var countryCode: String,
    var ensignUrl: String
)
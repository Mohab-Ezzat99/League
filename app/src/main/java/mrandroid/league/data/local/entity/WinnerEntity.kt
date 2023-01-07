package mrandroid.league.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WinnerEntity(
    @PrimaryKey(autoGenerate = false) var id: Long,
    var name: String,
    var shortName: String,
    var tla: String,
    var crestUrl: String
)
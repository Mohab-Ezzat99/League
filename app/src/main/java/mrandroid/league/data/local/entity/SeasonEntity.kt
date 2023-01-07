package mrandroid.league.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SeasonEntity(
    @PrimaryKey(autoGenerate = false) val id: Long,
    var startDate: String,
    var endDate: String,
    var currentMatchDay: Int,
    var winner: WinnerEntity? = null
)
package mrandroid.league.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompetitionEntity(
    @PrimaryKey(autoGenerate = false) val id: Long,
    var area: AreaEntity? = null,
    var name: String,
    var code: String,
    var emblemUrl: String,
    var plan: String,
    var currentSeason: SeasonEntity? = null,
    var seasons: List<SeasonEntity>? = null,
    var numberOfAvailableSeasons: Int,
    var lastUpdated: String
)

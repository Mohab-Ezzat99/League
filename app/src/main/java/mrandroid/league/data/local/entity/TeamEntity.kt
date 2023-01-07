package mrandroid.league.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TeamEntity(
    @PrimaryKey(autoGenerate = false) var id: Long,
    var area: AreaEntity? = null,
    var activeCompetitions: List<ActiveCompetitionsEntity>? = null,
    var name: String,
    var shortName: String,
    var tla: String,
    var crestUrl: String,
    var address: String,
    var phone: String,
    var website: String,
    var email: String,
    var founded: String,
    var clubColors: String,
    var venue: String,
    var squad: List<SquadEntity>? = null,
    var lastUpdated: String
)

@Entity
data class ActiveCompetitionsEntity(
    @PrimaryKey(autoGenerate = false) var id: Long,
    var area: AreaEntity? = null,
    var name: String,
    var code: String,
    var plan: String,
    var lastUpdated: String
)

@Entity
data class SquadEntity(
    @PrimaryKey(autoGenerate = false) var id: Long,
    var name: String,
    var position: String,
    var dateOfBirth: String,
    var countryOfBirth: String,
    var nationality: String,
    var shirtNumber: String,
    var role: String
)
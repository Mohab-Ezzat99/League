package mrandroid.league.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import mrandroid.league.data.local.entity.ActiveCompetitionsEntity
import mrandroid.league.data.local.entity.SquadEntity
import mrandroid.league.data.local.entity.TeamEntity
import mrandroid.league.data.remote.dto.AreaDto

data class TeamResponse(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("area") var area: AreaDto? = null,
    @SerializedName("activeCompetitions") var activeCompetitions: ArrayList<ActiveCompetitionsDto>? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("shortName") var shortName: String? = null,
    @SerializedName("tla") var tla: String? = null,
    @SerializedName("crestUrl") var crestUrl: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("website") var website: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("founded") var founded: Int? = null,
    @SerializedName("clubColors") var clubColors: String? = null,
    @SerializedName("venue") var venue: String? = null,
    @SerializedName("squad") var squad: ArrayList<SquadDto>? = null,
    @SerializedName("lastUpdated") var lastUpdated: String? = null
) {
    fun toTeamEntity() = TeamEntity(
        id = id ?: 0,
        area = area?.toAreaEntity(),
        activeCompetitions = activeCompetitions?.map { it.toActiveCompetitionsEntity() },
        name = name ?: "Unknown",
        shortName = shortName ?: "Unknown",
        tla = tla ?: "Unknown",
        crestUrl = crestUrl ?: "",
        address = address ?: "Unknown",
        phone = phone ?: "Unknown",
        website = website ?: "Unknown",
        email = email ?: "Unknown",
        founded = founded?.toString() ?: " Unknown",
        clubColors = clubColors ?: "Unknown",
        venue = venue ?: "Unknown",
        squad = squad?.map { it.toSquadEntity() },
        lastUpdated = lastUpdated ?: "Unknown",
    )
}

data class ActiveCompetitionsDto(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("area") var area: AreaDto? = AreaDto(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("plan") var plan: String? = null,
    @SerializedName("lastUpdated") var lastUpdated: String? = null
) {
    fun toActiveCompetitionsEntity() = ActiveCompetitionsEntity(
        id = id ?: 0,
        area = area?.toAreaEntity(),
        name = name ?: "Unknown",
        code = code ?: "Unknown",
        plan = plan ?: "Unknown",
        lastUpdated = lastUpdated ?: "Unknown"
    )
}

data class SquadDto(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("position") var position: String? = null,
    @SerializedName("dateOfBirth") var dateOfBirth: String? = null,
    @SerializedName("countryOfBirth") var countryOfBirth: String? = null,
    @SerializedName("nationality") var nationality: String? = null,
    @SerializedName("shirtNumber") var shirtNumber: String? = null,
    @SerializedName("role") var role: String? = null
) {
    fun toSquadEntity() = SquadEntity(
        id = id ?: 0,
        name = name ?: "Unknown",
        position = position ?: "Unknown",
        dateOfBirth = dateOfBirth ?: "Unknown",
        countryOfBirth = countryOfBirth ?: "Unknown",
        nationality = nationality ?: "Unknown",
        shirtNumber = shirtNumber ?: "Unknown",
        role = role ?: "Unknown"
    )
}
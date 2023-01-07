package mrandroid.league.data.remote.dto

import com.google.gson.annotations.SerializedName
import mrandroid.league.data.local.entity.CompetitionEntity

data class CompetitionDto(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("area") var area: AreaDto? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("emblemUrl") var emblemUrl: String? = null,
    @SerializedName("plan") var plan: String? = null,
    @SerializedName("currentSeason") var currentSeason: SeasonDto? = null,
    @SerializedName("seasons") var seasons: ArrayList<SeasonDto>? = null,
    @SerializedName("numberOfAvailableSeasons") var numberOfAvailableSeasons: Int? = null,
    @SerializedName("lastUpdated") var lastUpdated: String? = null
) {
    fun toCompetitionEntity() = CompetitionEntity(
        id = id ?: 0,
        area = area?.toAreaEntity(),
        name = name ?: "Unknown",
        code = code ?: "Unknown",
        emblemUrl = emblemUrl ?: "",
        plan = plan ?: "Unknown",
        currentSeason = currentSeason?.toSeasonEntity(),
        seasons = seasons?.map { it.toSeasonEntity() },
        numberOfAvailableSeasons = numberOfAvailableSeasons ?: 0,
        lastUpdated = lastUpdated ?: "Unknown"
    )
}

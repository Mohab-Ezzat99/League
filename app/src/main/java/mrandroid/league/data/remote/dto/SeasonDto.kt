package mrandroid.league.data.remote.dto

import com.google.gson.annotations.SerializedName
import mrandroid.league.data.local.entity.SeasonEntity

data class SeasonDto(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("startDate") var startDate: String? = null,
    @SerializedName("endDate") var endDate: String? = null,
    @SerializedName("currentMatchday") var currentMatchDay: Int? = null,
    @SerializedName("winner") var winner: WinnerDto? = null
) {
    fun toSeasonEntity() = SeasonEntity(
        id = id ?: 0,
        startDate = startDate ?: "Unknown",
        endDate = endDate ?: "Unknown",
        currentMatchDay = currentMatchDay ?: 0,
        winner = winner?.toWinnerEntity(),
    )
}
package mrandroid.league.data.remote.dto.response

import com.google.gson.annotations.SerializedName
import mrandroid.league.data.remote.dto.CompetitionDto

data class AllCompetitionsResponse(
    @SerializedName("count") var count: Int? = null,
    @SerializedName("competitions") var competitions: ArrayList<CompetitionDto>? = null
)
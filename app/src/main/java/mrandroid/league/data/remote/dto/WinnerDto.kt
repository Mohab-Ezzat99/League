package mrandroid.league.data.remote.dto

import com.google.gson.annotations.SerializedName
import mrandroid.league.data.local.entity.WinnerEntity

data class WinnerDto(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("shortName") var shortName: String? = null,
    @SerializedName("tla") var tla: String? = null,
    @SerializedName("crestUrl") var crestUrl: String? = null
) {
    fun toWinnerEntity() = WinnerEntity(
        id = id ?: 0,
        name = name ?: "Unknown",
        shortName = shortName ?: "Unknown",
        tla = tla ?: "Unknown",
        crestUrl = crestUrl ?: "",
    )
}

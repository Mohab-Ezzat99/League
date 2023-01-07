package mrandroid.league.data.remote.dto

import com.google.gson.annotations.SerializedName
import mrandroid.league.data.local.entity.AreaEntity

data class AreaDto(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("countryCode") var countryCode: String? = null,
    @SerializedName("ensignUrl") var ensignUrl: String? = null
) {
    fun toAreaEntity() = AreaEntity(
        id = id ?: 0,
        name = name ?: "Unknown",
        countryCode = countryCode ?: "Unknown",
        ensignUrl = ensignUrl ?: "Unknown",
    )
}
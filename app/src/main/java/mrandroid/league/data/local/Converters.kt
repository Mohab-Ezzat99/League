package mrandroid.league.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import mrandroid.league.data.local.entity.*
import mrandroid.league.util.parser.JsonParser

@ProvidedTypeConverter
class Converters(private val jsonParser: JsonParser) {

    // Objects

    @TypeConverter
    fun toAreaEntityJson(area: AreaEntity?): String? {
        return jsonParser.toJson(
            area,
            object : TypeToken<AreaEntity>() {}.type
        )
    }

    @TypeConverter
    fun fromAreaEntityJson(json: String): AreaEntity? {
        return jsonParser.fromJson<AreaEntity>(
            json,
            object : TypeToken<AreaEntity>() {}.type
        )
    }

    @TypeConverter
    fun toSeasonEntityJson(area: SeasonEntity?): String? {
        return jsonParser.toJson(
            area,
            object : TypeToken<SeasonEntity>() {}.type
        )
    }

    @TypeConverter
    fun fromSeasonEntityJson(json: String): SeasonEntity? {
        return jsonParser.fromJson<SeasonEntity>(
            json,
            object : TypeToken<SeasonEntity>() {}.type
        )
    }

    @TypeConverter
    fun toWinnerEntityJson(area: WinnerEntity?): String? {
        return jsonParser.toJson(
            area,
            object : TypeToken<WinnerEntity>() {}.type
        )
    }

    @TypeConverter
    fun fromWinnerEntityJson(json: String): WinnerEntity? {
        return jsonParser.fromJson<WinnerEntity>(
            json,
            object : TypeToken<WinnerEntity>() {}.type
        )
    }

    // Lists

    @TypeConverter
    fun toSeasonListJson(list: List<SeasonEntity>?): String? {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<SeasonEntity>>() {}.type
        )
    }

    @TypeConverter
    fun fromSeasonListJson(json: String?): List<SeasonEntity>? {
        return json?.let {
            jsonParser.fromJson<ArrayList<SeasonEntity>>(
                it,
                object : TypeToken<ArrayList<SeasonEntity>>() {}.type
            )
        }
    }

    @TypeConverter
    fun toActiveCompetitionsListJson(list: List<ActiveCompetitionsEntity>?): String? {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<ActiveCompetitionsEntity>>() {}.type
        )
    }

    @TypeConverter
    fun fromActiveCompetitionsListJson(json: String?): List<ActiveCompetitionsEntity>? {
        return json?.let {
            jsonParser.fromJson<ArrayList<ActiveCompetitionsEntity>>(
                it,
                object : TypeToken<ArrayList<ActiveCompetitionsEntity>>() {}.type
            )
        }
    }

    @TypeConverter
    fun toSquadListJson(list: List<SquadEntity>?): String? {
        return jsonParser.toJson(
            list,
            object : TypeToken<ArrayList<SquadEntity>>() {}.type
        )
    }

    @TypeConverter
    fun fromSquadListJson(json: String?): List<SquadEntity>? {
        return json?.let {
            jsonParser.fromJson<ArrayList<SquadEntity>>(
                it,
                object : TypeToken<ArrayList<SquadEntity>>() {}.type
            )
        }
    }

}
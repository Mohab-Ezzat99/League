package mrandroid.league.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mrandroid.league.data.local.entity.*

@Database(
    entities = [AreaEntity::class, CompetitionEntity::class, SeasonEntity::class,
        TeamEntity::class, ActiveCompetitionsEntity::class, SquadEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class LeagueDB : RoomDatabase() {
    abstract val dao: LeagueDao
}
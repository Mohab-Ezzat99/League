package mrandroid.league.data.local

import androidx.room.*
import mrandroid.league.data.local.entity.CompetitionEntity
import mrandroid.league.data.local.entity.SeasonEntity
import mrandroid.league.data.local.entity.TeamEntity

@Dao
interface LeagueDao {

    // Competitions

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompetitions(competitionsList: List<CompetitionEntity>)

    @Query("SELECT * FROM CompetitionEntity")
    suspend fun getAllCompetitions(): List<CompetitionEntity>

    @Query("DELETE FROM CompetitionEntity")
    suspend fun deleteAllCompetitions()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompetition(competitionEntity: CompetitionEntity)

    @Query("SELECT * FROM CompetitionEntity WHERE id=:competitionId")
    suspend fun getCompetitionById(competitionId: Long): CompetitionEntity

    @Delete
    suspend fun deleteCompetition(competitionEntity: CompetitionEntity)

    // Teams

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamInfo(teamEntity: TeamEntity)

    @Query("SELECT * FROM TeamEntity WHERE id=:teamId")
    suspend fun getTeamInfoById(teamId: Long): TeamEntity

    @Delete
    suspend fun deleteTeamsInfo(teamEntity: TeamEntity)

}
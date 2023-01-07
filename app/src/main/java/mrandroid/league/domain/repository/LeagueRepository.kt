package mrandroid.league.domain.repository

import kotlinx.coroutines.flow.Flow
import mrandroid.league.data.local.entity.CompetitionEntity
import mrandroid.league.data.local.entity.TeamEntity
import mrandroid.league.util.state.Resource

interface LeagueRepository {

    suspend fun getAllCompetitions(): Flow<Resource<List<CompetitionEntity>>>

    suspend fun getCompetitionDetails(competitionId: Long): Flow<Resource<CompetitionEntity>>

    suspend fun getTeamInfo(teamId: Long): Flow<Resource<TeamEntity>>

}
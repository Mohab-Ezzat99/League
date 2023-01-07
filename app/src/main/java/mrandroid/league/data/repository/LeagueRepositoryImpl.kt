package mrandroid.league.data.repository

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mrandroid.league.data.local.LeagueDao
import mrandroid.league.data.local.entity.CompetitionEntity
import mrandroid.league.data.local.entity.TeamEntity
import mrandroid.league.data.remote.LeagueApi
import mrandroid.league.domain.repository.LeagueRepository
import mrandroid.league.util.Constants.TAG
import mrandroid.league.util.state.ApiState
import mrandroid.league.util.state.Resource
import mrandroid.league.util.toResultFlow

class LeagueRepositoryImpl(
    private val LeagueApi: LeagueApi,
    private val LeagueDao: LeagueDao
) : LeagueRepository {

    override suspend fun getAllCompetitions(): Flow<Resource<List<CompetitionEntity>>> {
        return flow {
            emit(Resource.Loading())
            val cachedCompetitions = LeagueDao.getAllCompetitions()
            emit(Resource.Loading(data = cachedCompetitions))

            val result = toResultFlow { LeagueApi.getAllCompetitions() }
            result.collect { apiState ->
                when (apiState) {
                    is ApiState.Loading -> Unit
                    is ApiState.Error ->
                        emit(Resource.Error(apiState.message!!, data = cachedCompetitions))
                    is ApiState.Success -> {
                        apiState.data?.competitions?.let { list ->
                            LeagueDao.deleteAllCompetitions()
                            LeagueDao.insertCompetitions(list.map { it.toCompetitionEntity() })
                            val newestList = LeagueDao.getAllCompetitions()
                            emit(Resource.Success(newestList))
                        }
                    }
                }
            }
        }
    }

    override suspend fun getCompetitionDetails(competitionId: Long): Flow<Resource<CompetitionEntity>> {
        return flow {
            emit(Resource.Loading())
            val cachedCompetition = LeagueDao.getCompetitionById(competitionId)
            emit(Resource.Loading(data = cachedCompetition))

            val result = toResultFlow { LeagueApi.getCompetitionDetails(competitionId) }
            result.collect { apiState ->
                when (apiState) {
                    is ApiState.Loading -> Unit
                    is ApiState.Error ->
                        emit(Resource.Error(apiState.message!!, data = cachedCompetition))
                    is ApiState.Success -> {
                        apiState.data?.let { competition ->
                            LeagueDao.deleteCompetition(competition.toCompetitionEntity())
                            LeagueDao.insertCompetition(competition.toCompetitionEntity())
                            val newestCompetition = LeagueDao.getCompetitionById(competitionId)
                            emit(Resource.Success(newestCompetition))
                        }
                    }
                }
            }
        }
    }

    override suspend fun getTeamInfo(teamId: Long): Flow<Resource<TeamEntity>> {
        return flow {
            emit(Resource.Loading())
            val cachedTeamInfo = LeagueDao.getTeamInfoById(teamId)
            emit(Resource.Loading(data = cachedTeamInfo))

            val result = toResultFlow { LeagueApi.getTeamInfo(teamId) }
            result.collect { apiState ->
                when (apiState) {
                    is ApiState.Loading -> Unit
                    is ApiState.Error ->
                        emit(Resource.Error(apiState.message!!, data = cachedTeamInfo))
                    is ApiState.Success -> {
                        apiState.data?.let { teamInfo ->
                            LeagueDao.deleteTeamsInfo(teamInfo.toTeamEntity())
                            LeagueDao.insertTeamInfo(teamInfo.toTeamEntity())
                            val newestTeamInfo = LeagueDao.getTeamInfoById(teamId)
                            emit(Resource.Success(newestTeamInfo))
                        }
                    }
                }
            }
        }
    }

}
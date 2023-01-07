package mrandroid.league.data.remote

import mrandroid.league.data.remote.dto.CompetitionDto
import mrandroid.league.data.remote.dto.response.AllCompetitionsResponse
import mrandroid.league.data.remote.dto.response.TeamResponse
import retrofit2.Response
import retrofit2.http.*

interface LeagueApi {

    companion object {
        const val BASE_URL = "http://api.football-data.org/v2/"
    }

    @GET("competitions")
    suspend fun getAllCompetitions(): Response<AllCompetitionsResponse>

    @GET("competitions/{competitionId}")
    suspend fun getCompetitionDetails(@Path("competitionId") competitionId: Long): Response<CompetitionDto>

    @GET("teams/{teamId}")
    suspend fun getTeamInfo(@Path("teamId") teamId: Long): Response<TeamResponse>

}
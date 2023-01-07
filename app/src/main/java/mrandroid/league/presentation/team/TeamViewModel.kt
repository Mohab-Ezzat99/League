package mrandroid.league.presentation.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mrandroid.league.data.local.entity.TeamEntity
import mrandroid.league.domain.repository.LeagueRepository
import mrandroid.league.util.state.Resource
import mrandroid.league.util.state.UiState
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val leagueRepository: LeagueRepository
) : ViewModel() {

    private val _teamState = MutableStateFlow<UiState<TeamEntity>>(UiState.Empty())
    val teamState: StateFlow<UiState<TeamEntity>> = _teamState

    private var teamJob: Job? = null

    fun cancelRequest() {
        teamJob?.cancel()
    }

    fun getTeamInfo(teamId: Long) {
        teamJob?.cancel()
        teamJob = viewModelScope.launch {
            withContext(coroutineContext) {
                leagueRepository.getTeamInfo(teamId).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _teamState.value = UiState.Success(result.data)
                        }
                        is Resource.Error -> {
                            _teamState.value = UiState.Error(result.message!!)
                        }
                        is Resource.Loading -> {
                            _teamState.value = UiState.Loading(result.data)
                        }
                    }
                }
            }
        }
    }

}
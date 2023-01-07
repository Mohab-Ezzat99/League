package mrandroid.league.presentation.competition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mrandroid.league.data.local.entity.CompetitionEntity
import mrandroid.league.domain.repository.LeagueRepository
import mrandroid.league.util.state.Resource
import mrandroid.league.util.state.UiState
import javax.inject.Inject

@HiltViewModel
class CompetitionViewModel @Inject constructor(
    private val leagueRepository: LeagueRepository
) : ViewModel() {

    private val _competitionState = MutableStateFlow<UiState<CompetitionEntity>>(UiState.Empty())
    val competitionState: StateFlow<UiState<CompetitionEntity>> = _competitionState

    private var competitionJob: Job? = null

    fun cancelRequest() {
        competitionJob?.cancel()
    }

    fun getCompetition(competitionId: Long) {
        competitionJob?.cancel()
        competitionJob = viewModelScope.launch {
            withContext(coroutineContext) {
                leagueRepository.getCompetitionDetails(competitionId).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _competitionState.value = UiState.Success(result.data)
                        }
                        is Resource.Error -> {
                            _competitionState.value = UiState.Error(result.message!!)
                        }
                        is Resource.Loading -> {
                            _competitionState.value = UiState.Loading(result.data)
                        }
                    }
                }
            }
        }
    }

}
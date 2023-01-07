package mrandroid.league.presentation.home

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
class HomeViewModel @Inject constructor(
    private val leagueRepository: LeagueRepository
) : ViewModel() {

    private val _competitionsState = MutableStateFlow<UiState<List<CompetitionEntity>>>(UiState.Empty())
    val competitionsState: StateFlow<UiState<List<CompetitionEntity>>> = _competitionsState

    private var competitionsJob: Job? = null

    fun cancelRequest() {
        competitionsJob?.cancel()
    }

    fun getAllCompetitions() {
        competitionsJob?.cancel()
        competitionsJob = viewModelScope.launch {
            withContext(coroutineContext) {
                leagueRepository.getAllCompetitions().collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _competitionsState.value = UiState.Success(result.data)
                        }
                        is Resource.Error -> {
                            _competitionsState.value = UiState.Error(result.message!!)
                        }
                        is Resource.Loading -> {
                            _competitionsState.value = UiState.Loading(result.data)
                        }
                    }
                }
            }
        }
    }

}
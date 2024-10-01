package umc.everyones.everyoneslckmanage.presentation.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateTeamInfoTournamentResultViewmodel @Inject constructor() : ViewModel() {

    private val _tournamentResultList = MutableStateFlow<List<TournamentResult>>(emptyList())
    val tournamentResultList: StateFlow<List<TournamentResult>> get() = _tournamentResultList

    var teamName: String? = null

    init {
        _tournamentResultList.value = listOf(
            TournamentResult(1, 2018, "Lck Summer","T1"),
            TournamentResult(2, 2019, "Lck Spring","T1")
        )
    }

    fun getTournamentResultForTeam(teamName: String): List<TournamentResult> {
        return _tournamentResultList.value.filter { it.teamName == teamName }
    }

    fun addTournamentResult(newHistory: TournamentResult) {
        val currentList = _tournamentResultList.value.toMutableList()
        currentList.add(newHistory)
        _tournamentResultList.value = currentList
    }

    fun updateTournamentResult(updatedHistory: TournamentResult) {
        val currentList = _tournamentResultList.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedHistory.id }
        if (index != -1) {
            currentList[index] = updatedHistory
            _tournamentResultList.value = currentList
        }
    }

    fun deleteTournamentResult(historyToDelete: TournamentResult) {
        val currentList = _tournamentResultList.value.toMutableList()
        currentList.remove(historyToDelete)
        _tournamentResultList.value = currentList
    }
}

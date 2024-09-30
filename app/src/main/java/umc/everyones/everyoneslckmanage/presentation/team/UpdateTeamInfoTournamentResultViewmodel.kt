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
            TournamentResult(1, 2018, "PerfecrT  Pyosik  Bdd  Deft  BeryL"),
            TournamentResult(2, 2019, "PerfecrT  Pyosik  Bdd  Deft  BeryL")
        )
    }

    fun addWinningHistory(newHistory: TournamentResult) {
        val currentList = _tournamentResultList.value.toMutableList()
        currentList.add(newHistory)
        _tournamentResultList.value = currentList
    }

    fun updateWinningHistory(updatedHistory: TournamentResult) {
        val currentList = _tournamentResultList.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedHistory.id }
        if (index != -1) {
            currentList[index] = updatedHistory
            _tournamentResultList.value = currentList
        }
    }

    fun deleteWinningHistory(historyToDelete: TournamentResult) {
        val currentList = _tournamentResultList.value.toMutableList()
        currentList.remove(historyToDelete)
        _tournamentResultList.value = currentList
    }
}

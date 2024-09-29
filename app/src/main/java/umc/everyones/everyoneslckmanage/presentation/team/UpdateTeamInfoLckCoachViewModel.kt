package umc.everyones.everyoneslckmanage.presentation.team

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UpdateTeamInfoLckCoachViewModel @Inject constructor() : ViewModel() {

    var teamName: String? = null

    private val _lckCoachState = MutableStateFlow<List<LckCoach>>(emptyList())
    val lckCoachState: StateFlow<List<LckCoach>> = _lckCoachState

    init {
        _lckCoachState.value = listOf(
            LckCoach("1", "Player 1",  "https://picsum.photos/400/400"),
            LckCoach("2", "Player 2",  "https://picsum.photos/400/400"),
            LckCoach("3", "Player 3",  "https://picsum.photos/400/400"),
            LckCoach("4", "Player 4",  "https://picsum.photos/400/400"),
            LckCoach("5", "Player 5", "https://picsum.photos/400/400")
        )
    }

    fun addPlayer(player: LckCoach) {
        val currentList = _lckCoachState.value.toMutableList()
        currentList.add(player)
        _lckCoachState.value = currentList
    }

    fun updatePlayer(updatedPlayer: LckCoach) {
        val currentList = _lckCoachState.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedPlayer.id }
        if (index != -1) {
            currentList[index] = updatedPlayer
            _lckCoachState.value = currentList
        }
    }

    fun deletePlayer(playerId: String) {
        val currentList = _lckCoachState.value.toMutableList()
        val updatedList = currentList.filter { it.id != playerId }
        _lckCoachState.value = updatedList
    }
}


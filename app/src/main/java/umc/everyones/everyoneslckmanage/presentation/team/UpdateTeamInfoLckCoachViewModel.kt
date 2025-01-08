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

    private val _allCoaches = MutableStateFlow<List<LckCoach>>(emptyList())
    val allCoaches: StateFlow<List<LckCoach>> = _allCoaches

    init {
        initializeSampleData()
    }

    private fun initializeSampleData() {
        val sampleData = listOf(
            LckCoach(1, "Player 1", "https://picsum.photos/400/400", "T1"),
            LckCoach(2, "Player 2",  "https://picsum.photos/400/400", "T1"),
            LckCoach(3, "Player 3",  "https://picsum.photos/400/400", "T1"),
            LckCoach(4, "Player 4",  "https://picsum.photos/400/400", "DK"),
            LckCoach(5, "Player 5",  "https://picsum.photos/400/400", "DK"),
            LckCoach(6, "Player 6",  "https://picsum.photos/400/400", "Gen")
        )

        _allCoaches.value = sampleData
    }
    fun getCoachesForTeam(teamName: String): List<LckCoach> {
        return _allCoaches.value.filter { it.teamName == teamName }
    }

    fun addPlayerToTeam(player: LckCoach) {
        val currentList = _allCoaches.value.toMutableList()
        currentList.add(player)
        _allCoaches.value = currentList
    }

    fun updatePlayer(updatedPlayer: LckCoach) {
        val currentList = _allCoaches.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedPlayer.id }
        if (index != -1) {
            currentList[index] = updatedPlayer
            _allCoaches.value = currentList
        }
    }

    fun deletePlayer(playerId: Int) {
        val currentList = _allCoaches.value.toMutableList()
        val updatedList = currentList.filter { it.id != playerId }
        _allCoaches.value = updatedList
    }
}


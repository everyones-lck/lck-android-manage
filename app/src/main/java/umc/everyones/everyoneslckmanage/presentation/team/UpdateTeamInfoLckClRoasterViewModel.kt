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
class UpdateTeamInfoLckClRoasterViewModel @Inject constructor() : ViewModel() {

    var teamName: String? = null

    private val _allClRoasters = MutableStateFlow<List<LckClRoaster>>(emptyList())
    val allClRoasters: StateFlow<List<LckClRoaster>> = _allClRoasters

    init {
        initializeSampleData()
    }

    private fun initializeSampleData() {
        val sampleData = listOf(
            LckClRoaster(1, "Player 1", "미드", "https://picsum.photos/400/400", "T1"),
            LckClRoaster(2, "Player 2", "서폿", "https://picsum.photos/400/400", "T1"),
            LckClRoaster(3, "Player 3", "탑", "https://picsum.photos/400/400", "HLE"),
            LckClRoaster(4, "Player 4", "원딜", "https://picsum.photos/400/400", "DK"),
            LckClRoaster(5, "Player 5", "정글", "https://picsum.photos/400/400", "DK"),
            LckClRoaster(6, "Player 6", "탑", "https://picsum.photos/400/400", "Gen")
        )

        _allClRoasters.value = sampleData
    }
    fun getRoasterForTeam(teamName: String): List<LckClRoaster> {
        return _allClRoasters.value.filter { it.teamName == teamName }
    }

    fun addPlayerToTeam(player: LckClRoaster) {
        val currentList = _allClRoasters.value.toMutableList()
        currentList.add(player)
        _allClRoasters.value = currentList
    }

    fun updatePlayer(updatedPlayer: LckClRoaster) {
        val currentList = _allClRoasters.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedPlayer.id }
        if (index != -1) {
            currentList[index] = updatedPlayer
            _allClRoasters.value = currentList
        }
    }

    fun deletePlayer(playerId: Int) {
        val currentList = _allClRoasters.value.toMutableList()
        val updatedList = currentList.filter { it.id != playerId }
        _allClRoasters.value = updatedList
    }
}


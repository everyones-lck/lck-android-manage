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
class UpdateTeamInfoLckRoasterViewModel @Inject constructor() : ViewModel() {

    var teamName: String? = null

    private val _allRoasters = MutableStateFlow<List<LckRoaster>>(emptyList())
    val allRoasters: StateFlow<List<LckRoaster>> = _allRoasters

    init {
        initializeSampleData()
    }

    private fun initializeSampleData() {
        val sampleData = listOf(
            LckRoaster(1, "Player 1", "미드", "https://picsum.photos/400/400", "T1"),
            LckRoaster(2, "Player 2", "서폿", "https://picsum.photos/400/400", "T1"),
            LckRoaster(3, "Player 3", "탑", "https://picsum.photos/400/400", "T1"),
            LckRoaster(4, "Player 4", "원딜", "https://picsum.photos/400/400", "DK"),
            LckRoaster(5, "Player 5", "정글", "https://picsum.photos/400/400", "DK"),
            LckRoaster(6, "Player 6", "탑", "https://picsum.photos/400/400", "Gen")
        )

        _allRoasters.value = sampleData
    }
    fun getRoasterForTeam(teamName: String): List<LckRoaster> {
        return _allRoasters.value.filter { it.teamName == teamName }
    }

    fun addPlayerToTeam(player: LckRoaster) {
        val currentList = _allRoasters.value.toMutableList()
        currentList.add(player)
        _allRoasters.value = currentList
    }

    fun updatePlayer(updatedPlayer: LckRoaster) {
        val currentList = _allRoasters.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedPlayer.id }
        if (index != -1) {
            currentList[index] = updatedPlayer
            _allRoasters.value = currentList
        }
    }

    fun deletePlayer(playerId: Int) {
        val currentList = _allRoasters.value.toMutableList()
        val updatedList = currentList.filter { it.id != playerId }
        _allRoasters.value = updatedList
    }
}


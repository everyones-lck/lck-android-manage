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

    private val _lckClRoasterState = MutableStateFlow<List<LckClRoaster>>(emptyList())
    val lckClRoasterState: StateFlow<List<LckClRoaster>> = _lckClRoasterState

    init {
        _lckClRoasterState.value = listOf(
            LckClRoaster("1", "Player 1", "미드", "https://picsum.photos/400/400"),
            LckClRoaster("2", "Player 2", "서폿", "https://picsum.photos/400/400"),
            LckClRoaster("3", "Player 3", "탑", "https://picsum.photos/400/400"),
            LckClRoaster("4", "Player 4", "원딜", "https://picsum.photos/400/400"),
            LckClRoaster("5", "Player 5", "정글", "https://picsum.photos/400/400")
        )
    }

    fun addPlayer(player: LckClRoaster) {
        val currentList = _lckClRoasterState.value.toMutableList()
        currentList.add(player)
        _lckClRoasterState.value = currentList
    }

    fun updatePlayer(updatedPlayer: LckClRoaster) {
        val currentList = _lckClRoasterState.value.toMutableList()

        val index = currentList.indexOfFirst { it.id == updatedPlayer.id }
        if (index != -1) {
            currentList[index] = updatedPlayer
            _lckClRoasterState.value = currentList
        }
    }
    fun deletePlayer(playerId: String) {
        val currentList = _lckClRoasterState.value.toMutableList()
        val updatedList = currentList.filter { it.id != playerId }
        _lckClRoasterState.value = updatedList
    }
}


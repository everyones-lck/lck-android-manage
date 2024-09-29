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

    private val _lckRoasterState = MutableStateFlow<List<LckRoaster>>(emptyList())
    val lckRoasterState: StateFlow<List<LckRoaster>> = _lckRoasterState

    init {
        _lckRoasterState.value = listOf(
            LckRoaster("1", "Player 1", "미드", "https://picsum.photos/400/400"),
            LckRoaster("2", "Player 2", "서폿", "https://picsum.photos/400/400"),
            LckRoaster("3", "Player 3", "탑", "https://picsum.photos/400/400"),
            LckRoaster("4", "Player 4", "원딜", "https://picsum.photos/400/400"),
            LckRoaster("5", "Player 5", "정글", "https://picsum.photos/400/400")
        )
    }

    fun addPlayer(player: LckRoaster) {
        val currentList = _lckRoasterState.value.toMutableList()
        currentList.add(player)
        _lckRoasterState.value = currentList
    }

    fun updatePlayer(updatedPlayer: LckRoaster) {
        val currentList = _lckRoasterState.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedPlayer.id }
        if (index != -1) {
            currentList[index] = updatedPlayer
            _lckRoasterState.value = currentList
        }
    }

    fun deletePlayer(playerId: String) {
        val currentList = _lckRoasterState.value.toMutableList()
        val updatedList = currentList.filter { it.id != playerId }
        _lckRoasterState.value = updatedList
    }
}


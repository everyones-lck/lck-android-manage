package umc.everyones.everyoneslckmanage.presentation.team

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateTeamInfoWinningHistoryViewModel @Inject constructor() : ViewModel() {

    private val _winningHistoryList = MutableStateFlow<List<WinningHistory>>(emptyList())
    val winningHistoryList: StateFlow<List<WinningHistory>> get() = _winningHistoryList

    var teamName: String? = null

    init {
        _winningHistoryList.value = listOf(
            WinningHistory(100, 2018, "Lck Summer","DK"),
            WinningHistory(200, 2019, "Lck Summer","T1")
        )
    }

    fun getWinningHistoryForTeam(teamName: String): List<WinningHistory> {
        return _winningHistoryList.value.filter { it.teamName == teamName }
    }

    fun addWinningHistory(newHistory: WinningHistory) {
        val currentList = _winningHistoryList.value.toMutableList()
        currentList.add(newHistory)
        _winningHistoryList.value = currentList
    }

    fun updateWinningHistory(updatedHistory: WinningHistory) {
        val currentList = _winningHistoryList.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedHistory.id }
        if (index != -1) {
            currentList[index] = updatedHistory
            _winningHistoryList.value = currentList
        }
    }

    fun deleteWinningHistory(historyToDelete: WinningHistory) {
        val currentList = _winningHistoryList.value.toMutableList()
        currentList.remove(historyToDelete)
        _winningHistoryList.value = currentList
    }
}


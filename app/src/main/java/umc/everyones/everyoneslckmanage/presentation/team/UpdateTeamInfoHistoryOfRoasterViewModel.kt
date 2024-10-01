package umc.everyones.everyoneslckmanage.presentation.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateTeamInfoHistoryOfRoasterViewModel @Inject constructor() : ViewModel() {

    private val _historyOfRoasterList = MutableStateFlow<List<HistoryOfRoaster>>(emptyList())
    val historyOfRoasterList: StateFlow<List<HistoryOfRoaster>> get() = _historyOfRoasterList

    var teamName: String? = null

    init {
        _historyOfRoasterList.value = listOf(
            HistoryOfRoaster(1, 2018, "PerfecrT  Pyosik  Bdd  Deft  BeryL","DK"),
            HistoryOfRoaster(2, 2019, "PerfecrT  Pyosik  Bdd  Deft  BeryL","HLE")
        )
    }
    fun getHistoryOfRoasterForTeam(teamName: String): List<HistoryOfRoaster> {
        return _historyOfRoasterList.value.filter { it.teamName == teamName }
    }

    fun addHistoryOfRoaster(newHistory: HistoryOfRoaster) {
        val currentList = _historyOfRoasterList.value.toMutableList()
        currentList.add(newHistory)
        _historyOfRoasterList.value = currentList
    }

    fun updateHistoryOfRoaster(updatedHistory: HistoryOfRoaster) {
        val currentList = _historyOfRoasterList.value.toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedHistory.id }
        if (index != -1) {
            currentList[index] = updatedHistory
            _historyOfRoasterList.value = currentList
        }
    }

    fun deleteHistoryOfRoaster(historyToDelete: HistoryOfRoaster) {
        val currentList = _historyOfRoasterList.value.toMutableList()
        currentList.remove(historyToDelete)
        _historyOfRoasterList.value = currentList
    }
}

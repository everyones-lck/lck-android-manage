package umc.everyones.everyoneslckmanage.presentation.team

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UpdateTeamInfoLckClRoasterAddViewModel @Inject constructor() : ViewModel() {

    private val _playerWinningCareers = MutableStateFlow<Map<Int, List<WinningCareer>>>(emptyMap())
    val playerWinningCareers: StateFlow<Map<Int, List<WinningCareer>>> get() = _playerWinningCareers


    private val _playerHistoryOfTeam = MutableStateFlow<Map<Int, List<HistoryOfTeam>>>(emptyMap())
    val playerHistoryOfTeam: StateFlow<Map<Int, List<HistoryOfTeam>>> get() = _playerHistoryOfTeam


    fun getWinningCareerForPlayer(playerId: Int): List<WinningCareer> {
        return _playerWinningCareers.value[playerId] ?: emptyList()
    }

    private fun setWinningCareerForPlayer(playerId: Int, careerList: List<WinningCareer>) {
        val currentMap = _playerWinningCareers.value.toMutableMap()
        currentMap[playerId] = careerList
        _playerWinningCareers.value = currentMap
    }

    fun addWinningCareerToPlayer(playerId: Int, newCareer: WinningCareer) {
        val currentList = getWinningCareerForPlayer(playerId).toMutableList()
        currentList.add(newCareer)
        setWinningCareerForPlayer(playerId, currentList)
    }

    fun updateWinningCareerForPlayer(playerId: Int, updatedCareer: WinningCareer) {
        val currentList = getWinningCareerForPlayer(playerId).toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedCareer.id }
        if (index != -1) {
            currentList[index] = updatedCareer
            setWinningCareerForPlayer(playerId, currentList)
        }
    }

    fun deleteWinningCareerFromPlayer(playerId: Int, deleteCareer: WinningCareer) {
        val currentList = getWinningCareerForPlayer(playerId).toMutableList()
        val updatedList = currentList.filter { it.id != deleteCareer.id }
        setWinningCareerForPlayer(playerId, updatedList)
    }

    fun getHistoryTeamsForPlayer(playerId: Int): List<HistoryOfTeam> {
        return _playerHistoryOfTeam.value[playerId] ?: emptyList()
    }

    fun addHistoryTeamToPlayer(playerId: Int, newTeam: HistoryOfTeam) {
        val currentList = getHistoryTeamsForPlayer(playerId).toMutableList()
        currentList.add(newTeam)
        _playerHistoryOfTeam.value = _playerHistoryOfTeam.value.toMutableMap().apply {
            put(playerId, currentList)
        }
    }

    fun updateHistoryTeamForPlayer(playerId: Int, updatedTeam: HistoryOfTeam) {
        val currentList = getHistoryTeamsForPlayer(playerId).toMutableList()
        val index = currentList.indexOfFirst { it.id == updatedTeam.id }
        if (index != -1) {
            currentList[index] = updatedTeam
            _playerHistoryOfTeam.value = _playerHistoryOfTeam.value.toMutableMap().apply {
                put(playerId, currentList)
            }
        }
    }

    fun deleteHistoryTeamFromPlayer(playerId: Int, teamId: Int) {
        val currentList = getHistoryTeamsForPlayer(playerId).toMutableList()
        _playerHistoryOfTeam.value = _playerHistoryOfTeam.value.toMutableMap().apply {
            put(playerId, currentList.filter { it.id != teamId })
        }
    }
}

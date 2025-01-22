package umc.everyones.everyoneslckmanage.presentation.team

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import umc.everyones.everyoneslckmanage.domain.model.response.team.PlayerListModel
import umc.everyones.everyoneslckmanage.domain.repository.UpdateTeamInfoRepository
import umc.everyones.everyoneslckmanage.util.extension.Constants
import javax.inject.Inject

@HiltViewModel
class UpdateTeamInfoLckRoasterViewModel @Inject constructor(
    private val repository: UpdateTeamInfoRepository
) : ViewModel() {

    private val _players = MutableStateFlow<List<PlayerListModel.PlayerModel>>(emptyList())
    val players: StateFlow<List<PlayerListModel.PlayerModel>> get() = _players

    fun fetchRoasterData(teamId: Int, role: String) {
        viewModelScope.launch {
            val result = repository.getTeamPlayers(teamId, Constants.CURRENT_SEASON, role)

            result.onSuccess { response ->
                _players.value = response.players
            }.onFailure { error ->
                Log.e("UpdateTeamInfoViewModel", "Error fetching data: ${error.message}")
            }
        }
    }
}




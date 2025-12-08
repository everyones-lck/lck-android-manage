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
import umc.everyones.everyoneslckmanage.domain.model.response.team.PlayerListModel
import umc.everyones.everyoneslckmanage.domain.repository.UpdateTeamInfoRepository
import umc.everyones.everyoneslckmanage.util.extension.Constants
import javax.inject.Inject

@HiltViewModel
class UpdateTeamInfoLckCoachViewModel @Inject constructor(
    private val repository: UpdateTeamInfoRepository
) : ViewModel() {

    private val _coaches = MutableStateFlow<List<PlayerListModel.PlayerModel>>(emptyList())
    val coaches: StateFlow<List<PlayerListModel.PlayerModel>> get() = _coaches

    fun fetchCoachData(teamId: Int, role: String = "COACH") {
        viewModelScope.launch {
            val result = repository.getTeamPlayers(
                teamId = teamId,
                season = Constants.CURRENT_SEASON,
                role = role
            )

            result.onSuccess { response ->
                _coaches.value = response.players
            }.onFailure { error ->
                Log.e("CoachViewModel", "Error fetching coach data: ${error.message}")
            }
        }
    }
}

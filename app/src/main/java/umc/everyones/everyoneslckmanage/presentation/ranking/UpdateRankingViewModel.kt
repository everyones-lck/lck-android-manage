package umc.everyones.everyoneslckmanage.presentation.ranking

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class UpdateRankingViewModel @Inject constructor() : ViewModel(){

    private val _teams = MutableStateFlow<List<Ranking>>(emptyList())
    val teams: StateFlow<List<Ranking>> = _teams

    init {
        _teams.value = listOf(
            Ranking("Team A", 10, 2, 30),
            Ranking("Team B", 8, 4, 25),
            Ranking("Team C", 8, 4, 22),
            Ranking("Team D", 6, 6, 20),
            Ranking("Team E", 10, 1, 40),
            Ranking("Team F", 9, 4, 67),
            Ranking("Team G", 8, 4, 22),
            Ranking("Team H", 5, 6, 20),
            Ranking("Team I", 1, 2, 30),
            Ranking("Team J", 8, 8, 25)
        )
    }

    fun updateTeamData(teamName: String, win: Int, lose: Int, points: Int) {
        _teams.value = _teams.value.map { team ->
            if (team.name == teamName) {
                team.copy(win = win, lose = lose, points = points)
            } else {
                team
            }
        }
    }
}
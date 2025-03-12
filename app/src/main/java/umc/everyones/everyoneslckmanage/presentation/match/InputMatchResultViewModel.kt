package umc.everyones.everyoneslckmanage.presentation.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import umc.everyones.everyoneslckmanage.domain.model.response.match.MatchInfoModel
import umc.everyones.everyoneslckmanage.domain.repository.InputMatchRepository
import javax.inject.Inject

@HiltViewModel
class InputMatchResultViewModel @Inject constructor(
    private val repository: InputMatchRepository
): ViewModel(){
    private val _selectedMatchNumber = MutableStateFlow<Int?>(null)
    val selectedMatchNumber: StateFlow<Int?> get() = _selectedMatchNumber

    private val _matchInfo = MutableStateFlow<MatchInfoModel?>(null)
    val matchInfo: StateFlow<MatchInfoModel?> get() = _matchInfo

    fun updateSelectedMatchNumber(matchNumber: Int) {
        _selectedMatchNumber.value = matchNumber
    }

    fun resetSelectedMatchNumber() {
        _selectedMatchNumber.value = null
    }

    fun fetchMatchInfo() {
        viewModelScope.launch {
            repository.fetchMatchInfo()
                .onSuccess { response ->
                    _matchInfo.value = response
                    Timber.d("fetchMatchInfo 성공: $response")
                }
                .onFailure { exception ->
                    Timber.e("fetchMatchInfo 실패: ${exception.message}")
                }
        }
    }
}
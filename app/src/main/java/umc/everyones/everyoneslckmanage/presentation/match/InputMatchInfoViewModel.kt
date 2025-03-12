package umc.everyones.everyoneslckmanage.presentation.match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import umc.everyones.everyoneslckmanage.domain.model.match.SelectedMatch
import umc.everyones.everyoneslckmanage.domain.model.response.match.LckMatchDetailsModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.MatchInfoModel
import umc.everyones.everyoneslckmanage.domain.repository.InputMatchRepository
import javax.inject.Inject

@HiltViewModel
class InputMatchInfoViewModel @Inject constructor(
    private val repository: InputMatchRepository
): ViewModel(){
    private val _matchDetails = MutableStateFlow<LckMatchDetailsModel?>(null)
    val matchDetails: StateFlow<LckMatchDetailsModel?> get() = _matchDetails

    private val _selectedDate = MutableStateFlow<String?>(null) // 선택한 날짜를 저장
    val selectedDate: StateFlow<String?> get() = _selectedDate

    private val _selectedMatch = MutableStateFlow<SelectedMatch?>(null)
    val selectedMatch: StateFlow<SelectedMatch?> get() = _selectedMatch

    fun fetchLckMatchDetails(searchDate: String){
        viewModelScope.launch{
            val result = repository.fetchLckMatchDetails(searchDate)

            result.onSuccess { response ->
                _matchDetails.value = response
                Timber.d("MatchDetail %s", response.toString())
            }.onFailure { exception ->
                Timber.e(exception, "fetchLckMatchDetails API 호출 실패")
            }
        }
    }

    fun updateSelectedDate(date: String) {
        _selectedDate.value = date
    }

    fun updateSelectedMatch(selectedMatch: SelectedMatch) {
        _selectedMatch.value = selectedMatch
    }

    fun resetSelectedMatch() {
        _selectedMatch.value = null
    }

}
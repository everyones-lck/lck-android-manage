package umc.everyones.everyoneslckmanage.presentation.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import umc.everyones.everyoneslckmanage.domain.model.response.match.MatchInfoModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.SetResultInfoResponseModel
import umc.everyones.everyoneslckmanage.domain.repository.InputMatchRepository
import javax.inject.Inject

@HiltViewModel
class InputMatchResultViewModel @Inject constructor(
    private val repository: InputMatchRepository
): ViewModel(){
    private val _selectedMatch = MutableStateFlow<MatchInfoModel.MatchResponsesModel?>(null)
    val selectedMatch: StateFlow<MatchInfoModel.MatchResponsesModel?> get() = _selectedMatch

    private val _matchInfoList = MutableStateFlow<List<MatchInfoModel.MatchResponsesModel>>(emptyList())
    val matchInfoList: StateFlow<List<MatchInfoModel.MatchResponsesModel>> get() = _matchInfoList

    private val _setResultInfoMap = MutableStateFlow<Map<Long, SetResultInfoResponseModel>>(emptyMap())
    val setResultInfoMap: StateFlow<Map<Long, SetResultInfoResponseModel>> get() = _setResultInfoMap

    fun updateSelectedMatch(match: MatchInfoModel.MatchResponsesModel) {
        _selectedMatch.value = match
    }

    fun fetchMatchInfo() {
        viewModelScope.launch {
            repository.fetchMatchInfo()
                .onSuccess { response ->
                    _matchInfoList.value = response.matchResponses
                    Timber.d("fetchMatchInfo 성공: $response")
                }
                .onFailure { exception ->
                    Timber.e("fetchMatchInfo 실패: ${exception.message}")
                }
        }
    }

    fun fetchSetResultInfo(matchId: Long) {
        viewModelScope.launch {
            repository.fetchSetResultInfo(matchId)
                .onSuccess { response ->
                    _setResultInfoMap.value = _setResultInfoMap.value.toMutableMap().apply {
                        put(matchId, response) // matchId 별로 세트 결과 저장
                    }
                    Timber.d("fetchSetResultInfo 성공: matchId=$matchId, result=$response")
                }
                .onFailure { exception ->
                    Timber.e("fetchSetResultInfo 실패: ${exception.stackTraceToString()}")
                }
        }
    }
}
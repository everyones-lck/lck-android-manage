package umc.everyones.everyoneslckmanage.presentation.match

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import umc.everyones.everyoneslckmanage.domain.model.request.match.MatchResultModel
import umc.everyones.everyoneslckmanage.domain.model.request.match.SetResultModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.CommonResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.SetResultInfoResponseModel
import umc.everyones.everyoneslckmanage.domain.repository.InputMatchRepository
import javax.inject.Inject

@HiltViewModel
class InputSetResultViewModel @Inject constructor(
    private val repository: InputMatchRepository
): ViewModel() {
    private val _submitMatch = MutableStateFlow<Result<CommonResponseModel>?>(null)
    val submitMatch: StateFlow<Result<CommonResponseModel>?> get() = _submitMatch

    private val _submitSetResult = MutableStateFlow<Result<CommonResponseModel>?>(null)
    val submitSetResult: StateFlow<Result<CommonResponseModel>?> get() = _submitSetResult

    private val _setResultInfo = MutableStateFlow<SetResultInfoResponseModel?>(null)
    val setResultInfo: StateFlow<SetResultInfoResponseModel?> get() = _setResultInfo

    fun fetchMatchResult(matchId: Long, winnerTeamId: Int) {
        viewModelScope.launch {
            Timber.d("submitMatchResult 요청: matchId=$matchId, winnerTeamId=$winnerTeamId")
            repository.fetchMatchResults(MatchResultModel(matchId, winnerTeamId))  // Repository에 이 함수가 구현되어 있어야 함.
                .onSuccess { response ->
                    Timber.d("submitMatchResult 성공: $response")
                    _submitMatch.value = Result.success(response)
                }
                .onFailure { exception ->
                    Timber.e("submitMatchResult 실패: ${exception.stackTraceToString()}")
                    _submitMatch.value = Result.failure(exception)
                }
        }
    }

    fun fetchSetResultInfo(matchId: Long) {
        viewModelScope.launch {
            repository.fetchSetResultInfo(matchId)
                .onSuccess { response ->
                    _setResultInfo.value = response
                    Timber.d("fetchSetResultInfo 성공: $response")
                }
                .onFailure { exception ->
                    Timber.e("fetchSetResultInfo 실패: ${exception.stackTraceToString()}")
                    _setResultInfo.value = SetResultInfoResponseModel(emptyList())
                }
        }
    }

    fun fetchSetResults(request: SetResultModel) {
        viewModelScope.launch {
            Timber.d("fetchSetResults 요청: $request")
            repository.fetchSetResults(request)
                .onSuccess { response ->
                    Timber.d("fetchSetResults 성공: $response")
                    _submitSetResult.value = Result.success(response)
                }
                .onFailure { exception ->
                    Timber.e("fetchSetResults 실패: ${exception.stackTraceToString()}")
                    _submitSetResult.value = Result.failure(exception)
                }
        }
    }
}
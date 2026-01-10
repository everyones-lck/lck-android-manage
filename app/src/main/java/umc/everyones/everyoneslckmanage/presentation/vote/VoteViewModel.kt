package umc.everyones.everyoneslckmanage.presentation.vote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import umc.everyones.everyoneslckmanage.domain.model.request.match.CloseMatchModel
import umc.everyones.everyoneslckmanage.domain.model.request.match.CloseSetModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.CommonResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.MatchInfoModel
import umc.everyones.everyoneslckmanage.domain.repository.InputMatchRepository
import javax.inject.Inject

@HiltViewModel
class VoteViewModel @Inject constructor(
    private val repository: InputMatchRepository
): ViewModel() {
    private val _closeSetResult = MutableStateFlow<Result<CommonResponseModel>?>(null)
    val closeSetResult: StateFlow<Result<CommonResponseModel>?> get() = _closeSetResult

    private val _closeMatchResult = MutableStateFlow<Result<CommonResponseModel>?>(null)
    val closeMatchResult: StateFlow<Result<CommonResponseModel>?> get() = _closeMatchResult

    private val _selectedMatch = MutableStateFlow<MatchInfoModel.MatchResponsesModel?>(null)
    val selectedMatch: StateFlow<MatchInfoModel.MatchResponsesModel?> get() = _selectedMatch

    fun fetchCloseSets(request: CloseSetModel) {
        viewModelScope.launch {
            Timber.Forest.d("fetchCloseSets 요청: $request")
            repository.fetchCloseSets(request)
                .onSuccess { response ->
                    Timber.Forest.d("fetchCloseSets 성공: $response")
                    _closeSetResult.value = Result.success(response)
                }
                .onFailure { exception ->
                    Timber.Forest.e("fetchCloseSets 실패: ${exception.stackTraceToString()}")
                    _closeSetResult.value = Result.failure(exception)
                }
        }
    }

    fun fetchCloseMatch(request: CloseMatchModel) {
        viewModelScope.launch {
            Timber.Forest.d("fetchCloseMatch 요청: $request")
            repository.fetchCloseMatch(request)
                .onSuccess { response ->
                    Timber.Forest.d("fetchCloseMatch 성공: $response")
                    _closeMatchResult.value = Result.success(response)
                }
                .onFailure { exception ->
                    Timber.Forest.e("fetchCloseMatch 실패: ${exception.stackTraceToString()}")
                    _closeMatchResult.value = Result.failure(exception)
                }
        }
    }

    // (선택) 결과 재사용/중복 방지용 초기화 함수
    fun clearCloseResults() {
        _closeSetResult.value = null
        _closeMatchResult.value = null
    }

    fun updateSelectedMatch(match: MatchInfoModel.MatchResponsesModel) {
        _selectedMatch.value = match
        Timber.Forest.d("selectedMatch 업데이트: $match")
    }

    fun clearSelectedMatch() {
        _selectedMatch.value = null
    }

}
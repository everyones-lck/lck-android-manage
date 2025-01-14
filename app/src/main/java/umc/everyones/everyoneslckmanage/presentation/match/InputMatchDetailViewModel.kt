package umc.everyones.everyoneslckmanage.presentation.match

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import umc.everyones.everyoneslckmanage.domain.model.request.match.InputMatchModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.CommonResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.MatchInfoModel
import umc.everyones.everyoneslckmanage.domain.repository.InputMatchRepository
import javax.inject.Inject

@HiltViewModel
class InputMatchDetailViewModel @Inject constructor(
    private val repository: InputMatchRepository
): ViewModel(){
    private val _submitResult = MutableStateFlow<Result<CommonResponseModel>?>(null)
    val submitResult: StateFlow<Result<CommonResponseModel>?> get() = _submitResult

    fun fetchInputMatch(team1Id: Int, team2Id: Int, season: String, matchNumber: Long, matchDate: String) {
        viewModelScope.launch {
            Timber.d("InputMatch 요청: team1Id=$team1Id, team2Id=$team2Id, season=$season, matchNumber=$matchNumber, matchDate=$matchDate")
            repository.fetchInputMatch(InputMatchModel(team1Id, team2Id, season, matchNumber, matchDate)).onSuccess { response ->
                if ((response as? CommonResponseModel)?.success == true) {
                    Timber.d("InputMatch 성공: $response")
                    _submitResult.value = Result.success(response)
                } else {
                    val errorMsg = (response as? CommonResponseModel)?.message ?: "서버 오류"
                    Timber.e("서버에서 실패 응답: $errorMsg")
                    _submitResult.value = Result.failure(Exception(errorMsg))
                }
            }
                .onFailure { exception ->
                    Timber.e("InputMatch 실패: ${exception.stackTraceToString()}")
                    _submitResult.value = Result.failure(exception)
                }
        }
    }
    fun resetSubmitResult() {
        _submitResult.value = null
    }

}
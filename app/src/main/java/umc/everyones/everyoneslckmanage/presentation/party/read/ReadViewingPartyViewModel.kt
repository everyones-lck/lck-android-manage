package umc.everyones.everyoneslckmanage.presentation.party.read

import android.content.SharedPreferences
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import umc.everyones.everyoneslckmanage.domain.model.response.party.ReadViewingPartyModel
import umc.everyones.everyoneslckmanage.domain.repository.ViewingPartyRepository
import umc.everyones.everyoneslckmanage.util.network.EventFlow
import umc.everyones.everyoneslckmanage.util.network.MutableEventFlow
import umc.everyones.everyoneslckmanage.util.network.UiState

@HiltViewModel
class ReadViewingPartyViewModel @Inject constructor(
    private val repository: ViewingPartyRepository,
    private val spf: SharedPreferences
) : ViewModel() {
    private val _title = MutableStateFlow<String>("")
    val title: StateFlow<String> get() = _title

    private val _postId = MutableStateFlow<Long>(-1)
    val postId: StateFlow<Long> get() = _postId

    private val _readViewingPartyEvent = MutableStateFlow<UiState<ReadViewingPartyEvent>>(UiState.Empty)
    val readViewingPartyEvent: StateFlow<UiState<ReadViewingPartyEvent>> get() = _readViewingPartyEvent

    private val _isWriter = MutableEventFlow<Boolean>()
    val isWriter: EventFlow<Boolean> get() = _isWriter
    sealed class ReadViewingPartyEvent {
        data class ReadViewingParty(val viewingParty: ReadViewingPartyModel): ReadViewingPartyEvent()
        data object JoinViewingParty: ReadViewingPartyEvent()

        data object DeleteViewingParty: ReadViewingPartyEvent()

        data class WriteDoneViewingParty(val isWriteDone: Boolean): ReadViewingPartyEvent()
    }
    fun setTitle(title: String) {
        _title.value = title
    }

    fun setPostId(postId: Long){
        _postId.value = postId
    }

    fun fetchViewingParty() {
        viewModelScope.launch {
            _readViewingPartyEvent.value = UiState.Loading
            repository.fetchViewingParty(postId.value).onSuccess { response ->
                Timber.d("fetchViewingParty", response.toString())
                _readViewingPartyEvent.value = UiState.Success(
                    ReadViewingPartyEvent.ReadViewingParty(
                        response
                    )
                )
                val writerName = response.writerInfo.split("|").first().trim()
                Timber.d("writername", writerName.toString())
                _isWriter.emit(spf.getString("nickName", "").toString() == writerName)
            }.onFailure {
                Timber.d("fetchViewingParty error", it.stackTraceToString())
                _readViewingPartyEvent.value = UiState.Failure("뷰잉파티를 조회하지 못했습니다")
            }
        }
    }
}
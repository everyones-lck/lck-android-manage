package umc.everyones.everyoneslckmanage.presentation.community

import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import umc.everyones.everyoneslckmanage.domain.repository.CommunityRepository


@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepository
) : ViewModel() {
    private val _currentFilter = MutableStateFlow(Pair("잡담", true))
    val currentFilter: SharedFlow<Pair<String, Boolean>> get() = _currentFilter

    @OptIn(ExperimentalCoroutinesApi::class)
    val communityReportListPage = _currentFilter.flatMapLatest { (category, isPost) ->
        if (isPost) {
            repository.fetchPagingSource(category).map { it as PagingData<Any> }
        } else {
            repository.fetchCommentPagingSource(category).map { it as PagingData<Any> }
        }
    }.cachedIn(viewModelScope)

    // 필터 변경 시 호출할 함수 추가
    fun setFilter(category: String, isPost: Boolean) {
        _currentFilter.value = Pair(category, isPost)
    }

    private val _refreshEvent = MutableSharedFlow<Unit>()
    val refreshEvent: SharedFlow<Unit> get() = _refreshEvent

    fun refreshCategoryPage() { viewModelScope.launch { _refreshEvent.emit(Unit) } }
}
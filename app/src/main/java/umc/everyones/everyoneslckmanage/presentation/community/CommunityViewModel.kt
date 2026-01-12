package umc.everyones.everyoneslckmanage.presentation.community

import android.util.Log
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import umc.everyones.everyoneslckmanage.domain.model.request.community.PageableRequestModel
import umc.everyones.everyoneslckmanage.domain.repository.CommunityRepository


@HiltViewModel
class CommunityViewModel @Inject constructor(
    private val repository: CommunityRepository
) : ViewModel() {

    val smallTalkListPage = repository.fetchPagingSource("잡담").cachedIn(viewModelScope)
    val supportListPage = repository.fetchPagingSource("응원").cachedIn(viewModelScope)
    val freeAgentListPage = repository.fetchPagingSource("FA").cachedIn(viewModelScope)
    val tradeListPage = repository.fetchPagingSource("거래").cachedIn(viewModelScope)
    val questionListPage = repository.fetchPagingSource("질문").cachedIn(viewModelScope)
    val reviewListPage = repository.fetchPagingSource("후기").cachedIn(viewModelScope)

    private val _categoryNeedsRefresh = MutableStateFlow<String>("잡담")
    val categoryNeedsRefresh: StateFlow<String> get() = _categoryNeedsRefresh

    fun fetchCommunityList(postType: String, page: Int, size: Int) {
        viewModelScope.launch {
            repository.fetchCommunityList(postType, page, size).onSuccess { response ->
                Timber.d("fetchCommunityList: %s", response.toString())
            }.onFailure {
                Timber.d("fetchCommunityList error: %s", it.stackTraceToString())
            }
        }
    }

    fun getCommunityWithReportList(postType: String, page: Int, size: Int) {
        val pageable = PageableRequestModel(page, size, null)
        viewModelScope.launch {
            repository.getCommunityWithReportList(pageable, postType).onSuccess { response ->
                Timber.d("getCommunityWithReportList: %s", response.toString())
            }.onFailure {
                Timber.d("getCommunityWithReportList error: %s", it.stackTraceToString())
            }
        }
    }

    fun refreshCategoryPage(category: String) {
        _categoryNeedsRefresh.value = ""
        _categoryNeedsRefresh.value = category
    }

    fun deleteCommunityPost(postId: Long) {
        viewModelScope.launch {
            repository.deleteCommunityPost(postId).onSuccess { response ->
                Timber.d("deleteCommunityPost: %s", response.toString())
            }.onFailure {
                Timber.d("deleteCommunityPost error: %s", it.stackTraceToString())
            }
        }
    }

    fun deleteCommunityComment(commentId: Long) {
        viewModelScope.launch {
            repository.deleteCommunityComment(commentId).onSuccess { response ->
                Timber.d("deleteCommunityComment: %s", response.toString())
            }.onFailure {
                Timber.d("deleteCommunityComment error: %s", it.stackTraceToString())
            }
        }
    }
}
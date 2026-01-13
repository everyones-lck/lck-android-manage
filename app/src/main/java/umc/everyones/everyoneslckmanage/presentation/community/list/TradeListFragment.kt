package umc.everyones.everyoneslckmanage.presentation.community.list

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentCommunityListBinding
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommentWithReportListResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityWithReportListModel
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.presentation.community.CommunityViewModel
import umc.everyones.everyoneslckmanage.presentation.community.DeleteCommunityCommentFragmentDirections
import umc.everyones.everyoneslckmanage.presentation.community.DeleteCommunityContentFragmentDirections
import umc.everyones.everyoneslckmanage.presentation.community.adapter.CommentListRVA
import umc.everyones.everyoneslckmanage.presentation.community.adapter.PostListRVA
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted

class TradeListFragment : BaseFragment<FragmentCommunityListBinding>(R.layout.fragment_community_list) {
    private val viewModel: CommunityViewModel by activityViewModels()
    private var _postListRVA: PostListRVA? = null
    private val postListRVA get() = _postListRVA
    private var _commentListRVA: CommentListRVA? = null
    private val commentListRVA get() = _commentListRVA
    private var isPost: Boolean = true

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.currentFilter.collect { filter ->
                isPost = filter.second
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.communityReportListPage.collectLatest { data ->
                if (isPost) {
                    initPostListRVAdapter()
                    postListRVA?.submitData(data as PagingData<CommunityWithReportListModel.CommunityReportListElementModel>)?.let { postListRVA?.refresh() }
                } else {
                    initCommentListRVAdapter()
                    commentListRVA?.submitData(data as PagingData<CommentWithReportListResponseModel.CommentWithReportListResponseElementModel>)?.let { commentListRVA?.refresh() }
                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.currentFilter.collect { currentFilter ->
                Timber.d("trade", currentFilter)
                if (currentFilter.first == CATEGORY) {
                    if (isPost) _postListRVA?.refresh() else _commentListRVA?.refresh()
                    binding.rvCommunityList.scrollToPosition(0)
                }
            }
        }
    }

    override fun initView() {

    }

    private fun initPostListRVAdapter() {
        if (_postListRVA != null) return
        _postListRVA = PostListRVA { postId ->
            val action = DeleteCommunityContentFragmentDirections.actionDeleteCommunityContentFragmentToReadPostFragment(postId)
            findNavController().navigate(action)
        }
        binding.rvCommunityList.adapter = _postListRVA
        setupLoadStateListener(_postListRVA!!)
    }

    private fun initCommentListRVAdapter() {
        if (_commentListRVA != null) return
        _commentListRVA = CommentListRVA { commentId ->
            val action = DeleteCommunityCommentFragmentDirections.actionDeleteCommunityCommentFragmentToReadPostFragment(commentId)
            findNavController().navigate(action)
        }
        binding.rvCommunityList.adapter = _commentListRVA
        setupLoadStateListener(_commentListRVA!!)
    }

    private fun setupLoadStateListener(adapter: androidx.paging.PagingDataAdapter<*, *>) {
        adapter.addLoadStateListener { combinedLoadStates ->
            with(binding){
                layoutShimmer.isVisible = combinedLoadStates.source.refresh is LoadState.Loading
                rvCommunityList.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading
                if(combinedLoadStates.source.refresh is LoadState.Loading){
                    layoutShimmer.startShimmer()
                } else {
                    layoutShimmer.stopShimmer()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _postListRVA = null
        _commentListRVA = null
    }

    companion object {
        private const val CATEGORY = "거래"
    }
}
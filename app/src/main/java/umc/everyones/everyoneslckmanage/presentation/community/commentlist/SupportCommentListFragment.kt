package umc.everyones.everyoneslckmanage.presentation.community.commentlist

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentPostListBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.presentation.community.CommunityViewModel
import umc.everyones.everyoneslckmanage.presentation.community.DeleteCommunityCommentContentFragmentDirections
import umc.everyones.everyoneslckmanage.presentation.community.DeleteCommunityPostContentFragmentDirections
import umc.everyones.everyoneslckmanage.presentation.community.adapter.CommentListRVA
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted

class SupportCommentListFragment : BaseFragment<FragmentPostListBinding>(R.layout.fragment_post_list) {
    private val viewModel: CommunityViewModel by activityViewModels()
    private var _commentListRVA: CommentListRVA? = null
    private val commentListRVA
        get() = _commentListRVA

    private var readResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data?.getBooleanExtra("isReadMenuDone", false) == true){
                _commentListRVA?.refresh()
                binding.rvPostList.scrollToPosition(0)
            }
        }
    }

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.supportListPage.collectLatest { data ->
                commentListRVA?.submitData(data)
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.categoryNeedsRefresh.collect { categoryNeedsRefresh ->
                Timber.d("FreeAgent", categoryNeedsRefresh)
                if (categoryNeedsRefresh == CATEGORY) {
                    commentListRVA?.refresh()
                    binding.rvPostList.scrollToPosition(0)
                }
            }
        }
    }

    override fun initView() {
        initCommentListRVAdapter()
    }

    private fun initCommentListRVAdapter() {
        _commentListRVA = CommentListRVA { postId ->
            val action = DeleteCommunityCommentContentFragmentDirections.actionDeleteCommunityCommentContentFragmentToReadPostFragmentByComment(postId)
            findNavController().navigate(action)
        }
        binding.rvPostList.adapter = commentListRVA

        _commentListRVA?.addLoadStateListener { combinedLoadStates ->
            with(binding){
                layoutShimmer.isVisible = combinedLoadStates.source.refresh is LoadState.Loading
                rvPostList.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading
                if(combinedLoadStates.source.refresh is LoadState.Loading){
                    layoutShimmer.startShimmer()
                }

                if(combinedLoadStates.source.refresh is LoadState.NotLoading){
                    layoutShimmer.stopShimmer()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _commentListRVA = null
    }

    companion object {
        private const val CATEGORY = "응원"
    }
}
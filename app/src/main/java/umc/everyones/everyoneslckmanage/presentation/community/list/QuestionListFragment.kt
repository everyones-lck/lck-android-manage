package umc.everyones.everyoneslckmanage.presentation.community.list

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
import umc.everyones.everyoneslckmanage.presentation.community.DeleteCommunityContentFragmentDirections
import umc.everyones.everyoneslckmanage.presentation.community.adapter.PostListRVA
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted

class QuestionListFragment : BaseFragment<FragmentPostListBinding>(R.layout.fragment_post_list) {
    private val viewModel: CommunityViewModel by activityViewModels()
    private var _postListRVA: PostListRVA? = null
    private val postListRVA
        get() = _postListRVA

    private var readResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            if(result.data?.getBooleanExtra("isReadMenuDone", false) == true){
                _postListRVA?.refresh()
                binding.rvPostList.scrollToPosition(0)
            }
        }
    }

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.questionListPage.collectLatest { data ->
                postListRVA?.submitData(data)
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.categoryNeedsRefresh.collect { categoryNeedsRefresh ->
                Timber.d("Question", categoryNeedsRefresh)
                if (categoryNeedsRefresh == CATEGORY) {
                    postListRVA?.refresh()
                    binding.rvPostList.scrollToPosition(0)
                }
            }
        }
    }

    override fun initView() {
        initPostListRVAdapter()
    }

    private fun initPostListRVAdapter() {
        _postListRVA = PostListRVA { postId ->
            val action = DeleteCommunityContentFragmentDirections.actionDeleteCommunityContentFragmentToReadPostFragment(postId)
            findNavController().navigate(action)
        }
        binding.rvPostList.adapter = postListRVA

        _postListRVA?.addLoadStateListener { combinedLoadStates ->
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
        _postListRVA = null
    }

    companion object {
        private const val CATEGORY = "질문"
    }
}
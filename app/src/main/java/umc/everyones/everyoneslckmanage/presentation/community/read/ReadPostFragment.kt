package umc.everyones.everyoneslckmanage.presentation.community.read

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentReadPostBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.presentation.community.adapter.CommentRVA
import umc.everyones.everyoneslckmanage.presentation.community.adapter.ReadMediaRVA
import umc.everyones.everyoneslckmanage.util.GridSpaceItemDecoration
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import umc.everyones.everyoneslckmanage.util.extension.showCustomSnackBar
import umc.everyones.everyoneslckmanage.util.network.UiState

@AndroidEntryPoint
class ReadPostFragment : BaseFragment<FragmentReadPostBinding>(R.layout.fragment_read_post) {
    private val viewModel: ReadPostViewModel by activityViewModels()
    private val commentRVA by lazy {
        CommentRVA {
            val dialog = DeleteCommentDialogFragment()
            dialog.show(childFragmentManager, dialog.tag)
        }

    }

    private val readMediaRVA by lazy {
        ReadMediaRVA { url, isImage ->
            Log.d("url", url)
            // 미디어 원본 보기 기능
            viewModel.setImageUrl(url)
            val dialog = if (isImage) {
                ReadImageDialogFragment()
            } else {
                ReadVideoDialogFragment()
            }
            dialog.show(childFragmentManager, dialog.tag)
        }
    }

    // Community Fragment에서 전송한 postId 수신
    private val args: ReadPostFragmentArgs by navArgs()
    private val postId by lazy {
        args.postId
    }

    override fun initObserver() {
        repeatOnStarted {
            viewModel.readCommunityEvent.collect { state ->
                when (state) {
                    is UiState.Success -> {
                        handleReadCommunityEvent(state.data)
                    }

                    is UiState.Failure -> showCustomSnackBar(binding.root, state.msg)
                    else -> Unit
                }
            }
        }
    }

    private fun handleReadCommunityEvent(event: ReadPostViewModel.ReadCommunityEvent) {
        when (event) {
            ReadPostViewModel.ReadCommunityEvent.EditPost -> {}
            is ReadPostViewModel.ReadCommunityEvent.ReadPost -> {
                with(event.post) {
                    binding.tvReadPostTitle.text = postTitle
                    binding.tvReadPostBody.text = content
                    binding.tvReadWriter.text = writerInfo
                    binding.tvReadCategory.text = postType
                    binding.tvReadDate.text = postCreatedAt
                    Glide.with(requireContext())
                        .load(writerProfileUrl)
                        .into(binding.ivReadProfileImage)
                    commentRVA.submitList(commentList)
                    readMediaRVA.submitList(fileUrlList) {
                        binding.rvReadMedia.visibility = View.VISIBLE
                    }
                    binding.svRead.isVisible = true
                }
            }

            else -> Unit
        }
    }

    override fun initView() {
        // 키보드 올라올 때 화면 맨 밑으로 자동스크롤을 위한 리스너 등록
        viewModel.setPostId(postId)
        initCommentRVAdapter()
        initReadMediaRVAdapter()

        deletePost()
        viewModel.fetchCommunityPost()

        binding.ivReadBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
        Timber.d("postId", postId.toString())
    }

    private fun deletePost() {
        binding.ivDeletePostBtn.setOnSingleClickListener {
            val dialog = DeletePostDialogFragment()
            dialog.show(childFragmentManager, dialog.tag)
        }
    }

    private fun initCommentRVAdapter() {
        binding.rvReadComment.apply {
            adapter = commentRVA
            itemAnimator = null
        }
    }

    private fun initReadMediaRVAdapter() {
        binding.rvReadMedia.apply {
            adapter = readMediaRVA
            itemAnimator = null
        }

        // RecyclerView Item의 GridLayout에서의 일정한 간격을 위해 설정
        binding.rvReadMedia.addItemDecoration(GridSpaceItemDecoration(4, 8))
    }
}
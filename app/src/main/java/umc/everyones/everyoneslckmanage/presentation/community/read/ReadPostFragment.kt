package umc.everyones.everyoneslckmanage.presentation.community.read

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import timber.log.Timber
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentReadPostBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment

@AndroidEntryPoint
class ReadPostFragment : BaseFragment<FragmentReadPostBinding>(R.layout.activity_read_post) {
    private val viewModel: ReadPostViewModel by viewModels()
    private val commentRVA by lazy {
        CommentRVA(
            // 댓글 신고 기능
            reportComment = { commentId ->
                viewModel.reportCommunityComment(commentId)
            },

            // 댓글 삭제 기능
            deleteComment = { commentId ->
                viewModel.deleteComment(commentId)
            }
        )
    }

    private val readMediaRVA by lazy {
        ReadMediaRVA { url, isImage ->
            // 미디어 원본 보기 기능
            viewModel.setImageUrl(url)
            val dialog = if (isImage) {
                ReadImageDialogFragment()
            } else {
                ReadVideoDialogFragment()
            }
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }

    // Community Fragment에서 전송한 postId 수신
    private val postId by lazy {
        intent.getLongExtra("postId", 0)
    }

    private var editResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                if (result.data?.getBooleanExtra("isEditDone", false) == true) {
                    setResult(
                        RESULT_OK,
                        MainActivity.readMenuDoneIntent(this, true)
                    )
                    finish()
                }
            }
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

        repeatOnStarted {
            viewModel.isWriter.collect { isWriter ->
                with(binding) {
                    if (isWriter) {
                        layoutReadReportBtn.visibility = View.GONE
                        layoutReadWriterMenu.visibility = View.VISIBLE
                    } else {
                        layoutReadReportBtn.visibility = View.VISIBLE
                        layoutReadWriterMenu.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun handleReadCommunityEvent(event: ReadPostViewModel.ReadCommunityEvent) {
        when (event) {
            ReadPostViewModel.ReadCommunityEvent.DeletePost -> {
                setResult(
                    RESULT_OK,
                    MainActivity.readMenuDoneIntent(this, true)
                )
                finish()
            }

            ReadPostViewModel.ReadCommunityEvent.EditPost -> {}
            is ReadPostViewModel.ReadCommunityEvent.ReadPost -> {
                with(event.post) {
                    binding.tvReadPostTitle.text = postTitle
                    binding.tvReadPostBody.text = content
                    binding.tvReadWriter.text = writerInfo
                    binding.tvReadCategory.text = postType
                    binding.tvReadDate.text = postCreatedAt
                    Glide.with(this@ReadPostActivity)
                        .load(writerProfileUrl)
                        .into(binding.ivReadProfileImage)
                    commentRVA.submitList(commentList)
                    readMediaRVA.submitList(fileUrlList) {
                        binding.rvReadMedia.visibility = View.VISIBLE
                    }
                    binding.svRead.isVisible = true
                }
            }

            ReadPostViewModel.ReadCommunityEvent.ReportComment -> {
                showCustomSnackBar(binding.layoutReadReportBtn, "댓글이 신고 되었습니다")
            }

            ReadPostViewModel.ReadCommunityEvent.ReportPost -> {
                showCustomSnackBar(binding.layoutReadReportBtn, "게시글이 신고 되었습니다")
            }

            ReadPostViewModel.ReadCommunityEvent.CreateComment -> {
                binding.etReadCommentInput.setText("")
            }

            ReadPostViewModel.ReadCommunityEvent.DeleteComment -> {

            }
        }
    }

    override fun initView() {
        // 키보드 올라올 때 화면 맨 밑으로 자동스크롤을 위한 리스너 등록
        viewModel.setPostId(postId)
        KeyboardUtil.registerKeyboardVisibilityListener(
            binding.root,
            binding.svRead,
            binding.etReadCommentInput
        )
        initCommentRVAdapter()
        initReadMediaRVAdapter()

        // 댓글 유효성 검사
        validateCommentSend()
        editPost()
        reportPost()
        deletePost()
        sendComment()
        viewModel.fetchCommunityPost()

        binding.ivReadBackBtn.setOnSingleClickListener {
            finish()
        }
        Timber.d("postId", postId.toString())
    }

    private fun reportPost() {
        binding.layoutReadReportBtn.setOnSingleClickListener {
            viewModel.reportCommunityPost()
        }
    }

    private fun editPost() {
        binding.layoutReadEditBtn.setOnSingleClickListener {
            // 글 작성 화면으로 이동 및 현재 게시글 Data 전송
            editResultLauncher.launch(
                WritePostActivity.editIntent(
                    this,
                    EditPost(
                        postId,
                        binding.tvReadPostTitle.text.toString(),
                        binding.tvReadPostBody.text.toString(),
                        binding.tvReadCategory.textToString()
                    )
                )
            )
        }
    }

    private fun deletePost() {
        binding.layoutReadDeleteBtn.setOnSingleClickListener {
            viewModel.deleteCommunityPost()
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

    private fun validateCommentSend() {
        binding.etReadCommentInput.addTextChangedListener(
            onTextChanged = { text: CharSequence?, _: Int, _: Int, _: Int ->
                if (text != null) {

                    // 댓글 작성 여부에 따른 전송 버튼 활성화 제어
                    if (text.isEmpty()) {
                        binding.ivReadSendCommentBtn.setImageDrawable(drawableOf(R.drawable.ic_send))
                    } else {
                        binding.ivReadSendCommentBtn.setImageDrawable(drawableOf(R.drawable.ic_send_enabled))
                    }

                    if (text.length >= 1000) {
                        showCustomSnackBar(
                            binding.ivReadSendCommentBtn,
                            "댓글은 최대 1,000자까지 입력할 수 있어요"
                        )
                    }
                }
            }
        )
    }

    private fun sendComment() {
        with(binding) {
            ivReadSendCommentBtn.setOnSingleClickListener {
                if(etReadCommentInput.textToString().isNotEmpty()) {
                    viewModel.createComment(etReadCommentInput.textToString())
                }
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        hideKeyboardOnOutsideTouch(event, binding.etReadCommentInput, binding.layoutReadSendComment)
        return super.dispatchTouchEvent(event)
    }

    companion object {
        fun newIntent(context: Context, postId: Long) =
            Intent(context, ReadPostActivity::class.java).apply {
                putExtra("postId", postId)
            }

        fun editDoneIntent(context: Context, isEditDone: Boolean) =
            Intent(context, ReadPostActivity::class.java).apply {
                putExtra("isEditDone", isEditDone)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        KeyboardUtil.unregisterKeyboardVisibilityListener(binding.root)
    }
}
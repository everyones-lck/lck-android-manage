package umc.everyones.everyoneslckmanage.presentation.community.read

import androidx.fragment.app.activityViewModels
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.DialogDeleteCommentBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseDialogFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class DeleteCommentDialogFragment: BaseDialogFragment<DialogDeleteCommentBinding>(R.layout.dialog_delete_comment) {
    private val viewModel: ReadPostViewModel by activityViewModels()
    override fun initObserver() {

    }

    override fun initView() {
        requireContext().dialogFragmentResize(this, 0.8f)
        binding.btnDeleteCommentCancel.setOnSingleClickListener {
            dismiss()
        }
    }
}
package umc.everyones.everyoneslckmanage.presentation.party.read

import androidx.fragment.app.activityViewModels
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.DialogDeleteCommentBinding
import umc.everyones.everyoneslckmanage.databinding.DialogDeleteViewingPartyBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseDialogFragment
import umc.everyones.everyoneslckmanage.presentation.community.read.ReadPostViewModel
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class DeleteViewingPartyDialogFragment: BaseDialogFragment<DialogDeleteViewingPartyBinding>(R.layout.dialog_delete_viewing_party) {
    private val viewModel: ReadPostViewModel by activityViewModels()
    override fun initObserver() {

    }

    override fun initView() {
        requireContext().dialogFragmentResize(this, 0.8f)
        binding.btnDeletePostCancel.setOnSingleClickListener {
            dismiss()
        }
    }
}
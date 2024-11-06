package umc.everyones.everyoneslckmanage.presentation.community.read

import androidx.navigation.fragment.findNavController
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentCommentReportReasonBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentViewingPartyReportReasonBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class CommentReportReasonFragment  : BaseFragment<FragmentCommentReportReasonBinding>(R.layout.fragment_comment_report_reason){
    private val navigator by lazy {
        findNavController()
    }
    override fun initObserver() {

    }

    override fun initView() {
        binding.ivCommentReportBackBtn.setOnSingleClickListener {
            navigator.navigateUp()
        }
    }

}
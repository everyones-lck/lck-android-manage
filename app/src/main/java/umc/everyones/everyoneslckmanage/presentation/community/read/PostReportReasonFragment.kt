package umc.everyones.everyoneslckmanage.presentation.community.read

import androidx.navigation.fragment.findNavController
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentPostReportReasonBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentViewingPartyReportReasonBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class PostReportReasonFragment  : BaseFragment<FragmentPostReportReasonBinding>(R.layout.fragment_post_report_reason){
    private val navigator by lazy {
        findNavController()
    }
    override fun initObserver() {

    }

    override fun initView() {
        binding.ivPostReportBackBtn.setOnSingleClickListener {
            navigator.navigateUp()
        }
    }

}
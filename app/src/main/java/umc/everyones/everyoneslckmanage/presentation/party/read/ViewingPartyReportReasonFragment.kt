package umc.everyones.everyoneslckmanage.presentation.party.read

import androidx.navigation.fragment.findNavController
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentViewingPartyReportReasonBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class ViewingPartyReportReasonFragment : BaseFragment<FragmentViewingPartyReportReasonBinding>(R.layout.fragment_viewing_party_report_reason){
    private val navigator by lazy {
        findNavController()
    }
    override fun initObserver() {

    }

    override fun initView() {
        binding.ivViewingPartyReportBackBtn.setOnSingleClickListener {
            navigator.navigateUp()
        }
    }

}
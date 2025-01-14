package umc.everyones.everyoneslckmanage.presentation.vote

import androidx.navigation.fragment.findNavController
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentInputMatchInfoBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentManageMatchPredictVoteBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class ManageMatchPredictVoteTimeFragment : BaseFragment<FragmentManageMatchPredictVoteBinding>(R.layout.fragment_manage_match_predict_vote) {
    override fun initObserver() {

    }

    override fun initView() {
        goBackButton()
    }

    private fun goBackButton() {
        binding.ivManagePredictionBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }
}
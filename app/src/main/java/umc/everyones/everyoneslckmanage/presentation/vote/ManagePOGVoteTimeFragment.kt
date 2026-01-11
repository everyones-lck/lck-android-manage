package umc.everyones.everyoneslckmanage.presentation.vote

import androidx.navigation.fragment.findNavController
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentInputMatchInfoBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentManagePogVoteBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class ManagePOGVoteTimeFragment : BaseFragment<FragmentManagePogVoteBinding>(R.layout.fragment_manage_pog_vote) {
    override fun initObserver() {

    }

    override fun initView() {
        goBackButton()
    }

    private fun goBackButton() {
        binding.ivManagePogBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }
}
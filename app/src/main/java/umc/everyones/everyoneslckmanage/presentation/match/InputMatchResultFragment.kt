package umc.everyones.everyoneslckmanage.presentation.match

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentInputSetResultBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class InputMatchResultFragment : BaseFragment<FragmentInputSetResultBinding>(R.layout.fragment_input_set_result) {
    private val viewModel: InputMatchResultViewModel by activityViewModels()
    override fun initObserver() {

    }

    override fun initView() {
        goBackButton()
    }

    private fun goBackButton() {
        binding.ivSetResultBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }
}
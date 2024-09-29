package umc.everyones.everyoneslckmanage.presentation.team


import androidx.navigation.fragment.findNavController
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class UpdateTeamInfoFragment : BaseFragment<FragmentUpdateTeamInfoBinding>(R.layout.fragment_update_team_info) {

    private val navigator by lazy {
        findNavController()
    }
    override fun initObserver() {

    }

    override fun initView() {
        setTeamNameClickListener()
        setupBackButtonListener()
    }

    private fun setTeamNameClickListener() {
        val teamTextViews = listOf(
            binding.tvUpdateTeamGen,
            binding.tvUpdateTeamT1,
            binding.tvUpdateTeamHle,
            binding.tvUpdateTeamDk,
            binding.tvUpdateTeamKt,
            binding.tvUpdateTeamDrx,
            binding.tvUpdateTeamNs,
            binding.tvUpdateTeamBro,
            binding.tvUpdateTeamKdf,
            binding.tvUpdateTeamBnk
        )

        teamTextViews.forEach { textView ->
            textView.setOnSingleClickListener {
                val teamName = textView.text.toString()
                val action = UpdateTeamInfoFragmentDirections.actionUpdateTeamInfoFragmentToUpdateTeamInfoDetailFragment(teamName)
                navigator.navigate(action)
            }
        }
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoFragmentDirections
                .actionUpdateTeamInfoFragmentToHomeFragment()
            navigator.navigate(action)
        }
    }
}

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
        val teamMap = mapOf(
            "GEN" to 2,
            "T1" to 5,
            "HLE" to 3,
            "DK" to 4,
            "KT" to 6,
            "DRX" to 10,
            "NS" to 9,
            "BRO" to 11,
            "KDF" to 7,
            "BNK" to 8
        )

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
                val teamId = teamMap[teamName] ?: return@setOnSingleClickListener
                val action = UpdateTeamInfoFragmentDirections
                    .actionUpdateTeamInfoFragmentToUpdateTeamInfoDetailFragment(teamName, teamId)
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

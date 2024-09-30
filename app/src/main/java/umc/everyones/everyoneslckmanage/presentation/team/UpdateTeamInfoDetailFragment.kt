package umc.everyones.everyoneslckmanage.presentation.team

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoDetailBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class UpdateTeamInfoDetailFragment : BaseFragment<FragmentUpdateTeamInfoDetailBinding>(R.layout.fragment_update_team_info_detail) {

    private val navigator by lazy {
        findNavController()
    }

    override fun initObserver() {
    }

    override fun initView() {
        setupBackButtonListener()
        setupTeamName()
        setupLckRoasterNavigation()
        setupLckClRoasterNavigation()
        setupLckCoachNavigation()
        setupWinningHistoryNavigation()
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamDetailPrevious.setOnClickListener {
            val action = UpdateTeamInfoDetailFragmentDirections
                .actionUpdateTeamInfoDetailToUpdateTeamInfoFragment()
            navigator.navigate(action)
        }
    }

    private fun setupTeamName() {
        val teamName = arguments?.getString("teamName") ?: "Unknown Team"
        binding.tvUpdateTeamDetailTeamName.text = teamName
    }

    private fun setupLckRoasterNavigation() {
        binding.tvUpdateTeamDetailLckRoaster.setOnClickListener {
            val teamName = binding.tvUpdateTeamDetailTeamName.text.toString()
            val action = UpdateTeamInfoDetailFragmentDirections
                .actionUpdateTeamInfoDetailToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = teamName
                )
            navigator.navigate(action)
        }
    }

    private fun setupLckClRoasterNavigation() {
        binding.tvUpdateTeamDetailLckClRoaster.setOnClickListener {
            val teamName = binding.tvUpdateTeamDetailTeamName.text.toString()
            val action = UpdateTeamInfoDetailFragmentDirections
                .actionUpdateTeamInfoDetailToUpdateTeamInfoLckClRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = teamName
                )
            navigator.navigate(action)
        }
    }

    private fun setupLckCoachNavigation() {
        binding.tvUpdateTeamDetailLckCoach.setOnClickListener {
            val teamName = binding.tvUpdateTeamDetailTeamName.text.toString()
            val action = UpdateTeamInfoDetailFragmentDirections
                .actionUpdateTeamInfoDetailToUpdateTeamInfoLckCoachFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = teamName
                )
            navigator.navigate(action)
        }
    }

    private fun setupWinningHistoryNavigation() {
        binding.tvUpdateTeamDetailWinningHistory.setOnClickListener {
            val teamName = binding.tvUpdateTeamDetailTeamName.text.toString()
            val action = UpdateTeamInfoDetailFragmentDirections
                .actionUpdateTeamInfoDetailToUpdateTeamInfoWinningHistoryFragment(
                    teamName = teamName
                )
            navigator.navigate(action)
        }
    }
}
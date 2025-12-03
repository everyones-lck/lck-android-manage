package umc.everyones.everyoneslckmanage.presentation.team

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckCoachBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class UpdateTeamInfoLckCoachFragment :
    BaseFragment<FragmentUpdateTeamInfoLckCoachBinding>(R.layout.fragment_update_team_info_lck_coach) {

    private val viewModel: UpdateTeamInfoLckCoachViewModel by activityViewModels()
    private lateinit var coachAdapter: LckCoachRVA

    private val navigator by lazy { findNavController() }

    private var teamName: String = "Unknown Team"
    private var teamId: Int = -1
    private val role = "COACH"

    override fun initView() {
        val args = UpdateTeamInfoLckCoachFragmentArgs.fromBundle(requireArguments())
        teamName = args.teamName ?: "Unknown Team"
        teamId = args.teamId

        setupUI()
        setupRecycler()
        setupNavigationButtons()
        fetchData()
    }

    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.coaches.collect { coachList ->
                coachAdapter.submitList(
                    coachList.map {
                        LckCoach(
                            id = it.playerId,
                            name = it.playerName,
                            imageUrl = it.imageUrl ?: "",
                            teamName = teamName
                        )
                    }
                )
            }
        }
    }

    private fun setupUI() {
        binding.tvUpdateTeamLckCoachTeamName.text = teamName
    }

    private fun setupRecycler() {
        coachAdapter = LckCoachRVA { coach ->
            val action = UpdateTeamInfoLckCoachFragmentDirections
                .actionUpdateTeamInfoLckCoachFragmentToUpdateTeamInfoLckCoachEditFragment(
                    playerId = coach.id,
                    playerName = coach.name,
                    playerPosition = "COACH",
                    playerImageUrl = coach.imageUrl,
                    teamName = teamName,
                    teamId = teamId
                )
            navigator.navigate(action)
        }

        binding.rvUpdateTeamLckCoach.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = coachAdapter
        }
    }

    private fun setupNavigationButtons() {
        binding.ivUpdateTeamLckCoachPrevious.setOnSingleClickListener {
            val action = UpdateTeamInfoLckCoachFragmentDirections
                .actionUpdateTeamInfoLckCoachFragmentToUpdateTeamInfoDetailFragment(
                    teamName = teamName,
                    teamId = teamId
                )
            navigator.navigate(action)
        }

        binding.ivUpdateTeamLckCoachPlayerAdd.setOnSingleClickListener {
            val action = UpdateTeamInfoLckCoachFragmentDirections
                .actionUpdateTeamInfoLckCoachFragmentToUpdateTeamInfoLckCoachAddFragment(
                    teamName = teamName,
                    teamId = teamId
                )
            navigator.navigate(action)
        }
    }

    private fun fetchData() {
        viewModel.fetchCoachData(teamId, role)
    }
}

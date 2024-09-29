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
class UpdateTeamInfoLckCoachFragment : BaseFragment<FragmentUpdateTeamInfoLckCoachBinding>(R.layout.fragment_update_team_info_lck_coach) {

    private val viewModel: UpdateTeamInfoLckCoachViewModel by activityViewModels()
    private lateinit var lckCoachAdapter: LckCoachRVA

    private val navigator by lazy {
        findNavController()
    }

    override fun initView() {
        val teamName = arguments?.getString("teamName") ?: viewModel.teamName ?: "Unknown Team"
        viewModel.teamName = teamName
        setTeamName(teamName)

        initLckCoachRVAdapter()
        setupBackButtonListener(teamName)

        binding.ivUpdateTeamLckCoachPlayerAdd.setOnClickListener {
            val action = UpdateTeamInfoLckCoachFragmentDirections
                .actionUpdateTeamInfoLckCoachFragmentToUpdateTeamInfoLckCoachAddFragment(
                    playerId = null,
                    playerName = null,
                    playerImageUrl = null
                )
            navigator.navigate(action)
        }

        val newPlayer = arguments?.getSerializable("newPlayer") as? LckCoach
        newPlayer?.let {
            viewModel.addPlayer(it)
        }

        val updatedRoaster = arguments?.getSerializable("updatedRoaster") as? LckCoach
        updatedRoaster?.let {
            viewModel.updatePlayer(it)
        }
    }

    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.lckCoachState.collect { list ->
                lckCoachAdapter.submitList(list.toList())
            }
        }
    }

    private fun setTeamName(teamName: String) {
        binding.tvUpdateTeamLckCoachTeamName.text = teamName
    }
    private fun setupBackButtonListener(teamName: String) {
        binding.ivUpdateTeamLckCoachPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckCoachFragmentDirections
                .actionUpdateTeamInfoLckCoachFragmentToUpdateTeamInfoDetailFragment(
                    teamName = teamName
                )
            navigator.navigate(action)
        }
    }

    private fun initLckCoachRVAdapter() {
        lckCoachAdapter = LckCoachRVA() { roaster ->
            val action = UpdateTeamInfoLckCoachFragmentDirections
                .actionUpdateTeamInfoLckCoachFragmentToUpdateTeamInfoLckCoachEditFragment(
                    playerId = roaster.id,
                    playerName = roaster.name,
                    playerImageUrl = roaster.imageUrl
                )
            navigator.navigate(action)
        }

        binding.rvUpdateTeamLckCoach.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lckCoachAdapter
        }
    }
}

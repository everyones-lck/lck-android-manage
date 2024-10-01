package umc.everyones.everyoneslckmanage.presentation.team

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class UpdateTeamInfoLckRoasterFragment : BaseFragment<FragmentUpdateTeamInfoLckRoasterBinding>(R.layout.fragment_update_team_info_lck_roaster) {

    private val viewModel: UpdateTeamInfoLckRoasterViewModel by activityViewModels()
    private lateinit var lckRoasterAdapter: LckRoasterRVA

    private val navigator by lazy {
        findNavController()
    }

    override fun initView() {
        val teamName = arguments?.getString("teamName") ?: viewModel.teamName ?: "Unknown Team"
        viewModel.teamName = teamName
        setTeamName(teamName)

        initLckRoasterRVAdapter()
        setupBackButtonListener(teamName)

        binding.ivUpdateTeamLckRoasterPlayerAdd.setOnSingleClickListener {
            val action = UpdateTeamInfoLckRoasterFragmentDirections
                .actionUpdateTeamInfoLckRoasterFragmentToUpdateTeamInfoLckRoasterAddFragment(
                    playerId = -1,
                    playerName = null,
                    playerPosition = null,
                    playerImageUrl = null
                )
            navigator.navigate(action)
        }

        val newPlayer = arguments?.getSerializable("newPlayer") as? LckRoaster
        newPlayer?.let {
            viewModel.addPlayerToTeam(it)
        }

        val updatedRoaster = arguments?.getSerializable("updatedRoaster") as? LckRoaster
        updatedRoaster?.let {
            viewModel.updatePlayer(it)
        }
    }

    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.allRoasters.collect { allRoasters ->
                val teamName = viewModel.teamName ?: "Unknown Team"
                val teamRoasterList = viewModel.getRoasterForTeam(teamName)
                lckRoasterAdapter.submitList(teamRoasterList.toList())
            }
        }
    }

    private fun setTeamName(teamName: String) {
        binding.tvUpdateTeamLckRoasterTeamName.text = teamName
    }

    private fun setupBackButtonListener(teamName: String) {
        binding.ivUpdateTeamLckRoasterPrevious.setOnSingleClickListener {
            val action = UpdateTeamInfoLckRoasterFragmentDirections
                .actionUpdateTeamInfoLckRoasterFragmentToUpdateTeamInfoDetailFragment(
                    teamName = teamName
                )
            navigator.navigate(action)
        }
    }

    private fun initLckRoasterRVAdapter() {
        lckRoasterAdapter = LckRoasterRVA { roaster ->
            val action = UpdateTeamInfoLckRoasterFragmentDirections
                .actionUpdateTeamInfoLckRoasterFragmentToUpdateTeamInfoLckRoasterEditFragment(
                    playerId = roaster.id,
                    playerName = roaster.name,
                    playerPosition = roaster.position,
                    playerImageUrl = roaster.imageUrl
                )
            navigator.navigate(action)
        }

        binding.rvUpdateTeamLckRoaster.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lckRoasterAdapter
        }
    }
}

package umc.everyones.everyoneslckmanage.presentation.team

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckClRoasterBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class UpdateTeamInfoLckClRoasterFragment : BaseFragment<FragmentUpdateTeamInfoLckClRoasterBinding>(R.layout.fragment_update_team_info_lck_cl_roaster) {

    private val viewModel: UpdateTeamInfoLckClRoasterViewModel by activityViewModels()
    private lateinit var lckClRoasterAdapter: LckClRoasterRVA

    private val navigator by lazy {
        findNavController()
    }

    override fun initView() {
        val teamName = arguments?.getString("teamName") ?: viewModel.teamName ?: "Unknown Team"
        viewModel.teamName = teamName
        setTeamName(teamName)

        initLckClRoasterRVAdapter()
        setupBackButtonListener(teamName)

        binding.ivUpdateTeamLckClRoasterPlayerAdd.setOnSingleClickListener {
            val action = UpdateTeamInfoLckClRoasterFragmentDirections
                .actionUpdateTeamInfoLckClRoasterFragmentToUpdateTeamInfoLckClRoasterAddFragment(
                    playerId = -1,
                    playerName = null,
                    playerPosition = null,
                    playerImageUrl = null
                )
            navigator.navigate(action)
        }

        val newPlayer = arguments?.getSerializable("newPlayer") as? LckClRoaster
        newPlayer?.let {
            viewModel.addPlayerToTeam(it)
        }

        val updatedRoaster = arguments?.getSerializable("updatedRoaster") as? LckClRoaster
        updatedRoaster?.let {
            viewModel.updatePlayer(it)
        }
    }

    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.allClRoasters.collect { allClRoasters ->
                val teamName = viewModel.teamName ?: "Unknown Team"
                val teamRoasterList = viewModel.getRoasterForTeam(teamName)
                lckClRoasterAdapter.submitList(teamRoasterList.toList())
            }
        }
    }

    private fun setTeamName(teamName: String) {
        binding.tvUpdateTeamLckClRoasterTeamName.text = teamName
    }
    private fun setupBackButtonListener(teamName: String) {
        binding.ivUpdateTeamLckClRoasterPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckClRoasterFragmentDirections
                .actionUpdateTeamInfoLckClRoasterFragmentToUpdateTeamInfoDetailFragment(
                    teamName = teamName
                )
            navigator.navigate(action)
        }
    }

    private fun initLckClRoasterRVAdapter() {
        lckClRoasterAdapter = LckClRoasterRVA { roaster ->
            val action = UpdateTeamInfoLckClRoasterFragmentDirections
                .actionUpdateTeamInfoLckClRoasterFragmentToUpdateTeamInfoLckClRoasterEditFragment(
                    playerId = roaster.id,
                    playerName = roaster.name,
                    playerPosition = roaster.position,
                    playerImageUrl = roaster.imageUrl
                )
            navigator.navigate(action)
        }

        binding.rvUpdateTeamLckRoasterCl.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lckClRoasterAdapter
        }
    }
}

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
class UpdateTeamInfoLckClRoasterFragment:
    BaseFragment<FragmentUpdateTeamInfoLckClRoasterBinding>(R.layout.fragment_update_team_info_lck_cl_roaster) {

    private val viewModel: UpdateTeamInfoLckClRoasterViewModel by activityViewModels()
    private lateinit var lckClRoasterAdapter: LckClRoasterRVA

    private val navigator by lazy { findNavController() }

    private var teamName: String = "Unknown Team"
    private var teamId: Int = -1
    private val role = "LCK_CL_ROSTER"

    override fun initView() {
        val args = UpdateTeamInfoLckClRoasterFragmentArgs.fromBundle(requireArguments())

        teamName = args.teamName ?: "Unknown Team"
        teamId = args.teamId

        setTeamName(teamName)
        initLckClRoasterRVAdapter()
        setupBackButtonListener()
        setupAddButtonListener()
        fetchData()
    }

    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.players.collect { playerList ->
                val roasterList = playerList.map { player ->
                    LckClRoaster(
                        id = player.playerId,
                        name = player.playerName,
                        position = player.playerPosition,
                        imageUrl = player.imageUrl ?: "",
                        teamName = player.playerRole
                    )
                }
                lckClRoasterAdapter.submitList(roasterList)
            }
        }
    }

    private fun fetchData() {
        viewModel.fetchRoasterData(teamId, role)
    }

    private fun setTeamName(teamName: String) {
        binding.tvUpdateTeamLckClRoasterTeamName.text = teamName
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckClRoasterPrevious.setOnSingleClickListener {
            val action =
                UpdateTeamInfoLckClRoasterFragmentDirections
                    .actionUpdateTeamInfoLckClRoasterFragmentToUpdateTeamInfoDetailFragment(
                        teamName = teamName,
                        teamId = teamId
                    )
            navigator.navigate(action)
        }
    }

    private fun setupAddButtonListener() {
        binding.ivUpdateTeamLckClRoasterPlayerAdd.setOnSingleClickListener {
            val action =
                UpdateTeamInfoLckClRoasterFragmentDirections
                    .actionUpdateTeamInfoLckClRoasterFragmentToUpdateTeamInfoLckClRoasterAddFragment(
                        teamName = teamName,
                        teamId = teamId
                    )
            navigator.navigate(action)
        }
    }

    private fun initLckClRoasterRVAdapter() {
        lckClRoasterAdapter = LckClRoasterRVA { roaster ->
            val action =
                UpdateTeamInfoLckClRoasterFragmentDirections
                    .actionUpdateTeamInfoLckClRoasterFragmentToUpdateTeamInfoLckClRoasterEditFragment(
                        playerId = roaster.id,
                        playerName = roaster.name,
                        playerPosition = roaster.position,
                        playerImageUrl = roaster.imageUrl,
                        teamName = teamName,
                        teamId = teamId
                    )
            navigator.navigate(action)
        }

        binding.rvUpdateTeamLckRoasterCl.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lckClRoasterAdapter
        }
    }
}

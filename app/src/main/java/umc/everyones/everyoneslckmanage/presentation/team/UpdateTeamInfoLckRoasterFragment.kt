package umc.everyones.everyoneslckmanage.presentation.team

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import javax.inject.Inject

@AndroidEntryPoint
class UpdateTeamInfoLckRoasterFragment : BaseFragment<FragmentUpdateTeamInfoLckRoasterBinding>(R.layout.fragment_update_team_info_lck_roaster) {

    private val viewModel: UpdateTeamInfoLckRoasterViewModel by activityViewModels()
    private lateinit var lckRoasterAdapter: LckRoasterRVA

    private val navigator by lazy {
        findNavController()
    }

    private var teamName: String = "Unknown Team"
    private var teamId: Int = -1
    private val role: String = "LCK_ROSTER"

    override fun initView() {
        val args = UpdateTeamInfoLckRoasterFragmentArgs.fromBundle(requireArguments())
        teamName = args.teamName ?: "Unknown Team"
        teamId = args.teamId

        setTeamName(teamName)
        initLckRoasterRVAdapter()
        setupBackButtonListener()
        fetchData()
    }

    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.players.collect { playerList ->
                val roasterList = playerList.map { player ->
                    LckRoaster(
                        id = player.playerId,
                        name = player.playerName,
                        position = player.playerPosition,
                        imageUrl = player.imageUrl ?: "",
                        teamName = player.playerRole
                    )
                }
                lckRoasterAdapter.submitList(roasterList)
            }
        }
    }

    private fun fetchData() {
        viewModel.fetchRoasterData(teamId, role)
    }

    private fun setTeamName(teamName: String) {
        binding.tvUpdateTeamLckRoasterTeamName.text = teamName
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckRoasterPrevious.setOnSingleClickListener {
            val action = UpdateTeamInfoLckRoasterFragmentDirections
                .actionUpdateTeamInfoLckRoasterFragmentToUpdateTeamInfoDetailFragment(
                    teamName = teamName,
                    teamId = teamId
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
                    playerImageUrl = roaster.imageUrl,
                    teamName = teamName,
                    teamId = teamId
                )
            navigator.navigate(action)
        }

        binding.rvUpdateTeamLckRoaster.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = lckRoasterAdapter
        }
    }
}

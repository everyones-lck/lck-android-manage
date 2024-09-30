package umc.everyones.everyoneslckmanage.presentation.team

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoHistoryOfRoasterBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoWinningHistoryBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class UpdateTeamInfoHistoryOfRoasterFragment : BaseFragment<FragmentUpdateTeamInfoHistoryOfRoasterBinding>(R.layout.fragment_update_team_info_history_of_roaster) {

    private val viewModel: UpdateTeamInfoHistoryOfRoasterViewModel by viewModels()

    private val navigator by lazy {
        findNavController()
    }


    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.historyOfRoasterList.collect { list ->
                (binding.rvUpdateTeamHistoryOfRoaster.adapter as HistoryOfRoasterRVA).submitList(list)
            }
        }
    }

    override fun initView() {
        val teamName = arguments?.getString("teamName") ?: viewModel.teamName ?: "Unknown Team"
        viewModel.teamName = teamName

        setupRecyclerView()
        setupAddButton()
        setTeamName(teamName)
        setupBackButtonListener(teamName)
    }

    private fun setupRecyclerView() {
        binding.rvUpdateTeamHistoryOfRoaster.layoutManager = LinearLayoutManager(context)
        binding.rvUpdateTeamHistoryOfRoaster.adapter = HistoryOfRoasterRVA(
            onAddWinningHistory = { newHistory ->
                viewModel.addWinningHistory(newHistory)
            },
            onSaveWinningHistory = { updatedHistory ->
                viewModel.updateWinningHistory(updatedHistory)
            },
            onDeleteWinningHistory = { historyToDelete ->
                viewModel.deleteWinningHistory(historyToDelete)
            }
        )
    }

    private fun setupBackButtonListener(teamName: String) {
        binding.ivUpdateTeamHistoryOfRoasterPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoHistoryOfRoasterFragmentDirections
                .actionUpdateTeamInfoHistoryOfRoasterFragmentToUpdateTeamInfoDetailFragment(
                    teamName = teamName
                )
            navigator.navigate(action)
        }
    }

    private fun setupAddButton() {
        binding.ivUpdateTeamHistoryOfRoasterAdd.setOnSingleClickListener {
            (binding.rvUpdateTeamHistoryOfRoaster.adapter as HistoryOfRoasterRVA).enterAddMode()
        }
    }

    private fun setTeamName(teamName: String) {
        binding.tvUpdateTeamHistoryOfRoasterTeamName.text = teamName
    }
}
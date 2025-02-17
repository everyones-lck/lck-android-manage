package umc.everyones.everyoneslckmanage.presentation.team

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoWinningHistoryBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class UpdateTeamInfoWinningHistoryFragment : BaseFragment<FragmentUpdateTeamInfoWinningHistoryBinding>(R.layout.fragment_update_team_info_winning_history) {

    private val viewModel: UpdateTeamInfoWinningHistoryViewModel by activityViewModels()

    private val winningHistoryAdapter by lazy {
        WinningHistoryRVA(
            onAddWinningHistory = { newHistory -> viewModel.addWinningHistory(newHistory) },
            onSaveWinningHistory = { updatedHistory -> viewModel.updateWinningHistory(updatedHistory) },
            onDeleteWinningHistory = { historyToDelete -> viewModel.deleteWinningHistory(historyToDelete) },
            viewModel = viewModel
        )
    }

    private val navigator by lazy {
        findNavController()
    }


    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.winningHistoryList.collect { allCoaches ->
                val teamName = viewModel.teamName ?: "Unknown Team"
                val teamCoachList = viewModel.getWinningHistoryForTeam(teamName)
                winningHistoryAdapter.submitList(teamCoachList.toList())
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
        binding.rvUpdateTeamWinningHistory.layoutManager = LinearLayoutManager(context)
        binding.rvUpdateTeamWinningHistory.adapter = winningHistoryAdapter
    }

    private fun setupBackButtonListener(teamName: String) {
        binding.ivUpdateTeamWinningHistoryPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoWinningHistoryFragmentDirections
                .actionUpdateTeamInfoWinningHistoryFragmentToUpdateTeamInfoDetailFragment(
                    teamName = teamName
                )
            navigator.navigate(action)
        }
    }

    private fun setupAddButton() {
        binding.ivUpdateTeamWinningHistoryAdd.setOnClickListener {
            (binding.rvUpdateTeamWinningHistory.adapter as WinningHistoryRVA).enterAddMode()
        }
    }

    private fun setTeamName(teamName: String) {
        binding.tvUpdateTeamWinningHistoryTeamName.text = teamName
    }
}
package umc.everyones.everyoneslckmanage.presentation.team

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoTournamentResultBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoWinningHistoryBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class UpdateTeamInfoTournamentResultFragment : BaseFragment<FragmentUpdateTeamInfoTournamentResultBinding>(R.layout.fragment_update_team_info_tournament_result) {

    private val viewModel: UpdateTeamInfoTournamentResultViewmodel by viewModels()

    private val navigator by lazy {
        findNavController()
    }


    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.tournamentResultList.collect { list ->
                (binding.rvUpdateTeamTournamentResult.adapter as TournamentResultRVA).submitList(list)
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
        binding.rvUpdateTeamTournamentResult.layoutManager = LinearLayoutManager(context)
        binding.rvUpdateTeamTournamentResult.adapter = TournamentResultRVA(
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
        binding.ivUpdateTeamTournamentResultPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoTournamentResultFragmentDirections
                .actionUpdateTeamInfoTournamentResultFragmentToUpdateTeamInfoDetailFragment(
                    teamName = teamName
                )
            navigator.navigate(action)
        }
    }

    private fun setupAddButton() {
        binding.ivUpdateTeamTournamentResultAdd.setOnSingleClickListener {
            (binding.rvUpdateTeamTournamentResult.adapter as TournamentResultRVA).enterAddMode()
        }
    }

    private fun setTeamName(teamName: String) {
        binding.tvUpdateTeamTournamentResultTeamName.text = teamName
    }
}
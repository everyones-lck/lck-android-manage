package umc.everyones.everyoneslckmanage.presentation.ranking

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateRankingEditBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment

class UpdateRankingEditFragment : BaseFragment<FragmentUpdateRankingEditBinding>(R.layout.fragment_update_ranking_edit) {

    private val viewModel: UpdateRankingViewModel by activityViewModels()
    private val args: UpdateRankingEditFragmentArgs by navArgs()
    private val navigator by lazy {
        findNavController()
    }
    override fun initObserver() {
        val teamName = args.teamName
        lifecycleScope.launchWhenStarted {
            viewModel.teams.collect { teams ->
                val team = teams.find { it.name == teamName }
                team?.let {
                    binding.tvUpdateRankingEditTeamName.text = it.name
                    binding.tvUpdateRankingEditWinNumber.text = it.win.toString()
                    binding.tvUpdateRankingEditLoseNumber.text = it.lose.toString()
                    binding.tvUpdateRankingEditWinningPoint.text = it.points.toString()
                }
            }
        }
    }

    override fun initView() {
        val rank = args.rank
        binding.tvUpdateRankingEditNumber.text = rank.toString()

        binding.ivUpdateRankingEditPrevious.setOnClickListener {
            handleBackPress()
        }
    }
    private fun handleBackPress() {
        val editedWin = binding.etUpdateRankingEditWinNumber.text.toString().toIntOrNull() ?: 0
        val editedLose = binding.etUpdateRankingEditLoseNumber.text.toString().toIntOrNull() ?: 0
        val editedPoints = binding.etUpdateRankingEditWinningPoint.text.toString().toIntOrNull() ?: 0
        val teamName = binding.tvUpdateRankingEditTeamName.text.toString()

        viewModel.updateTeamData(teamName, editedWin, editedLose, editedPoints)

        navigator.navigateUp()
    }
}
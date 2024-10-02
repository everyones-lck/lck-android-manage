package umc.everyones.everyoneslckmanage.presentation.ranking

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentInputMatchInfoBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateRankingBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment

class UpdateRankingFragment : BaseFragment<FragmentUpdateRankingBinding>(R.layout.fragment_update_ranking) {

    private val viewModel: UpdateRankingViewModel by activityViewModels()
    private val navigator by lazy {
        findNavController()
    }
    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.teams.collect { teams ->
                val rankedTeams = calculateRanking(teams)
                val rankingAdapter = RankingRVA(rankedTeams) { team, rank ->
                    val action = UpdateRankingFragmentDirections
                        .actionUpdateRankingFragmentToUpdateRankingEditFragment(rank,team.name)
                    navigator.navigate(action)
                }
                binding.rvUpdateRanking.adapter = rankingAdapter
            }
        }
    }

    override fun initView() {
        binding.rvUpdateRanking.layoutManager = LinearLayoutManager(requireContext())

        binding.ivUpdateRankingPrevious.setOnClickListener {
            val action = UpdateRankingFragmentDirections
                .actionUpdateRankingFragmentToHomeFragment()
            navigator.navigate(action)
        }
    }

    fun calculateRanking(teams: List<Ranking>): List<Pair<Ranking, Int>> {
        val sortedTeams = teams.sortedWith(
            compareByDescending<Ranking> { it.win }
                .thenBy { it.lose }
                .thenByDescending { it.points }
        )

        val rankingList = mutableListOf<Pair<Ranking, Int>>()
        var currentRank = 1

        for (i in sortedTeams.indices) {
            if (i > 0) {
                val previousTeam = sortedTeams[i - 1]
                val currentTeam = sortedTeams[i]

                if (previousTeam.win == currentTeam.win &&
                    previousTeam.lose == currentTeam.lose &&
                    previousTeam.points == currentTeam.points) {
                    rankingList.add(currentTeam to currentRank)
                } else {
                    currentRank = i + 1
                    rankingList.add(currentTeam to currentRank)
                }
            } else {
                rankingList.add(sortedTeams[i] to currentRank)
            }
        }

        return rankingList
    }
}

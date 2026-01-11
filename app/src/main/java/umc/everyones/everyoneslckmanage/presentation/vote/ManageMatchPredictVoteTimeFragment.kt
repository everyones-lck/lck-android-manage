package umc.everyones.everyoneslckmanage.presentation.vote

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentManageMatchPredictVoteBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.presentation.match.InputMatchResultViewModel
import umc.everyones.everyoneslckmanage.presentation.vote.adapter.VoteMatchInfoRVA
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import kotlin.getValue

@AndroidEntryPoint
class ManageMatchPredictVoteTimeFragment : BaseFragment<FragmentManageMatchPredictVoteBinding>(R.layout.fragment_manage_match_predict_vote) {
    private val matchResultViewModel: InputMatchResultViewModel by activityViewModels()
    private val viewModel: VoteViewModel by activityViewModels()
    private lateinit var adapter: VoteMatchInfoRVA
    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            matchResultViewModel.matchInfoList.collect { matchList ->
                adapter.submitList(matchList)

                // matchId 별 세트 결과 API 호출
                matchList.forEach { match ->
                    matchResultViewModel.fetchSetResultInfo(match.matchId)
                }
            }
        }
    }

    override fun initView() {
        goBackButton()
        setupRecyclerView()
        matchResultViewModel.fetchMatchInfo()
    }

    private fun setupRecyclerView() {
        adapter = VoteMatchInfoRVA { selectedMatch ->
            viewModel.updateSelectedMatch(selectedMatch)
            findNavController().navigate(R.id.action_manageMatchPrediction_to_manageManageMatchPredictionTimeFragment)
        }
        binding.rvManagePredictionContainer.adapter = adapter
        binding.rvManagePredictionContainer.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun goBackButton() {
        binding.ivManagePredictionBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }
}
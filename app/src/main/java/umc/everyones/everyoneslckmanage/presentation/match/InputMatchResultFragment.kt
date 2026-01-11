package umc.everyones.everyoneslckmanage.presentation.match

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentInputMatchResultBinding
import umc.everyones.everyoneslckmanage.domain.model.response.match.LckMatchDetailsModel
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.presentation.match.adapter.MatchResultRVA
import umc.everyones.everyoneslckmanage.util.extension.getFormattedDate
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.util.Calendar

@AndroidEntryPoint
class InputMatchResultFragment : BaseFragment<FragmentInputMatchResultBinding>(R.layout.fragment_input_match_result) {
    private val matchResultViewModel: InputMatchResultViewModel by activityViewModels()
    private lateinit var adapter: MatchResultRVA

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
        // 세트 결과 업데이트
        viewLifecycleOwner.repeatOnStarted {
            matchResultViewModel.setResultInfoMap.collect { resultMap ->
                adapter.updateMatchWithSetResults(resultMap)
            }
        }
    }

    override fun initView() {
        goBackButton()
        setupRecyclerView()
        matchResultViewModel.fetchMatchInfo()
    }

    private fun goBackButton() {
        binding.ivSetResultBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        adapter = MatchResultRVA { selectedMatch ->
            matchResultViewModel.updateSelectedMatch(selectedMatch)
            findNavController().navigate(R.id.action_inputMatchResultFragment_to_inputSetResultFragment)
        }
        binding.rvSetResultContainer.adapter = adapter
        binding.rvSetResultContainer.layoutManager = LinearLayoutManager(requireContext())
    }

}
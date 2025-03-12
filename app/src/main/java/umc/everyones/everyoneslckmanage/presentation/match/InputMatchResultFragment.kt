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
    private val matchInfoViewModel: InputMatchInfoViewModel by activityViewModels()
    private val matchResultViewModel: InputMatchResultViewModel by activityViewModels()
    private lateinit var adapter: MatchResultRVA
    private var selectedDate: Calendar = Calendar.getInstance()

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            matchInfoViewModel.matchDetails.collect { matchDetails ->
                if (matchDetails != null) {
                    updateMatchDetails(matchDetails)
                } else {
                    Timber.tag("initObserver").e("Failed to fetch match details")
                }
            }
        }
    }

    override fun initView() {
        goBackButton()
        setupRecyclerView()

        val today = Calendar.getInstance().getFormattedDate()
        matchInfoViewModel.updateSelectedDate(today)
        matchInfoViewModel.fetchLckMatchDetails(today)
    }

    private fun goBackButton() {
        binding.ivSetResultBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }

    private fun updateMatchDetails(matchDetailsModel: LckMatchDetailsModel) {
        val formattedDate = selectedDate.getFormattedDate()
        val matchDetailList = matchDetailsModel.matchByDateList
            .flatMap { it.matchDetailList }
            .filter { it.matchDate == formattedDate }
            .sortedBy { it.matchDate }
        adapter.submitList(matchDetailList)
    }


    private fun setupRecyclerView() {
        adapter = MatchResultRVA { selectedMatch ->
            matchInfoViewModel.updateSelectedMatch(selectedMatch)
            findNavController().navigate(R.id.action_inputMatchResultFragment_to_inputSetResultFragment)
        }
        binding.rvSetResultContainer.adapter = adapter
        binding.rvSetResultContainer.layoutManager = LinearLayoutManager(requireContext())
    }
}
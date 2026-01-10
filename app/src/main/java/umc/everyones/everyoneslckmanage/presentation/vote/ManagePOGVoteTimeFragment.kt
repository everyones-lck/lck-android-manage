package umc.everyones.everyoneslckmanage.presentation.vote

import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentInputMatchInfoBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentManagePogVoteBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.presentation.match.InputMatchResultViewModel
import umc.everyones.everyoneslckmanage.presentation.vote.adapter.VoteMatchInfoRVA
import umc.everyones.everyoneslckmanage.presentation.vote.adapter.VotePogInfoRVA
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import kotlin.getValue

@AndroidEntryPoint
class ManagePOGVoteTimeFragment : BaseFragment<FragmentManagePogVoteBinding>(R.layout.fragment_manage_pog_vote) {
    private val matchResultViewModel: InputMatchResultViewModel by activityViewModels()
    private val viewModel: VoteViewModel by activityViewModels()
    private lateinit var adapter: VotePogInfoRVA
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
        adapter = VotePogInfoRVA { selectedMatch ->
            viewModel.updateSelectedMatch(selectedMatch)
            findNavController().navigate(R.id.action_managePogVoteTimeFragment_to_managePogTimeFragment)
        }
        binding.rvManagePogContainer.adapter = adapter
        binding.rvManagePogContainer.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun goBackButton() {
        binding.ivManagePogBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }
}
package umc.everyones.everyoneslckmanage.presentation.vote

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentManagePredicitonTimeBinding
import umc.everyones.everyoneslckmanage.domain.model.request.match.CloseMatchModel
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import kotlin.getValue

@AndroidEntryPoint
class ManagePredictionTimeFragment: BaseFragment<FragmentManagePredicitonTimeBinding>(R.layout.fragment_manage_prediciton_time) {
    private val viewModel: VoteViewModel by activityViewModels()
    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.selectedMatch.collect { match ->
                if (match == null) return@collect

                binding.tvManagePredictionTeam1.text = match.team1Name
                binding.tvManagePredictionTeam2.text = match.team2Name
                binding.tvManagePredictionDate.text = match.matchDate.substring(11, 16)
                binding.tvManagePredictionTime.text = match.matchDate.dropLast(3)

                val matchId = match.matchId

                binding.tvManagePredictionClose.setOnSingleClickListener {
                    viewModel.fetchCloseMatch(CloseMatchModel(matchId = matchId))
                }

                binding.tvMatchIdTest.text = matchId.toString()
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.closeMatchResult.collect { result ->
                result ?: return@collect

                result.onSuccess {
                    Toast.makeText(requireContext(), "투표가 종료되었습니다.", Toast.LENGTH_SHORT).show()
                    viewModel.clearCloseResults()
                }.onFailure { e ->
                    Toast.makeText(requireContext(), "투표 종료 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                    viewModel.clearCloseResults()
                }
            }
        }
    }

    override fun initView() {
        goBackButton()
    }

    private fun goBackButton() {
        binding.ivManagePredictionTimeBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
        binding.ivManagePredictionCheckBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }
}
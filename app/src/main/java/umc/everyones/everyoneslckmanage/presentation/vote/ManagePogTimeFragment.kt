package umc.everyones.everyoneslckmanage.presentation.vote

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentManagePogTimeBinding
import umc.everyones.everyoneslckmanage.domain.model.request.match.CloseMatchModel
import umc.everyones.everyoneslckmanage.domain.model.request.match.CloseSetModel
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import kotlin.getValue

@AndroidEntryPoint
class ManagePogTimeFragment: BaseFragment<FragmentManagePogTimeBinding>(R.layout.fragment_manage_pog_time) {
    private val viewModel: VoteViewModel by activityViewModels()
    private var lastClosedSetNumber: Int? = null

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.selectedMatch.collect { match ->
                if (match == null) return@collect

                binding.tvManagePogTeam1.text = match.team1Name
                binding.tvManagePogTeam2.text = match.team2Name
                binding.tvManagePogDate.text = match.matchDate.substring(11, 16)
                binding.tvManagePogTime.text = match.matchDate.dropLast(3)

                binding.tvManagePogCloseSet1.setOnSingleClickListener {
                    lastClosedSetNumber = 1
                    viewModel.fetchCloseSets(CloseSetModel(1))
                }
                binding.tvManagePogCloseSet2.setOnSingleClickListener {
                    lastClosedSetNumber = 2
                    viewModel.fetchCloseSets(CloseSetModel(2))
                }
                binding.tvManagePogCloseSet3.setOnSingleClickListener {
                    lastClosedSetNumber = 3
                    viewModel.fetchCloseSets(CloseSetModel(3))
                }
                binding.tvManagePogCloseSet4.setOnSingleClickListener {
                    lastClosedSetNumber = 4
                    viewModel.fetchCloseSets(CloseSetModel(4))
                }
                binding.tvManagePogCloseSet5.setOnSingleClickListener {
                    lastClosedSetNumber = 5
                    viewModel.fetchCloseSets(CloseSetModel(5))
                }
                val matchId = match.matchId
                binding.tvManagePogCloseMatch.setOnSingleClickListener {
                    viewModel.fetchCloseMatchPog(CloseMatchModel(matchId))
                }

                binding.tvMatchIdTest.text = matchId.toString()
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.closeSetResult.collect { result ->
                result ?: return@collect

                result.onSuccess {
                    val n = lastClosedSetNumber
                    Toast.makeText(requireContext(), if (n != null) "${n}세트 투표가 종료되었습니다." else "투표가 종료되었습니다.", Toast.LENGTH_SHORT).show()
                    viewModel.clearCloseResults()
                }.onFailure { e ->
                    Toast.makeText(requireContext(), "투표 종료 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                    viewModel.clearCloseResults()
                }
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.closeMatchPogResult.collect { result ->
                result ?: return@collect

                result.onSuccess {
                    Toast.makeText(requireContext(), "매치 POG 투표가 종료되었습니다.", Toast.LENGTH_SHORT).show()
                    viewModel.clearCloseResults()
                }.onFailure { e ->
                    Toast.makeText(requireContext(), "매치 POG 종료 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                    viewModel.clearCloseResults()
                }
            }
        }
    }

    override fun initView() {
        goBackButton()
    }

    private fun goBackButton() {
        binding.ivManagePogTimeBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
        binding.ivManagePogCheckBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }
}
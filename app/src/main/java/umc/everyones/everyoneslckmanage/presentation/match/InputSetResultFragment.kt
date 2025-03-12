package umc.everyones.everyoneslckmanage.presentation.match

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentInputSetResultBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class InputSetResultFragment:BaseFragment<FragmentInputSetResultBinding>(R.layout.fragment_input_set_result) {
    private val matchInfoViewModel: InputMatchInfoViewModel by activityViewModels()
    private val viewModel: InputSetResultViewModel by activityViewModels()
    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            matchInfoViewModel.selectedMatch.collect { selectedMatch ->
                selectedMatch?.let { match ->
                    binding.tvMatchResultMatchTitle.text = match.seasonTitle
                    binding.tvMatchResultMatchDate.text = match.matchDate
                    binding.tvMatchResultMatchTime.text = match.matchTime
                    binding.btnMatchResultTeam1.text = match.team1Name
                    binding.btnMatchResultTeam2.text = match.team2Name
                }
            }
        }
    }

    override fun initView() {
        goBackButton()
        setupResultSubmission()
    }

    private fun goBackButton() {
        binding.ivMatchResultBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupResultSubmission() {
        binding.tvManagePogCloseSet2.setOnSingleClickListener {
            // 라디오 그룹에서 선택된 라디오 버튼 ID를 확인합니다.
            val selectedRadioId = binding.rgMatchResultWinner.checkedRadioButtonId
            // 선택된 라디오 버튼에 따라 팀 이름 결정
            val winnerTeamName = when(selectedRadioId) {
                R.id.btn_match_result_team1 -> binding.btnMatchResultTeam1.text.toString()
                R.id.btn_match_result_team2 -> binding.btnMatchResultTeam2.text.toString()
                else -> null
            }

            if (winnerTeamName.isNullOrBlank()) {
                Toast.makeText(requireContext(), "승리 팀을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnSingleClickListener
            }

            // 팀 매핑 정보: teamList (Key: 팀 이름, Value: teamId)
            val teamList = mapOf(
                "empty" to 1,
                "GEN" to 2,
                "HLE" to 3,
                "DK" to 4,
                "T1" to 5,
                "KT" to 6,
                "KDF" to 7,
                "BNK" to 8,
                "NS" to 9,
                "DRX" to 10,
                "BRO" to 11,
                "BLG" to 12,
                "LGD" to 13,
                "RA" to 14,
                "EDG" to 15,
                "TL" to 16,
                "Sengoku" to 17,
                "WE" to 18
            )
            val winnerTeamId = teamList[winnerTeamName] ?: run {
                Toast.makeText(requireContext(), "선택한 팀의 정보가 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnSingleClickListener
            }

            // 공유 ViewModel에서 선택된 매치 정보 가져오기 (예시: matchNumber가 포함되어 있다고 가정)
            val selectedMatch = matchInfoViewModel.selectedMatch.value
            if (selectedMatch == null) {
                Toast.makeText(requireContext(), "매치 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnSingleClickListener
            }

            val matchId = selectedMatch.matchNumber.toLong()  // matchNumber를 Long으로 변환

            // 이제 ViewModel의 submitMatchResult() 함수로 API 요청을 보냅니다.
            viewModel.fetchMatchResult(matchId, winnerTeamId)
        }
    }
}
package umc.everyones.everyoneslckmanage.presentation.match

import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentInputSetResultBinding
import umc.everyones.everyoneslckmanage.domain.model.response.match.SetResultInfoResponseModel
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.presentation.match.adapter.SetResultRVA
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class InputSetResultFragment:BaseFragment<FragmentInputSetResultBinding>(R.layout.fragment_input_set_result) {
    private val matchResultViewModel: InputMatchResultViewModel by activityViewModels()
    private val viewModel: InputSetResultViewModel by activityViewModels()
    private lateinit var adapter: SetResultRVA
    private val setInfoList = mutableListOf<SetResultInfoResponseModel.SetsInformationModel>() // 세트 리스트 저장
    private val selectedWinners = mutableMapOf<Int, String>() // 선택한 승리 팀 저장

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            matchResultViewModel.selectedMatch.collect { selectedMatch ->
                selectedMatch?.let { match ->
                    binding.tvMatchResultMatchTitle.text = "LCK ${match.seasonInfo}"
                    binding.tvMatchResultMatchDate.text = match.matchDate.substring(0, 10)
                    binding.tvMatchResultMatchTime.text = match.matchDate.substring(11, 16)
                    binding.btnMatchResultTeam1.text = match.team1Name
                    binding.btnMatchResultTeam2.text = match.team2Name

                    viewModel.fetchSetResultInfo(match.matchId)
                }
            }
        }
        viewLifecycleOwner.repeatOnStarted {
            viewModel.setResultInfo.collect { setResultInfo ->
                setResultInfo?.let {
                    setInfoList.clear()
                    setInfoList.addAll(it.setsInformation) // 기존 세트 정보 추가
                    adapter.submitList(setInfoList.toList()) // 변경된 데이터 반영
                }
            }
        }
    }

    override fun initView() {
        goBackButton()
        setupRecyclerView()
        setupResultSubmission()
        setupAddSetButton()
    }

    private fun goBackButton() {
        binding.ivMatchResultBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupRecyclerView() {
        val matchId = matchResultViewModel.selectedMatch.value?.matchId
        if (matchId == null) {
            Toast.makeText(requireContext(), "매치 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        adapter = SetResultRVA(
            matchId = matchId,
            onRadioButtonClick = { setIndex, winnerTeam ->
                selectedWinners[setIndex] = winnerTeam
            },
            onSubmitSetResult = { setResult ->
                lifecycleScope.launch {
                    runCatching {
                        viewModel.fetchSetResults(setResult)
                    }.onSuccess {
                        Toast.makeText(requireContext(), "${setResult.setIndex} 세트 결과가 성공적으로 등록되었습니다!", Toast.LENGTH_SHORT).show()
                    }.onFailure {
                        Toast.makeText(requireContext(), "${setResult.setIndex} 세트 결과 등록에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
        binding.rvSetResultContainer.adapter = adapter
        binding.rvSetResultContainer.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupAddSetButton() {
        binding.ivSetResultAddBtn.setOnClickListener {
            val selectedMatch = matchResultViewModel.selectedMatch.value
            if (selectedMatch == null) {
                Toast.makeText(requireContext(), "매치 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newSetIndex = setInfoList.size + 1 // 기존 개수 + 1
            val newSet = SetResultInfoResponseModel.SetsInformationModel(
                setIndex = newSetIndex,
                winnerTeam = selectedMatch.team1Name, // 기본적으로 팀1을 승자로 설정
                loserTeam = selectedMatch.team2Name
            )

            setInfoList.add(newSet) // 리스트에 추가
            adapter.submitList(setInfoList.toList()) // UI 업데이트
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
            val selectedMatch = matchResultViewModel.selectedMatch.value
            if (selectedMatch == null) {
                Toast.makeText(requireContext(), "매치 정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show()
                return@setOnSingleClickListener
            }

            val matchId = selectedMatch.matchId

            lifecycleScope.launch {
                runCatching {
                    viewModel.fetchMatchResult(matchId, winnerTeamId)
                }.onSuccess {
                    Toast.makeText(requireContext(), "매치 결과가 성공적으로 등록되었습니다!", Toast.LENGTH_SHORT).show()
                }.onFailure {
                    Toast.makeText(requireContext(), "매치 결과 등록에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
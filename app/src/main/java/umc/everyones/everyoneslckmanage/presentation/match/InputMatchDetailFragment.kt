package umc.everyones.everyoneslckmanage.presentation.match

import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentInputMatchDetailBinding
import umc.everyones.everyoneslckmanage.domain.model.request.match.InputMatchModel
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.presentation.match.adapter.MatchSpinnerAdapter
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class InputMatchDetailFragment : BaseFragment<FragmentInputMatchDetailBinding>(R.layout.fragment_input_match_detail) {
    private val matchInfoViewModel: InputMatchInfoViewModel by activityViewModels()
    private val viewModel: InputMatchDetailViewModel by activityViewModels()

    override fun initObserver() {
        observeSubmitResult()
    }

    override fun initView() {
        goBackButton()
        initSpinnerAdapter()
        observeSelectedDate()
        setupSubmitButton()
    }

    private fun goBackButton() {
        binding.ivMatchDetailBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }

    private fun observeSelectedDate() {
        viewLifecycleOwner.repeatOnStarted {
            matchInfoViewModel.selectedDate.collect { date ->
                date?.let {
                    binding.tvMatchInfoDate.text = it // 날짜 업데이트
                }
            }
        }
    }

    private fun setupSubmitButton() {
        binding.ivInputMatchInfoConfirm.setOnSingleClickListener {
            val team1Name = binding.spinnerInputMatchTeam1.selectedItem.toString()
            val team2Name = binding.spinnerInputMatchTeam2.selectedItem.toString()

            val team1Id = teamList[team1Name] ?: run {
                Toast.makeText(requireContext(), "팀1을 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnSingleClickListener
            }

            val team2Id = teamList[team2Name] ?: run {
                Toast.makeText(requireContext(), "팀2를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnSingleClickListener
            }

            val year = binding.tvMatchYear.text.toString().trim()
            val seasonName = binding.spinnerInputMatchSeason.selectedItem.toString()
            val season = if (year.isNotBlank()) "$year $seasonName" else seasonName
            if (season.isBlank()) {
                Toast.makeText(requireContext(), "시즌을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnSingleClickListener
            }

            val matchNumber = binding.tvMatchNumber.text.toString().toLongOrNull() ?: 0L
            val matchDate = createMatchDate()
            if (matchDate == null) {
                Toast.makeText(requireContext(), "날짜와 시간을 올바르게 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnSingleClickListener
            }

            viewModel.fetchInputMatch(team1Id, team2Id, season, matchNumber, matchDate)
        }
    }

    private fun createMatchDate(): String? {
        val date = binding.tvMatchInfoDate.text.toString() // yyyy-MM-dd 형식
        val time = binding.etInputMatchTimeResult.text.toString() // HH:mm 형식

        if (date.isBlank() || time.isBlank()) {
            return null // 날짜나 시간이 비어있으면 null 반환
        }

        return "$date $time"
    }


    private fun observeSubmitResult() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.submitResult.collect { result ->
                result?.onSuccess {
                    Toast.makeText(requireContext(), "매치 정보가 성공적으로 전송되었습니다.", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                    viewModel.resetSubmitResult() // 상태 초기화
                }?.onFailure { e ->
                    Toast.makeText(requireContext(), "전송 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                    viewModel.resetSubmitResult() // 상태 초기화
                }
            }
        }
    }

    private fun initSpinnerAdapter() {
        val teamNames = teamList.keys.toList() // teamMap에서 팀 이름 리스트 추출

        binding.spinnerInputMatchSeason.adapter = MatchSpinnerAdapter(requireContext(), seasonList)
        binding.spinnerInputMatchTeam1.adapter = MatchSpinnerAdapter(requireContext(), teamNames)
        binding.spinnerInputMatchTeam2.adapter = MatchSpinnerAdapter(requireContext(), teamNames)
    }

    companion object {
        private val teamList = mapOf(
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
        private val seasonList = listOf("Cup", "Spring", "Summer", "MSI", "EWC", "Worlds", "KeSPA Cup", "LPL Summer")
    }

}
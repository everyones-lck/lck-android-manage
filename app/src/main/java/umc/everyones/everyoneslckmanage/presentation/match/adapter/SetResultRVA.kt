package umc.everyones.everyoneslckmanage.presentation.match.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.ItemInputSetResultBinding
import umc.everyones.everyoneslckmanage.domain.model.request.match.SetResultModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.SetResultInfoResponseModel

class SetResultRVA(
    private val matchId: Long,
    private val onRadioButtonClick: (setIndex: Int, winnerTeam: String) -> Unit,
    private val onSubmitSetResult: (SetResultModel) -> Unit
) : ListAdapter<SetResultInfoResponseModel.SetsInformationModel, SetResultRVA.ViewHolder>(DiffCallback()) {
    private val selectedWinners = mutableMapOf<Int, String>() // 선택된 승리 팀 저장

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInputSetResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ViewHolder(private val binding: ItemInputSetResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SetResultInfoResponseModel.SetsInformationModel) {
            binding.tvSetResultWin.text = "${item.setIndex} 세트"

            // RadioButton에 team1과 team2 매칭
            binding.btnSetResultTeam1.text = item.winnerTeam
            binding.btnSetResultTeam2.text = item.loserTeam

            // 기존 리스너 제거 후 체크 설정
            binding.rgSetResultWinner.setOnCheckedChangeListener(null)

            // API에서 받은 데이터 기준으로 자동 선택
            if (selectedWinners.containsKey(item.setIndex)) {
                // 기존에 사용자가 선택한 값이 있다면 유지
                when (selectedWinners[item.setIndex]) {
                    item.winnerTeam -> binding.btnSetResultTeam1.isChecked = true
                    item.loserTeam -> binding.btnSetResultTeam2.isChecked = true
                }
            } else {
                // API에서 받은 값 기준으로 자동 선택
                binding.btnSetResultTeam1.isChecked = item.winnerTeam == binding.btnSetResultTeam1.text.toString()
                binding.btnSetResultTeam2.isChecked = item.winnerTeam == binding.btnSetResultTeam2.text.toString()
            }

            binding.rgSetResultWinner.setOnCheckedChangeListener { _, checkedId ->
                val selectedTeam = when (checkedId) {
                    R.id.btn_set_result_team1 -> item.winnerTeam
                    R.id.btn_set_result_team2 -> item.loserTeam
                    else -> null
                }
                selectedTeam?.let {
                    selectedWinners[item.setIndex] = it
                    onRadioButtonClick(item.setIndex, it)
                }

                binding.tvSetResultSend.setOnClickListener {
                    val selectedWinner = selectedWinners[item.setIndex]

                    if (selectedWinner == null) {
                        Toast.makeText(binding.root.context, "${item.setIndex} 세트의 승리 팀을 선택해주세요.", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }

                    val teamList = mapOf(
                        "empty" to 1, "GEN" to 2, "HLE" to 3, "DK" to 4, "T1" to 5, "KT" to 6,
                        "KDF" to 7, "BNK" to 8, "NS" to 9, "DRX" to 10, "BRO" to 11, "BLG" to 12,
                        "LGD" to 13, "RA" to 14, "EDG" to 15, "TL" to 16, "Sengoku" to 17, "WE" to 18
                    )

                    val winnerTeamId = teamList[selectedWinner] ?: 1
                    val loserTeamId = teamList[if (selectedWinner == item.winnerTeam) item.loserTeam else item.winnerTeam] ?: 1

                    val setResult = SetResultModel(matchId, item.setIndex, winnerTeamId, loserTeamId)

                    // API 호출
                    onSubmitSetResult(setResult)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<SetResultInfoResponseModel.SetsInformationModel>() {
        override fun areItemsTheSame(oldItem: SetResultInfoResponseModel.SetsInformationModel, newItem: SetResultInfoResponseModel.SetsInformationModel): Boolean {
            return oldItem.setIndex == newItem.setIndex
        }

        override fun areContentsTheSame(oldItem: SetResultInfoResponseModel.SetsInformationModel, newItem: SetResultInfoResponseModel.SetsInformationModel): Boolean {
            return oldItem == newItem
        }
    }
}
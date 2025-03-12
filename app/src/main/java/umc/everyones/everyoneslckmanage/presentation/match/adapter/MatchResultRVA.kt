package umc.everyones.everyoneslckmanage.presentation.match.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.everyoneslckmanage.databinding.ItemInputMatchResultBinding
import umc.everyones.everyoneslckmanage.domain.model.response.match.MatchInfoModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.SetResultInfoResponseModel

class MatchResultRVA(
    private val onItemClick: (MatchInfoModel.MatchResponsesModel) -> Unit
): ListAdapter<MatchInfoModel.MatchResponsesModel, MatchResultRVA.ViewHolder>(DiffCallback()) {
    private var setResultMap: Map<Long, SetResultInfoResponseModel> = emptyMap()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInputMatchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        val setResults = setResultMap[item.matchId] // 해당 matchId에 대한 세트 결과 가져오기
        holder.bind(item, setResults)
        holder.itemView.setOnClickListener {
            onItemClick(item)  // 클릭한 아이템 전달
        }
    }

    fun updateMatchWithSetResults(setResults: Map<Long, SetResultInfoResponseModel>) {
        this.setResultMap = setResults
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemInputMatchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MatchInfoModel.MatchResponsesModel, setResultInfo: SetResultInfoResponseModel?) {
            binding.tvMatchResultTitle.text = "LCK ${item.seasonInfo}"
            binding.tvMatchResultDate.text = item.matchDate.substring(0, 10)
            binding.tvMatchResultTime.text = item.matchDate.substring(11, 16)

            // 세트 승리 정보 업데이트
            val sets = setResultInfo?.setsInformation ?: emptyList()
            val winnerTeams = sets.map { it.winnerTeam }

            // UI에 세트 결과 적용
            val setTextViews = listOf(
                binding.tvMatchSet1Team,
                binding.tvMatchSet2Team,
                binding.tvMatchSet3Team,
                binding.tvMatchSet4Team,
                binding.tvMatchSet5Team
            )

            val dividerViews = listOf(
                binding.tvMatchDivideSet1,
                binding.tvMatchDivideSet2,
                binding.tvMatchDivideSet3,
                binding.tvMatchDivideSet4
            )

            // 세트 데이터 적용
            setTextViews.forEachIndexed { index, textView ->
                if (index < winnerTeams.size) {
                    textView.text = winnerTeams[index]
                    textView.visibility = View.VISIBLE
                } else {
                    textView.visibility = View.GONE
                }
            }

            // 나머지 Divider들 처리
            dividerViews.forEachIndexed { index, divider ->
                divider.visibility = if (index < winnerTeams.size - 1) View.VISIBLE else View.GONE
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<MatchInfoModel.MatchResponsesModel>() {
        override fun areItemsTheSame(oldItem: MatchInfoModel.MatchResponsesModel, newItem: MatchInfoModel.MatchResponsesModel): Boolean {
            return oldItem.matchId == newItem.matchId
        }


        override fun areContentsTheSame(oldItem: MatchInfoModel.MatchResponsesModel, newItem: MatchInfoModel.MatchResponsesModel): Boolean {
            return oldItem == newItem

        }

    }
}
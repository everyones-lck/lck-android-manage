package umc.everyones.everyoneslckmanage.presentation.vote.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.everyoneslckmanage.databinding.ItemManagePredictionBinding
import umc.everyones.everyoneslckmanage.domain.model.response.match.MatchInfoModel

class VoteMatchInfoRVA (
    private val onItemClick: (MatchInfoModel.MatchResponsesModel) -> Unit
): ListAdapter<MatchInfoModel.MatchResponsesModel, VoteMatchInfoRVA.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemManagePredictionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
        holder.itemView.setOnClickListener {
            onItemClick(getItem(position))  // 클릭한 아이템 전달
        }
    }

    inner class ViewHolder(private val binding: ItemManagePredictionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: MatchInfoModel.MatchResponsesModel) {
            binding.tvManagePogTitle.text = "LCK ${item.seasonInfo}"
            binding.tvManagePogDate.text = item.matchDate.substring(11, 16)
            binding.tvManagePogTime.text = item.matchDate.dropLast(3)
            binding.tvManagePogTeam1.text = item.team1Name
            binding.tvManagePogTeam2.text = item.team2Name
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
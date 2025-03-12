package umc.everyones.everyoneslckmanage.presentation.match.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.everyoneslckmanage.databinding.ItemInputMatchResultBinding
import umc.everyones.everyoneslckmanage.domain.model.match.SelectedMatch
import umc.everyones.everyoneslckmanage.domain.model.response.match.LckMatchDetailsModel

class MatchResultRVA(
//    private val onItemClick: (LckMatchDetailsModel.LckMatchDetailsElementModel) -> Unit
    private val onItemClick: (SelectedMatch) -> Unit
): ListAdapter<LckMatchDetailsModel.LckMatchDetailsElementModel, MatchResultRVA.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemInputMatchResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            val selectedMatch = SelectedMatch(
                matchNumber = item.matchNumber.toLong(),
                seasonTitle = "LCK ${item.season}",
                matchDate = item.matchDate,
                matchTime = item.matchTime.dropLast(3),
                team1Name = item.team1.teamName,
                team2Name = item.team2.teamName
            )
            onItemClick(selectedMatch)
        }
    }

    inner class ViewHolder(private val binding: ItemInputMatchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LckMatchDetailsModel.LckMatchDetailsElementModel) {
            binding.tvMatchResultTitle.text = "LCK ${item.season}"
            binding.tvMatchResultDate.text = item.matchDate
            binding.tvMatchResultTime.text = item.matchTime.dropLast(3)
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<LckMatchDetailsModel.LckMatchDetailsElementModel>() {
        override fun areItemsTheSame(oldItem: LckMatchDetailsModel.LckMatchDetailsElementModel, newItem: LckMatchDetailsModel.LckMatchDetailsElementModel): Boolean {
            return oldItem.matchNumber == newItem.matchNumber && oldItem.matchDate == newItem.matchDate
        }


        override fun areContentsTheSame(oldItem: LckMatchDetailsModel.LckMatchDetailsElementModel, newItem: LckMatchDetailsModel.LckMatchDetailsElementModel): Boolean {
            return oldItem == newItem

        }

    }
}
package umc.everyones.everyoneslckmanage.presentation.match.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import umc.everyones.everyoneslckmanage.databinding.ItemMatchInfoBinding
import umc.everyones.everyoneslckmanage.domain.model.response.match.LckMatchDetailsModel
import umc.everyones.everyoneslckmanage.domain.model.response.match.MatchInfoModel
import umc.everyones.everyoneslckmanage.presentation.community.adapter.CommentRVA

class MatchInfoRVA (

): ListAdapter<LckMatchDetailsModel.LckMatchDetailsElementModel, MatchInfoRVA.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMatchInfoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemMatchInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: LckMatchDetailsModel.LckMatchDetailsElementModel) {
            binding.tvMatchInfoMatchTime.text = item.matchTime.dropLast(3)
            binding.tvMatchInfoMatchTeam1.text = item.team1.teamName
            binding.tvMatchInfoMatchTeam2.text = item.team2.teamName
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<LckMatchDetailsModel.LckMatchDetailsElementModel>() {
        override fun areItemsTheSame(oldItem: LckMatchDetailsModel.LckMatchDetailsElementModel, newItem: LckMatchDetailsModel.LckMatchDetailsElementModel): Boolean {
            return oldItem.matchDate == newItem.matchDate
        }


        override fun areContentsTheSame(oldItem: LckMatchDetailsModel.LckMatchDetailsElementModel, newItem: LckMatchDetailsModel.LckMatchDetailsElementModel): Boolean {
            return oldItem == newItem

        }

    }
}
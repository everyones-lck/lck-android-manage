package umc.everyones.everyoneslckmanage.presentation.ranking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.everyoneslckmanage.databinding.ItemUpdateRankingBinding

class RankingRVA(
    private val teamRankings: List<Pair<Ranking, Int>>,
    private val onItemClick: (Ranking, Int) -> Unit
) : RecyclerView.Adapter<RankingRVA.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUpdateRankingBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(teamRankings[position])
    }

    override fun getItemCount(): Int = teamRankings.size

    inner class ViewHolder(val binding: ItemUpdateRankingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(teamRanking: Pair<Ranking, Int>) {
            val (team, rank) = teamRanking

            binding.tvUpdateRankingNumber.text = teamRanking.second.toString()
            binding.tvUpdateRankingTeamName.text = teamRanking.first.name
            binding.tvUpdateRankingWinNumber.text = teamRanking.first.win.toString()
            binding.tvUpdateRankingLoseNumber.text = teamRanking.first.lose.toString()
            binding.tvUpdateRankingWinningPoint.text = teamRanking.first.points.toString()

            binding.root.setOnClickListener {
                onItemClick(team, rank)
            }
        }
    }
}
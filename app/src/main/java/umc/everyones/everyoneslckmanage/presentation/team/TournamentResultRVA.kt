package umc.everyones.everyoneslckmanage.presentation.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.ItemUpdateTeamInfoTournamentResultBinding
import umc.everyones.everyoneslckmanage.databinding.ItemUpdateTeamInfoWinningHistoryBinding


class TournamentResultRVA(
    private val onAddWinningHistory: (TournamentResult) -> Unit,
    private val onSaveWinningHistory: (TournamentResult) -> Unit,
    private val onDeleteWinningHistory: (TournamentResult) -> Unit
) : RecyclerView.Adapter<TournamentResultRVA.TournamentResultViewHolder>() {

    private var tournamentResultList: MutableList<TournamentResult> = mutableListOf()
    private var editModePosition: Int = -1
    private var addMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TournamentResultViewHolder {
        val binding = ItemUpdateTeamInfoTournamentResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TournamentResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder:TournamentResultViewHolder, position: Int) {
        if (addMode && position == tournamentResultList.size) {
            holder.bindNewEntry()
        } else {
            val tournamentResult = tournamentResultList[position]
            holder.bind(tournamentResult, position)
        }
    }

    override fun getItemCount(): Int {
        return tournamentResultList.size + if (addMode) 1 else 0
    }

    fun submitList(newList: List<TournamentResult>) {
        tournamentResultList.clear()
        tournamentResultList.addAll(newList)
        notifyDataSetChanged()
    }

    fun enterAddMode() {
        addMode = true
        notifyDataSetChanged()
    }

    fun exitAddMode() {
        addMode = false
        notifyDataSetChanged()
    }

    inner class TournamentResultViewHolder(private val binding: ItemUpdateTeamInfoTournamentResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(tournamentResult: TournamentResult, position: Int) {
            with(binding) {
                tvUpdateTeamInfoTournamentResultYear.text = tournamentResult.year.toString()
                tvUpdateTeamInfoTournamentResultPlayer.text = tournamentResult.seasonName

                if (position == editModePosition) {
                    etUpdateTeamInfoTournamentResultYear.setText(tournamentResult.year.toString())
                    etUpdateTeamInfoTournamentResultPlayer.setText(tournamentResult.seasonName)

                    etUpdateTeamInfoTournamentResultYear.visibility = View.VISIBLE
                    etUpdateTeamInfoTournamentResultPlayer.visibility = View.VISIBLE
                    ivUpdateTeamInfoTournamentResultSave.visibility = View.VISIBLE

                    tvUpdateTeamInfoTournamentResultYear.visibility = View.GONE
                    tvUpdateTeamInfoTournamentResultPlayer.visibility = View.GONE
                    ivUpdateTeamInfoTournamentResultEdit.visibility = View.GONE
                    ivUpdateTeamInfoTournamentResultDelete.visibility = View.GONE
                } else {
                    etUpdateTeamInfoTournamentResultYear.visibility = View.GONE
                    etUpdateTeamInfoTournamentResultPlayer.visibility = View.GONE
                    ivUpdateTeamInfoTournamentResultSave.visibility = View.GONE

                    tvUpdateTeamInfoTournamentResultYear.visibility = View.VISIBLE
                    tvUpdateTeamInfoTournamentResultPlayer.visibility = View.VISIBLE
                    ivUpdateTeamInfoTournamentResultEdit.visibility = View.VISIBLE
                    ivUpdateTeamInfoTournamentResultDelete.visibility = View.VISIBLE
                }

                ivUpdateTeamInfoTournamentResultEdit.setOnClickListener {
                    editModePosition = position
                    notifyDataSetChanged()
                }

                ivUpdateTeamInfoTournamentResultSave.setOnClickListener {
                    val updatedTournamentResult = tournamentResult.copy(
                        year = etUpdateTeamInfoTournamentResultYear.text.toString().toInt(),
                        seasonName = etUpdateTeamInfoTournamentResultPlayer.text.toString()
                    )
                    onSaveWinningHistory(updatedTournamentResult)
                    editModePosition = -1
                    notifyDataSetChanged()
                }

                ivUpdateTeamInfoTournamentResultDelete.setOnClickListener {
                    onDeleteWinningHistory(tournamentResult)
                }
            }
        }

        fun bindNewEntry() {
            with(binding) {
                etUpdateTeamInfoTournamentResultYear.setText("")
                etUpdateTeamInfoTournamentResultPlayer.setText("")

                etUpdateTeamInfoTournamentResultYear.visibility = View.VISIBLE
                etUpdateTeamInfoTournamentResultPlayer.visibility = View.VISIBLE
                ivUpdateTeamInfoTournamentResultSave.visibility = View.VISIBLE

                tvUpdateTeamInfoTournamentResultYear.visibility = View.GONE
                tvUpdateTeamInfoTournamentResultPlayer.visibility = View.GONE
                ivUpdateTeamInfoTournamentResultEdit.visibility = View.GONE
                ivUpdateTeamInfoTournamentResultDelete.visibility = View.GONE

                ivUpdateTeamInfoTournamentResultSave.setOnClickListener {
                    val newTournamentResult = TournamentResult(
                        id = tournamentResultList.size + 1,
                        year = etUpdateTeamInfoTournamentResultYear.text.toString().toInt(),
                        seasonName = etUpdateTeamInfoTournamentResultPlayer.text.toString()
                    )
                    onAddWinningHistory(newTournamentResult)
                    exitAddMode()
                }
            }
        }
    }
}

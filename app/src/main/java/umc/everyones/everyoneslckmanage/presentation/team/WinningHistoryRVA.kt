package umc.everyones.everyoneslckmanage.presentation.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.ItemUpdateTeamInfoWinningHistoryBinding
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.util.UUID


class WinningHistoryRVA(
    private val onAddWinningHistory: (WinningHistory) -> Unit,
    private val onSaveWinningHistory: (WinningHistory) -> Unit,
    private val onDeleteWinningHistory: (WinningHistory) -> Unit,
    private val viewModel: UpdateTeamInfoWinningHistoryViewModel
) : RecyclerView.Adapter<WinningHistoryRVA.WinningHistoryViewHolder>() {

    private var winningHistoryList: MutableList<WinningHistory> = mutableListOf()
    private var editModePosition: Int = -1
    private var addMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WinningHistoryViewHolder {
        val binding = ItemUpdateTeamInfoWinningHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WinningHistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WinningHistoryViewHolder, position: Int) {
        if (addMode && position == winningHistoryList.size) {
            holder.bindNewEntry()
        } else {
            val winningHistory = winningHistoryList[position]
            holder.bind(winningHistory, position)
        }
    }

    override fun getItemCount(): Int {
        return winningHistoryList.size + if (addMode) 1 else 0
    }

    fun submitList(newList: List<WinningHistory>) {
        winningHistoryList.clear()
        winningHistoryList.addAll(newList)
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

    inner class WinningHistoryViewHolder(private val binding: ItemUpdateTeamInfoWinningHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(winningHistory: WinningHistory, position: Int) {
            with(binding) {
                tvUpdateTeamInfoWinningHistoryYear.text = winningHistory.year.toString()
                tvUpdateTeamInfoWinningHistorySeason.text = winningHistory.seasonName

                if (position == editModePosition) {
                    etUpdateTeamInfoWinningHistoryYear.setText(winningHistory.year.toString())
                    etUpdateTeamInfoWinningHistorySeason.setText(winningHistory.seasonName)

                    etUpdateTeamInfoWinningHistoryYear.visibility = View.VISIBLE
                    etUpdateTeamInfoWinningHistorySeason.visibility = View.VISIBLE
                    ivUpdateTeamInfoWinningHistorySave.visibility = View.VISIBLE

                    tvUpdateTeamInfoWinningHistoryYear.visibility = View.GONE
                    tvUpdateTeamInfoWinningHistorySeason.visibility = View.GONE
                    ivUpdateTeamInfoWinningHistoryEdit.visibility = View.GONE
                    ivUpdateTeamInfoWinningHistoryDelete.visibility = View.GONE
                } else {
                    etUpdateTeamInfoWinningHistoryYear.visibility = View.GONE
                    etUpdateTeamInfoWinningHistorySeason.visibility = View.GONE
                    ivUpdateTeamInfoWinningHistorySave.visibility = View.GONE

                    tvUpdateTeamInfoWinningHistoryYear.visibility = View.VISIBLE
                    tvUpdateTeamInfoWinningHistorySeason.visibility = View.VISIBLE
                    ivUpdateTeamInfoWinningHistoryEdit.visibility = View.VISIBLE
                    ivUpdateTeamInfoWinningHistoryDelete.visibility = View.VISIBLE
                }

                ivUpdateTeamInfoWinningHistoryEdit.setOnClickListener {
                    editModePosition = position
                    notifyDataSetChanged()
                }

                ivUpdateTeamInfoWinningHistorySave.setOnClickListener {
                    val updatedWinningHistory = winningHistory.copy(
                        year = etUpdateTeamInfoWinningHistoryYear.text.toString().toInt(),
                        seasonName = etUpdateTeamInfoWinningHistorySeason.text.toString()
                    )
                    onSaveWinningHistory(updatedWinningHistory)
                    editModePosition = -1
                    notifyDataSetChanged()
                }

                ivUpdateTeamInfoWinningHistoryDelete.setOnClickListener {
                    onDeleteWinningHistory(winningHistory)
                }
            }
        }

        fun bindNewEntry() {
            with(binding) {
                etUpdateTeamInfoWinningHistoryYear.setText("")
                etUpdateTeamInfoWinningHistorySeason.setText("")

                etUpdateTeamInfoWinningHistoryYear.visibility = View.VISIBLE
                etUpdateTeamInfoWinningHistorySeason.visibility = View.VISIBLE
                ivUpdateTeamInfoWinningHistorySave.visibility = View.VISIBLE

                tvUpdateTeamInfoWinningHistoryYear.visibility = View.GONE
                tvUpdateTeamInfoWinningHistorySeason.visibility = View.GONE
                ivUpdateTeamInfoWinningHistoryEdit.visibility = View.GONE
                ivUpdateTeamInfoWinningHistoryDelete.visibility = View.GONE

                ivUpdateTeamInfoWinningHistorySave.setOnSingleClickListener {
                    val teamName = viewModel.teamName ?: "Unknown Team"
                    val newWinningHistory = WinningHistory(
                        id = UUID.randomUUID().hashCode(),
                        year = etUpdateTeamInfoWinningHistoryYear.text.toString().toInt(),
                        seasonName = etUpdateTeamInfoWinningHistorySeason.text.toString(),
                        teamName = teamName
                    )
                    onAddWinningHistory(newWinningHistory)
                    exitAddMode()
                }
            }
        }
    }
}

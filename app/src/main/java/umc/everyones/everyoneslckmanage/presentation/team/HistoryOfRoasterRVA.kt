package umc.everyones.everyoneslckmanage.presentation.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.ItemUpdateTeamInfoHistoryOfRoasterBinding
import umc.everyones.everyoneslckmanage.databinding.ItemUpdateTeamInfoWinningHistoryBinding


class HistoryOfRoasterRVA(
    private val onAddWinningHistory: (HistoryOfRoaster) -> Unit,
    private val onSaveWinningHistory: (HistoryOfRoaster) -> Unit,
    private val onDeleteWinningHistory: (HistoryOfRoaster) -> Unit
) : RecyclerView.Adapter<HistoryOfRoasterRVA.HistoryOfRoasterViewHolder>() {

    private var historyOfRoasterList: MutableList<HistoryOfRoaster> = mutableListOf()
    private var editModePosition: Int = -1
    private var addMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryOfRoasterViewHolder {
        val binding = ItemUpdateTeamInfoHistoryOfRoasterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryOfRoasterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryOfRoasterViewHolder, position: Int) {
        if (addMode && position == historyOfRoasterList.size) {
            holder.bindNewEntry()
        } else {
            val historyOfRoaster = historyOfRoasterList[position]
            holder.bind(historyOfRoaster, position)
        }
    }

    override fun getItemCount(): Int {
        return historyOfRoasterList.size + if (addMode) 1 else 0
    }

    fun submitList(newList: List<HistoryOfRoaster>) {
        historyOfRoasterList.clear()
        historyOfRoasterList.addAll(newList)
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

    inner class HistoryOfRoasterViewHolder(private val binding: ItemUpdateTeamInfoHistoryOfRoasterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(historyOfRoaster: HistoryOfRoaster, position: Int) {
            with(binding) {
                tvUpdateTeamInfoHistoryOfRoasterYear.text =historyOfRoaster.year.toString()
                tvUpdateTeamInfoHistoryOfRoasterSeason.text = historyOfRoaster.seasonName

                if (position == editModePosition) {
                    etUpdateTeamInfoHistoryOfRoasterYear.setText(historyOfRoaster.year.toString())
                    etUpdateTeamInfoHistoryOfRoasterSeason.setText(historyOfRoaster.seasonName)

                    etUpdateTeamInfoHistoryOfRoasterYear.visibility = View.VISIBLE
                    etUpdateTeamInfoHistoryOfRoasterSeason.visibility = View.VISIBLE
                    ivUpdateTeamInfoHistoryOfRoasterSave.visibility = View.VISIBLE

                    tvUpdateTeamInfoHistoryOfRoasterYear.visibility = View.GONE
                    tvUpdateTeamInfoHistoryOfRoasterSeason.visibility = View.GONE
                    ivUpdateTeamInfoHistoryOfRoasterEdit.visibility = View.GONE
                    ivUpdateTeamInfoHistoryOfRoasterDelete.visibility = View.GONE
                } else {
                    etUpdateTeamInfoHistoryOfRoasterYear.visibility = View.GONE
                    etUpdateTeamInfoHistoryOfRoasterSeason.visibility = View.GONE
                    ivUpdateTeamInfoHistoryOfRoasterSave.visibility = View.GONE

                    tvUpdateTeamInfoHistoryOfRoasterYear.visibility = View.VISIBLE
                    tvUpdateTeamInfoHistoryOfRoasterSeason.visibility = View.VISIBLE
                    ivUpdateTeamInfoHistoryOfRoasterEdit.visibility = View.VISIBLE
                    ivUpdateTeamInfoHistoryOfRoasterDelete.visibility = View.VISIBLE
                }

                ivUpdateTeamInfoHistoryOfRoasterEdit.setOnClickListener {
                    editModePosition = position
                    notifyDataSetChanged()
                }

                ivUpdateTeamInfoHistoryOfRoasterSave.setOnClickListener {
                    val updatedWinningHistory = historyOfRoaster.copy(
                        year = etUpdateTeamInfoHistoryOfRoasterYear.text.toString().toInt(),
                        seasonName = etUpdateTeamInfoHistoryOfRoasterSeason.text.toString()
                    )
                    onSaveWinningHistory(updatedWinningHistory)
                    editModePosition = -1
                    notifyDataSetChanged()
                }

                ivUpdateTeamInfoHistoryOfRoasterDelete.setOnClickListener {
                    onDeleteWinningHistory(historyOfRoaster)
                }
            }
        }

        fun bindNewEntry() {
            with(binding) {
                etUpdateTeamInfoHistoryOfRoasterYear.setText("")
                etUpdateTeamInfoHistoryOfRoasterSeason.setText("")

                etUpdateTeamInfoHistoryOfRoasterYear.visibility = View.VISIBLE
                etUpdateTeamInfoHistoryOfRoasterSeason.visibility = View.VISIBLE
                ivUpdateTeamInfoHistoryOfRoasterSave.visibility = View.VISIBLE

                tvUpdateTeamInfoHistoryOfRoasterYear.visibility = View.GONE
                tvUpdateTeamInfoHistoryOfRoasterSeason.visibility = View.GONE
                ivUpdateTeamInfoHistoryOfRoasterEdit.visibility = View.GONE
                ivUpdateTeamInfoHistoryOfRoasterDelete.visibility = View.GONE

                ivUpdateTeamInfoHistoryOfRoasterSave.setOnClickListener {
                    val newHistoryOfRoaster = HistoryOfRoaster(
                        id = historyOfRoasterList.size + 1,
                        year = etUpdateTeamInfoHistoryOfRoasterYear.text.toString().toInt(),
                        seasonName = etUpdateTeamInfoHistoryOfRoasterSeason.text.toString()
                    )
                    onAddWinningHistory(newHistoryOfRoaster)
                    exitAddMode()
                }
            }
        }
    }
}

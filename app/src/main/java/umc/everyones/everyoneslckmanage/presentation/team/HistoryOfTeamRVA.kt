package umc.everyones.everyoneslckmanage.presentation.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.everyoneslckmanage.databinding.ItemUpdateTeamInfoLckEditOrAddBinding
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener


class HistoryOfTeamRVA(
    private val onAddHistoryTeam: (HistoryOfTeam) -> Unit,
    private val onSaveHistoryTeam: (HistoryOfTeam) -> Unit,
    private val onDeleteHistoryTeam: (HistoryOfTeam) -> Unit
) : RecyclerView.Adapter<HistoryOfTeamRVA.HistoryOfTeamViewHolder>() {

    private var historyOfTeamList: MutableList<HistoryOfTeam> = mutableListOf()
    private var editModePosition: Int = -1
    private var addMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryOfTeamViewHolder {
        val binding = ItemUpdateTeamInfoLckEditOrAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryOfTeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryOfTeamViewHolder, position: Int) {
        if (addMode && position == historyOfTeamList.size) {
            holder.bindNewEntry()
        } else {
            val HistoryOfTeam = historyOfTeamList[position]
            holder.bind(HistoryOfTeam, position)
        }
    }

    override fun getItemCount(): Int {
        return historyOfTeamList.size + if (addMode) 1 else 0
    }

    fun submitList(newList: List<HistoryOfTeam>) {
        historyOfTeamList.clear()
        historyOfTeamList.addAll(newList)
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

    inner class HistoryOfTeamViewHolder(private val binding: ItemUpdateTeamInfoLckEditOrAddBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(historyOfTeam: HistoryOfTeam, position: Int) {
            with(binding) {
                tvUpdateTeamInfoLckRoasterEditYear.text = historyOfTeam.year.toString()
                tvUpdateTeamInfoLckRoasterEditSeason.text = historyOfTeam.seasonName

                if (position == editModePosition) {
                    etUpdateTeamInfoLckRoasterEditYear.setText(historyOfTeam.year.toString())
                    etUpdateTeamInfoLckRoasterEditSeason.setText(historyOfTeam.seasonName)

                    etUpdateTeamInfoLckRoasterEditYear.visibility = View.VISIBLE
                    etUpdateTeamInfoLckRoasterEditSeason.visibility = View.VISIBLE
                    ivUpdateTeamInfoLckRoasterEditSave.visibility = View.VISIBLE

                    tvUpdateTeamInfoLckRoasterEditYear.visibility = View.GONE
                    tvUpdateTeamInfoLckRoasterEditSeason.visibility = View.GONE
                    ivUpdateTeamInfoLckRoasterEditEdit.visibility = View.GONE
                    ivUpdateTeamInfoLckRoasterEditDelete.visibility = View.GONE
                } else {
                    etUpdateTeamInfoLckRoasterEditYear.visibility = View.GONE
                    etUpdateTeamInfoLckRoasterEditSeason.visibility = View.GONE
                    ivUpdateTeamInfoLckRoasterEditSave.visibility = View.GONE

                    tvUpdateTeamInfoLckRoasterEditYear.visibility = View.VISIBLE
                    tvUpdateTeamInfoLckRoasterEditSeason.visibility = View.VISIBLE
                    ivUpdateTeamInfoLckRoasterEditEdit.visibility = View.VISIBLE
                    ivUpdateTeamInfoLckRoasterEditDelete.visibility = View.VISIBLE
                }

                ivUpdateTeamInfoLckRoasterEditEdit.setOnSingleClickListener {
                    editModePosition = position
                    notifyDataSetChanged()
                }

                ivUpdateTeamInfoLckRoasterEditSave.setOnClickListener {
                    val updatedHistoryOfTeam = historyOfTeam.copy(
                        year = etUpdateTeamInfoLckRoasterEditYear.text.toString().toInt(),
                        seasonName = etUpdateTeamInfoLckRoasterEditSeason.text.toString()
                    )
                    onSaveHistoryTeam(updatedHistoryOfTeam)
                    editModePosition = -1
                    notifyDataSetChanged()
                }

                ivUpdateTeamInfoLckRoasterEditDelete.setOnClickListener {
                    onDeleteHistoryTeam(historyOfTeam)
                }
            }
        }

        fun bindNewEntry() {
            with(binding) {
                etUpdateTeamInfoLckRoasterEditYear.setText("")
                etUpdateTeamInfoLckRoasterEditSeason.setText("")

                etUpdateTeamInfoLckRoasterEditYear.visibility = View.VISIBLE
                etUpdateTeamInfoLckRoasterEditSeason.visibility = View.VISIBLE
                ivUpdateTeamInfoLckRoasterEditSave.visibility = View.VISIBLE

                tvUpdateTeamInfoLckRoasterEditYear.visibility = View.GONE
                tvUpdateTeamInfoLckRoasterEditSeason.visibility = View.GONE
                ivUpdateTeamInfoLckRoasterEditEdit.visibility = View.GONE
                ivUpdateTeamInfoLckRoasterEditDelete.visibility = View.GONE

                ivUpdateTeamInfoLckRoasterEditSave.setOnSingleClickListener {
                    val newHistoryOfTeam = HistoryOfTeam(
                        id = historyOfTeamList.size + 1,
                        year = etUpdateTeamInfoLckRoasterEditYear.text.toString().toInt(),
                        seasonName = etUpdateTeamInfoLckRoasterEditSeason.text.toString()
                    )
                    onAddHistoryTeam(newHistoryOfTeam)
                    exitAddMode()
                }
            }
        }
    }
}

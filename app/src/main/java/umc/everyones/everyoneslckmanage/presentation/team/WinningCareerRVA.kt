package umc.everyones.everyoneslckmanage.presentation.team

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import umc.everyones.everyoneslckmanage.databinding.ItemUpdateTeamInfoLckEditOrAddBinding
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener


class WinningCareerRVA(
    private val onAddWinningCareer: (WinningCareer) -> Unit,
    private val onSaveWinningCareer: (WinningCareer) -> Unit,
    private val onDeleteWinningCareer: (WinningCareer) -> Unit
) : RecyclerView.Adapter<WinningCareerRVA.WinningCareerViewHolder>() {

    private var winningCareerList: MutableList<WinningCareer> = mutableListOf()
    private var editModePosition: Int = -1
    private var addMode: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WinningCareerViewHolder {
        val binding = ItemUpdateTeamInfoLckEditOrAddBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WinningCareerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WinningCareerViewHolder, position: Int) {
        if (addMode && position == winningCareerList.size) {
            holder.bindNewEntry()
        } else {
            val winningCareer = winningCareerList[position]
            holder.bind(winningCareer, position)
        }
    }

    override fun getItemCount(): Int {
        return winningCareerList.size + if (addMode) 1 else 0
    }

    fun submitList(newList: List<WinningCareer>) {
        winningCareerList.clear()
        winningCareerList.addAll(newList)
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

    inner class WinningCareerViewHolder(private val binding: ItemUpdateTeamInfoLckEditOrAddBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(winningCareer: WinningCareer, position: Int) {
            with(binding) {
                tvUpdateTeamInfoLckRoasterEditYear.text = winningCareer.year.toString()
                tvUpdateTeamInfoLckRoasterEditSeason.text = winningCareer.seasonName

                if (position == editModePosition) {
                    etUpdateTeamInfoLckRoasterEditYear.setText(winningCareer.year.toString())
                    etUpdateTeamInfoLckRoasterEditSeason.setText(winningCareer.seasonName)

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
                    val updatedWinningCareer = winningCareer.copy(
                        year = etUpdateTeamInfoLckRoasterEditYear.text.toString().toInt(),
                        seasonName = etUpdateTeamInfoLckRoasterEditSeason.text.toString()
                    )
                    onSaveWinningCareer(updatedWinningCareer)
                    editModePosition = -1
                    notifyDataSetChanged()
                }

                ivUpdateTeamInfoLckRoasterEditDelete.setOnClickListener {
                    onDeleteWinningCareer(winningCareer)
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
                    val newWinningCareer = WinningCareer(
                        id = winningCareerList.size + 1,
                        year = etUpdateTeamInfoLckRoasterEditYear.text.toString().toInt(),
                        seasonName = etUpdateTeamInfoLckRoasterEditSeason.text.toString()
                    )
                    onAddWinningCareer(newWinningCareer)
                    exitAddMode()
                }
            }
        }
    }
}

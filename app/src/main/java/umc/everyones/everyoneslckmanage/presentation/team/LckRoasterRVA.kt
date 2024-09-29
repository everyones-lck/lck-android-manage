package umc.everyones.everyoneslckmanage.presentation.team

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.ItemUpdateTeamInfoLckRoasterBinding

class LckRoasterRVA(private val onEditRoaster: (LckRoaster) -> Unit) :
    RecyclerView.Adapter<LckRoasterRVA.LckRoasterViewHolder>() {

    private var lastCheckedPosition: Int = -1
    private var lckRoasterList: MutableList<LckRoaster> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LckRoasterViewHolder {
        return LckRoasterViewHolder(
            ItemUpdateTeamInfoLckRoasterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LckRoasterViewHolder, position: Int) {
        val roaster = lckRoasterList[position]
        holder.bind(roaster, position)
    }

    override fun getItemCount(): Int = lckRoasterList.size

    fun submitList(newRoasterList: List<LckRoaster>) {
        lckRoasterList.clear()
        lckRoasterList.addAll(newRoasterList)
        notifyDataSetChanged()
    }

    inner class LckRoasterViewHolder(private val binding: ItemUpdateTeamInfoLckRoasterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(roaster: LckRoaster, position: Int) {
            with(binding) {
                tvUpdateTeamLckRoasterPlayerName.text = roaster.name
                tvUpdateTeamLckRoasterPlayerPosition.text = roaster.position

                Glide.with(ivUpdateTeamLckRoasterPlayerImg.context)
                    .load(roaster.imageUrl)
                    .into(ivUpdateTeamLckRoasterPlayerImg)

                cbUpdateTeamLckRoasterPlayerRadioButton.isChecked = position == lastCheckedPosition

                cbUpdateTeamLckRoasterPlayerRadioButton.setOnClickListener {
                    if (position == lastCheckedPosition) {
                        cbUpdateTeamLckRoasterPlayerRadioButton.isChecked = false
                        lastCheckedPosition = -1
                    } else {
                        lastCheckedPosition = position
                        notifyDataSetChanged()
                    }
                }

                ivUpdateTeamLckRoasterPlayerEdit.setOnClickListener {
                    onEditRoaster(roaster)
                }
            }
        }
    }
}
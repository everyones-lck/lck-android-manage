package umc.everyones.everyoneslckmanage.presentation.team

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.ItemUpdateTeamInfoLckClRoasterBinding
import umc.everyones.everyoneslckmanage.databinding.ItemUpdateTeamInfoLckRoasterBinding

class LckClRoasterRVA(private val onEditRoaster: (LckClRoaster) -> Unit) :
    RecyclerView.Adapter<LckClRoasterRVA.LckClRoasterViewHolder>() {

    private var lastCheckedPosition: Int = -1
    private var lckClRoasterList: MutableList<LckClRoaster> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LckClRoasterViewHolder {
        return LckClRoasterViewHolder(
            ItemUpdateTeamInfoLckClRoasterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LckClRoasterViewHolder, position: Int) {
        val roaster = lckClRoasterList[position]
        holder.bind(roaster, position)
    }

    override fun getItemCount(): Int = lckClRoasterList.size

    fun submitList(newRoasterList: List<LckClRoaster>) {
        lckClRoasterList.clear()
        lckClRoasterList.addAll(newRoasterList)
        notifyDataSetChanged()
    }

    inner class LckClRoasterViewHolder(private val binding: ItemUpdateTeamInfoLckClRoasterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(roaster: LckClRoaster, position: Int) {
            with(binding) {
                tvUpdateTeamLckClRoasterPlayerName.text = roaster.name
                tvUpdateTeamLckClRoasterPlayerPosition.text = roaster.position

                Glide.with(ivUpdateTeamLckClRoasterPlayerImg.context)
                    .load(roaster.imageUrl)
                    .into(ivUpdateTeamLckClRoasterPlayerImg)

                cbUpdateTeamLckClRoasterPlayerRadioButton.isChecked = position == lastCheckedPosition

                cbUpdateTeamLckClRoasterPlayerRadioButton.setOnClickListener {
                    if (position == lastCheckedPosition) {
                        cbUpdateTeamLckClRoasterPlayerRadioButton.isChecked = false
                        lastCheckedPosition = -1
                    } else {
                        lastCheckedPosition = position
                        notifyDataSetChanged()
                    }
                }

                ivUpdateTeamLckClRoasterPlayerEdit.setOnClickListener {
                    onEditRoaster(roaster)
                }
            }
        }
    }
}
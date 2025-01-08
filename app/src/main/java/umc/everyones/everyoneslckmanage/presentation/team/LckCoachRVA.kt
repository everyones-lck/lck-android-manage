package umc.everyones.everyoneslckmanage.presentation.team

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.ItemUpdateTeamInfoLckCoachBinding
import umc.everyones.everyoneslckmanage.databinding.ItemUpdateTeamInfoLckRoasterBinding

class LckCoachRVA(private val onEditRoaster: (LckCoach) -> Unit) :
    RecyclerView.Adapter<LckCoachRVA.LckCoachViewHolder>() {

    private var lastCheckedPosition: Int = -1
    private var lckCoachList: MutableList<LckCoach> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LckCoachViewHolder {
        return LckCoachViewHolder(
            ItemUpdateTeamInfoLckCoachBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LckCoachViewHolder, position: Int) {
        val roaster = lckCoachList[position]
        holder.bind(roaster, position)
    }

    override fun getItemCount(): Int = lckCoachList.size

    fun submitList(newRoasterList: List<LckCoach>) {
        lckCoachList.clear()
        lckCoachList.addAll(newRoasterList)
        notifyDataSetChanged()
    }

    inner class LckCoachViewHolder(private val binding: ItemUpdateTeamInfoLckCoachBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(roaster: LckCoach, position: Int) {
            with(binding) {
                tvUpdateTeamLckCoachPlayerName.text = roaster.name

                Glide.with(ivUpdateTeamLckCoachPlayerImg.context)
                    .load(roaster.imageUrl)
                    .into(ivUpdateTeamLckCoachPlayerImg)

                cbUpdateTeamLckCoachPlayerRadioButton.isChecked = position == lastCheckedPosition

                cbUpdateTeamLckCoachPlayerRadioButton.setOnClickListener {
                    if (position == lastCheckedPosition) {
                        cbUpdateTeamLckCoachPlayerRadioButton.isChecked = false
                        lastCheckedPosition = -1
                    } else {
                        lastCheckedPosition = position
                        notifyDataSetChanged()
                    }
                }

                ivUpdateTeamLckCoachPlayerEdit.setOnClickListener {
                    onEditRoaster(roaster)
                }
            }
        }
    }
}
package umc.everyones.everyoneslckmanage.presentation.party.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.databinding.ItemViewingPartyBinding
import umc.everyones.everyoneslckmanage.domain.model.response.party.ViewingPartyListModel
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class ViewingPartyRVA(val readViewingParty: (Long) -> Unit) :
    PagingDataAdapter<ViewingPartyListModel.ViewingPartyElementModel, ViewingPartyRVA.ViewingPartyViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewingPartyViewHolder {
        return ViewingPartyViewHolder(
            ItemViewingPartyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewingPartyViewHolder, position: Int) {
        val viewingParty = getItem(position)
        if(viewingParty != null) {
            holder.bind(viewingParty)
        }
    }

    inner class ViewingPartyViewHolder(private val binding: ItemViewingPartyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(viewingPartyItem: ViewingPartyListModel.ViewingPartyElementModel) {
            with(binding){
                tvViewingPartyTitle.text = viewingPartyItem.name
                tvViewingPartyWriter.text = viewingPartyItem.writerInfo
                tvViewingPartyAddress.text = viewingPartyItem.shortLocation
                tvViewingPartyDate.text = viewingPartyItem.partyDate
                Glide.with(ivViewingPartyProfile.context)
                    .load(viewingPartyItem.photoURL)
                    .into(ivViewingPartyProfile)
                root.setOnSingleClickListener {
                    readViewingParty(viewingPartyItem.id)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ViewingPartyListModel.ViewingPartyElementModel>() {
        override fun areItemsTheSame(oldItem: ViewingPartyListModel.ViewingPartyElementModel, newItem: ViewingPartyListModel.ViewingPartyElementModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ViewingPartyListModel.ViewingPartyElementModel, newItem: ViewingPartyListModel.ViewingPartyElementModel) =
            oldItem == newItem
    }
}
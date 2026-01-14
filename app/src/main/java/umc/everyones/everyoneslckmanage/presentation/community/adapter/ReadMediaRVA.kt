package umc.everyones.everyoneslckmanage.presentation.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.data.dto.response.community.ReadCommunityWithReportResponseDto
import umc.everyones.everyoneslckmanage.databinding.ItemMediaReadBinding
import umc.everyones.everyoneslckmanage.domain.model.response.community.ReadCommunityWithReportResponseModel
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto


class ReadMediaRVA(val viewOriginalMedia: (String, Boolean) -> Unit // 미디어 원본 보기 위한 함수
 ) : ListAdapter<ReadCommunityWithReportResponseDto.File, ReadMediaRVA.ReadMediaViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadMediaViewHolder {
        return ReadMediaViewHolder(
            ItemMediaReadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ReadMediaViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class ReadMediaViewHolder(private val binding: ItemMediaReadBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(file: ReadCommunityWithReportResponseDto.File){

                Glide.with(binding.ivMediaImage.context)
                    .load(file.fileUrl)
                    .into(binding.ivMediaImage)
                binding.ivMediaImage.setOnSingleClickListener {
                    viewOriginalMedia(file.fileUrl, file.isImage)
                }
            }
        }

    class DiffCallback : DiffUtil.ItemCallback<ReadCommunityWithReportResponseDto.File>() {
        override fun areItemsTheSame(oldItem: ReadCommunityWithReportResponseDto.File, newItem: ReadCommunityWithReportResponseDto.File) =
            oldItem === newItem

        override fun areContentsTheSame(oldItem: ReadCommunityWithReportResponseDto.File, newItem: ReadCommunityWithReportResponseDto.File) =
            oldItem == newItem
    }
}
package umc.everyones.everyoneslckmanage.presentation.community.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.databinding.ItemCommunityCommentBinding
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommentWithReportListResponseModel
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class CommentListRVA(val readPost: (Long) -> Unit) : PagingDataAdapter< CommentWithReportListResponseModel.CommentWithReportListResponseElementModel, CommentListRVA.PostViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemCommunityCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        if(post != null) {
            holder.bind(post)
        }
    }

    inner class PostViewHolder(private val binding: ItemCommunityCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(commentListItem: CommentWithReportListResponseModel.CommentWithReportListResponseElementModel){
                with(binding){
                    tvPostTitle.text = commentListItem.postTitle
                    tvPostReportCount.text = commentListItem.reportCounts.toString()
                    tvPostDate.text = commentListItem.createdAt.toFormattedDate()
                    tvPostNickname.text = commentListItem.userNickname
                    tvPostFavoriteTeam.text = commentListItem.supportTeamName

                    Glide.with(ivProfileImage.context)
                        .load(commentListItem.userProfilePicture)
                        .into(ivProfileImage)

                    // 게시글 postId 전달
                    root.setOnSingleClickListener {
                        readPost(commentListItem.postId)
                    }
                }
            }
        }

    class DiffCallback : DiffUtil.ItemCallback<  CommentWithReportListResponseModel.CommentWithReportListResponseElementModel>() {
        override fun areItemsTheSame(oldItem:   CommentWithReportListResponseModel.CommentWithReportListResponseElementModel, newItem:  CommentWithReportListResponseModel.CommentWithReportListResponseElementModel) =
            oldItem.postId == newItem.postId

        override fun areContentsTheSame(oldItem:   CommentWithReportListResponseModel.CommentWithReportListResponseElementModel, newItem:  CommentWithReportListResponseModel.CommentWithReportListResponseElementModel) =
            oldItem == newItem
    }

    fun String.toFormattedDate(): String {
        return try {
            val datePart = this.substring(0, 10)
            datePart.replace("-", ".")
        } catch (e: Exception) {
            this
        }
    }
}
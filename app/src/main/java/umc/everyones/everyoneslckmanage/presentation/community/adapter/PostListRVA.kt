package umc.everyones.everyoneslckmanage.presentation.community.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.databinding.ItemCommunityPostBinding
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityListModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityWithReportListModel
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class PostListRVA(val readPost: (Long) -> Unit) : PagingDataAdapter< CommunityWithReportListModel.CommunityReportListElementModel, PostListRVA.PostViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemCommunityPostBinding.inflate(
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

    inner class PostViewHolder(private val binding: ItemCommunityPostBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(postListItem:  CommunityWithReportListModel.CommunityReportListElementModel){
                with(binding){
                    tvPostTitle.text = postListItem.postTitle
                    tvPostReportCount.text = postListItem.reportCounts.toString()
                    tvPostDate.text = postListItem.postCreatedAt
                    tvPostNickname.text = postListItem.userNickname
                    tvPostFavoriteTeam.text = postListItem.supportTeamName
                    tvPostComment.text = postListItem.commentCounts.toString()

                    Glide.with(ivProfileImage.context)
                        .load(postListItem.userProfilePicture)
                        .into(ivProfileImage)

                    if (postListItem.thumbnailFileUrl.isNotEmpty()){
                        cvPostImage.visibility = View.VISIBLE
                        Glide.with(ivPostImage.context)
                            .load(postListItem.thumbnailFileUrl)
                            .into(ivPostImage)
                    } else {
                        cvPostImage.visibility = View.INVISIBLE
                    }

                    // 게시글 postId 전달
                    root.setOnSingleClickListener {
                        readPost(postListItem.postId)
                    }
                }
            }
        }

    class DiffCallback : DiffUtil.ItemCallback< CommunityWithReportListModel.CommunityReportListElementModel>() {
        override fun areItemsTheSame(oldItem:  CommunityWithReportListModel.CommunityReportListElementModel, newItem:  CommunityWithReportListModel.CommunityReportListElementModel) =
            oldItem.postId == newItem.postId

        override fun areContentsTheSame(oldItem:  CommunityWithReportListModel.CommunityReportListElementModel, newItem:  CommunityWithReportListModel.CommunityReportListElementModel) =
            oldItem == newItem
    }
}
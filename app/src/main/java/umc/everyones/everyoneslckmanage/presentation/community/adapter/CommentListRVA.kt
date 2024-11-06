package umc.everyones.everyoneslckmanage.presentation.community.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.databinding.ItemCommunityPostBinding
import umc.everyones.everyoneslckmanage.databinding.ItemDeleteCommentBinding
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityListModel
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class CommentListRVA(val readPost: (Long) -> Unit) : PagingDataAdapter<CommunityListModel.CommunityListElementModel, CommentListRVA.CommentListViewHolder>(
    DiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentListViewHolder {
        return CommentListViewHolder(
            ItemDeleteCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentListViewHolder, position: Int) {
        val post = getItem(position)
        if(post != null) {
            holder.bind(post)
        }
    }

    inner class CommentListViewHolder(private val binding: ItemDeleteCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(postListItem: CommunityListModel.CommunityListElementModel){
            with(binding){
                tvPostDate.text = postListItem.postCreatedAt
                tvPostNickname.text = postListItem.userNickname
                tvPostFavoriteTeam.text = postListItem.supportTeamName

                Glide.with(ivProfileImage.context)
                    .load(postListItem.userProfilePicture)
                    .into(ivProfileImage)

                // 게시글 postId 전달
                root.setOnSingleClickListener {
                    readPost(postListItem.postId)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<CommunityListModel.CommunityListElementModel>() {
        override fun areItemsTheSame(oldItem: CommunityListModel.CommunityListElementModel, newItem: CommunityListModel.CommunityListElementModel) =
            oldItem.postId == newItem.postId

        override fun areContentsTheSame(oldItem: CommunityListModel.CommunityListElementModel, newItem: CommunityListModel.CommunityListElementModel) =
            oldItem == newItem
    }
}
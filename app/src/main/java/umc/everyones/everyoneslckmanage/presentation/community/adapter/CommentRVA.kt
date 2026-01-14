package umc.everyones.everyoneslckmanage.presentation.community.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.databinding.ItemCommentBinding
import umc.everyones.everyoneslckmanage.domain.model.response.community.ReadCommunityResponseModel
import umc.everyones.everyoneslckmanage.domain.model.response.community.ReadCommunityWithReportResponseModel
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class CommentRVA(
    val deleteComment: (commentId: Long) -> Unit
) : ListAdapter<ReadCommunityWithReportResponseModel.CommentListElementModel, CommentRVA.CommentViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(comment:ReadCommunityWithReportResponseModel.CommentListElementModel) {
            with(binding) {
                tvCommentNickname.text = comment.nickname
                tvCommentBody.text = comment.content
                tvCommentReportCount.text = comment.reportCount.toString()
                tvCommentDate.text = comment.createdAt.toFormattedDate()
                Glide.with(ivCommentProfile.context)
                    .load(comment.profileImageUrl)
                    .into(ivCommentProfile)

                // 댓글 삭제
                ivCommentDeleteBtn.setOnSingleClickListener {
                    deleteComment(comment.commentId)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<ReadCommunityWithReportResponseModel.CommentListElementModel>() {
        override fun areItemsTheSame(oldItem: ReadCommunityWithReportResponseModel.CommentListElementModel, newItem:ReadCommunityWithReportResponseModel.CommentListElementModel) =
            oldItem.commentId == newItem.commentId

        override fun areContentsTheSame(oldItem: ReadCommunityWithReportResponseModel.CommentListElementModel, newItem:ReadCommunityWithReportResponseModel.CommentListElementModel) =
            oldItem == newItem
    }

    fun String.toFormattedDate(): String {
        return try {
            val datePart = this.substring(0, 16).replace("T", " ")
            datePart.substring(2).replace("-", ".")
        } catch (e: Exception) {
            this
        }
    }
}
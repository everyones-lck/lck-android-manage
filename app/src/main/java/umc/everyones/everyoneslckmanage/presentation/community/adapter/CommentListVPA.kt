package umc.everyones.everyoneslckmanage.presentation.community.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.databinding.ItemCommunityPostBinding
import umc.everyones.everyoneslckmanage.domain.model.response.community.CommunityListModel
import umc.everyones.everyoneslckmanage.presentation.community.commentlist.FreeAgentCommentListFragment
import umc.everyones.everyoneslckmanage.presentation.community.commentlist.QuestionCommentListFragment
import umc.everyones.everyoneslckmanage.presentation.community.commentlist.ReviewCommentListFragment
import umc.everyones.everyoneslckmanage.presentation.community.commentlist.SmallTalkCommentListFragment
import umc.everyones.everyoneslckmanage.presentation.community.commentlist.SupportCommentListFragment
import umc.everyones.everyoneslckmanage.presentation.community.commentlist.TradeCommentListFragment
import umc.everyones.everyoneslckmanage.presentation.community.list.FreeAgentListFragment
import umc.everyones.everyoneslckmanage.presentation.community.list.QuestionListFragment
import umc.everyones.everyoneslckmanage.presentation.community.list.ReviewListFragment
import umc.everyones.everyoneslckmanage.presentation.community.list.SmallTalkListFragment
import umc.everyones.everyoneslckmanage.presentation.community.list.SupportListFragment
import umc.everyones.everyoneslckmanage.presentation.community.list.TradeListFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class CommentListVPA(fragment: Fragment): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 6
    }

    // API 연결하면서 내부 Fragment 바꿀 예정
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SmallTalkCommentListFragment()
            1 -> SupportCommentListFragment()
            2 -> FreeAgentCommentListFragment()
            3 -> TradeCommentListFragment()
            4 -> QuestionCommentListFragment()
            5 -> ReviewCommentListFragment()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}
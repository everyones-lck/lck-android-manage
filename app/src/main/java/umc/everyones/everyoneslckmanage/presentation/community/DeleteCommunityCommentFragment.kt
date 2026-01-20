package umc.everyones.everyoneslckmanage.presentation.community

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentDeleteCommunityCommentBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.toCategoryPosition
import umc.everyones.everyoneslckmanage.presentation.community.adapter.CommunityListVPA
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class DeleteCommunityCommentFragment: BaseFragment<FragmentDeleteCommunityCommentBinding>(R.layout.fragment_delete_community_comment) {
    private val communityViewModel: CommunityViewModel by activityViewModels()

    private var _communityListVPA: CommunityListVPA? = null
    private val communityListVPA get() = _communityListVPA

    // 글 작성 시 선택한 카테고리 화면으로 이동
    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val category = result.data?.getStringExtra("category") ?: ""
                binding.vpCommunityCommentList.currentItem = category.toCategoryPosition()
                val isWriteDone = result.data?.getBooleanExtra("isWriteDone", false) ?: false
                if (isWriteDone) {
                    communityViewModel.setFilter(category, false)
                }
            }
        }

    override fun initObserver() {
    }

    override fun initView() {
        communityViewModel.setFilter("잡담", false)

        initPostListVPAdapter()
        binding.ivReadBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initPostListVPAdapter() {
        _communityListVPA = CommunityListVPA(this)
        with(binding) {
            vpCommunityCommentList.adapter = communityListVPA

            TabLayoutMediator(tabCommunityCategoryComment, vpCommunityCommentList) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()

            tabCommunityCategoryComment.addOnTabSelectedListener(object :
                TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    communityViewModel.setFilter(tab?.text?.toString() ?: "잡담", false)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {

                }

            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _communityListVPA = null
    }

    companion object {
        private val tabTitles = listOf("잡담", "응원", "FA", "거래", "질문", "후기")
    }
}
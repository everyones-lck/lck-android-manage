package umc.everyones.everyoneslckmanage.presentation.community

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentDeleteCommunityContentBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.toCategoryPosition
import umc.everyones.everyoneslckmanage.presentation.community.adapter.PostListVPA
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

@AndroidEntryPoint
class DeleteCommunityContentFragment: BaseFragment<FragmentDeleteCommunityContentBinding>(R.layout.fragment_delete_community_content) {
    private val communityViewModel: CommunityViewModel by activityViewModels()

    private var _postListVPA: PostListVPA? = null
    private val postListVPA get() = _postListVPA

    // 글 작성 시 선택한 카테고리 화면으로 이동
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val category = result.data?.getStringExtra("category") ?: ""
            binding.vpCommunityPostList.currentItem = category.toCategoryPosition()
            val isWriteDone = result.data?.getBooleanExtra("isWriteDone", false) ?: false
            if (isWriteDone){
                communityViewModel.refreshCategoryPage(category)
            }
        }
    }

    override fun initObserver() {
    }

    override fun initView() {
        initPostListVPAdapter()
        binding.ivReadBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initPostListVPAdapter(){
        _postListVPA = PostListVPA(this)
        with(binding){
            vpCommunityPostList.adapter = postListVPA

            TabLayoutMediator(tabCommunityCategory, vpCommunityPostList) { tab, position ->
                tab.text = tabTitles[position]
            }.attach()

            tabCommunityCategory.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    communityViewModel.refreshCategoryPage(tab?.text?.toString() ?: "잡담")
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
        _postListVPA = null
    }

    companion object {
        private val tabTitles = listOf("잡담", "응원", "FA", "거래", "질문", "후기")
    }
}
package umc.everyones.everyoneslckmanage.presentation.party

import android.app.Activity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentDeleteViewingPartyBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentInputMatchInfoBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.presentation.party.adapter.ViewingPartyRVA
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted

class DeleteViewingPartyFragment : BaseFragment<FragmentDeleteViewingPartyBinding>(R.layout.fragment_delete_viewing_party) {
    private var _viewIngPartyRVA: ViewingPartyRVA? = null
    private val viewingPartyRVA get() = _viewIngPartyRVA
    private val viewModel: ViewingPartyViewModel by activityViewModels()
    private val navigator by lazy {
        findNavController()
    }

    private var writeResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            Timber.d("isWriteDone", result.data?.getBooleanExtra("isWriteDone", false).toString())
            if(result.data?.getBooleanExtra("isWriteDone", false) == true){
                viewModel.setIsRefreshNeeded(true)
            }
        }
    }

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.viewingPartyListPage.collectLatest { data ->
                viewingPartyRVA?.submitData(data)
            }
        }

        viewLifecycleOwner.repeatOnStarted {
            viewModel.isRefreshNeeded.collect { isRefreshNeeded ->
                if (isRefreshNeeded) {
                    viewingPartyRVA?.refresh()
                    viewModel.setIsRefreshNeeded(false)
                    binding.rvViewingParty.scrollToPosition(0)
                }
            }
        }
    }

    override fun initView() {
        initViewingPartyRVAdapter()
    }

    private fun initViewingPartyRVAdapter(){
        _viewIngPartyRVA = ViewingPartyRVA { postId  ->
            val action = DeleteViewingPartyFragmentDirections.actionDeleteViewingPartyFragmentToReadViewingPartyFragment(postId)
            navigator.navigate(action)
        }

        viewingPartyRVA?.addLoadStateListener { combinedLoadStates ->
            with(binding){
                layoutShimmer.isVisible = combinedLoadStates.source.refresh is LoadState.Loading
                rvViewingParty.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading
                if(combinedLoadStates.source.refresh is LoadState.Loading){
                    layoutShimmer.startShimmer()
                }

                if(combinedLoadStates.source.refresh is LoadState.NotLoading){
                    layoutShimmer.stopShimmer()
                }
            }
        }

        binding.rvViewingParty.adapter = viewingPartyRVA
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewIngPartyRVA = null
    }
}
package umc.everyones.everyoneslckmanage.presentation.party.read

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentReadViewingPartyBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.presentation.party.ViewingPartyViewModel
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import umc.everyones.everyoneslckmanage.util.extension.showCustomSnackBar
import umc.everyones.everyoneslckmanage.util.network.UiState

@AndroidEntryPoint
class ReadViewingPartyFragment : BaseFragment<FragmentReadViewingPartyBinding>(R.layout.fragment_read_viewing_party) {
    private val viewModel: ReadViewingPartyViewModel by activityViewModels()
    private val viewingPartyViewModel: ViewingPartyViewModel by activityViewModels()
    private val navigator by lazy {
        findNavController()
    }

    private val args: ReadViewingPartyFragmentArgs by navArgs()

    private val postId by lazy {
        args.postId
    }

    private var writeResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val isWriteDone = result.data?.getBooleanExtra("isWriteDone", false) ?: false
            if(isWriteDone){
                viewingPartyViewModel.setIsRefreshNeeded(true)
                navigator.navigateUp()
            }
        }
    }

    private var isParticipated = false
    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.readViewingPartyEvent.collect{ state ->
                when(state){
                    is UiState.Success -> {handleReadViewingPartyEvent(state.data)}
                    is UiState.Failure -> {
                        showCustomSnackBar(binding.root, state.msg)
                    }
                    else -> Unit
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun handleReadViewingPartyEvent(event: ReadViewingPartyViewModel.ReadViewingPartyEvent){
        when(event){
            is ReadViewingPartyViewModel.ReadViewingPartyEvent.ReadViewingParty -> {
                with(binding){
                    tvReadViewingPartyPostTitle.text = event.viewingParty.name
                    tvReadQualify.text = "To. ${event.viewingParty.qualify}"
                    tvReadDate.text = event.viewingParty.partyDate
                    tvReadPlace.text = event.viewingParty.place
                    tvReadPrice.text = "₩${event.viewingParty.price}"
                    tvReadParticipants.text = event.viewingParty.participants
                    tvReadEtc.text = event.viewingParty.etc
                    tvReadWriter.text = event.viewingParty.writerInfo
                    Glide.with(requireContext())
                        .load(event.viewingParty.ownerImage)
                        .into(ivReadProfileImage)

                    viewModel.setTitle(event.viewingParty.name)
                    isParticipated = event.viewingParty.isParticipated
                    layoutReadViewingPartyContent.isVisible = true
                }
            }

            ReadViewingPartyViewModel.ReadViewingPartyEvent.DeleteViewingParty -> {
                viewingPartyViewModel.setIsRefreshNeeded(true)
                navigator.navigateUp()
            }

            else -> Unit
        }
    }

    override fun initView() {
        Timber.d("postId", postId.toString())
        viewModel.setPostId(postId)
        binding.ivReadViewingPartyBackBtn.setOnSingleClickListener {
            navigator.navigateUp()
        }
        deleteViewingParty()
    }
    private fun deleteViewingParty(){
        binding.ivReadDeleteBtn.setOnSingleClickListener {
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchViewingParty()
    }
}
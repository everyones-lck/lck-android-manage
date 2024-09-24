package umc.everyones.everyoneslckmanage.presentation.community.read

import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.DialogMediaBinding
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import umc.everyones.everyoneslckmanage.presentation.base.BaseDialogFragment

class ReadImageDialogFragment : BaseDialogFragment<DialogMediaBinding>(R.layout.dialog_media) {
    private val viewModel: ReadPostViewModel by activityViewModels()

    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.imageUrl.collect{
                if (it.isEmpty()){
                    return@collect
                }
                with(binding){
                    Glide.with(requireContext())
                        .load(it)
                        .into(ivImage)
                }
            }
        }
    }

    override fun initView() {
        requireContext().dialogFragmentResize(this, 0.8f, 0.8f)
        binding.ivClose.setOnSingleClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}
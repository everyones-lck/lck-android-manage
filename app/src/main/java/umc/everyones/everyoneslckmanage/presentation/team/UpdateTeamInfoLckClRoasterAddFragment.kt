package umc.everyones.everyoneslckmanage.presentation.team

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckClRoasterAddBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterAddBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.util.UUID

class UpdateTeamInfoLckClRoasterAddFragment : BaseFragment<FragmentUpdateTeamInfoLckClRoasterAddBinding>(R.layout.fragment_update_team_info_lck_cl_roaster_add) {

    private var selectedImageUri: String? = null
    private val viewModel: UpdateTeamInfoLckClRoasterViewModel by activityViewModels()
    private val navigator by lazy {
        findNavController()
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivUpdateTeamLckClRoasterAddPhoto.setImageURI(it)
            selectedImageUri = it.toString()
        }
    }

    override fun initObserver() {}

    override fun initView() {
        setTeamName()
        setupCheckBoxListeners()
        setupSaveButtonListener()
        setupNoButtonListener()
        setupBackButtonListener()

        binding.ivUpdateTeamLckClRoasterAddPhoto.setOnClickListener {
            openGallery()
        }
    }

    private fun setTeamName() {
        val teamName = viewModel.teamName ?: "Unknown Team"
        binding.tvUpdateTeamLckClRoasterAddTeamName.text = teamName
    }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckClRoasterAddPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckClRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckClRoasterAddFragmentToUpdateTeamInfoLckClRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupCheckBoxListeners() {
        val checkBoxes = listOf(
            binding.cbUpdateTeamLckClRoasterAddPositionTop,
            binding.cbUpdateTeamLckClRoasterAddPositionMid,
            binding.cbUpdateTeamLckClRoasterAddPositionJungle,
            binding.cbUpdateTeamLckClRoasterAddPositionBot,
            binding.cbUpdateTeamLckClRoasterAddPositionSupport
        )

        checkBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    checkBoxes.forEach { otherCheckBox ->
                        if (otherCheckBox != checkBox) {
                            otherCheckBox.isChecked = false
                        }
                    }
                }
            }
        }
    }

    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckClRoasterAddNo.setOnSingleClickListener {
            val action = UpdateTeamInfoLckClRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckClRoasterAddFragmentToUpdateTeamInfoLckClRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckClRoasterAddCheck.setOnSingleClickListener  {
            val playerName = binding.edUpdateTeamLckClRoasterAddName.text.toString()
            val playerPosition = getSelectedPosition()

            val newPlayer = LckClRoaster(
                id = UUID.randomUUID().toString(),
                name = playerName,
                position = playerPosition,
                imageUrl = selectedImageUri ?: ""
            )

            val action = UpdateTeamInfoLckClRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckClRoasterAddFragmentToUpdateTeamInfoLckClRoasterFragment(
                    newPlayer = newPlayer,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun getSelectedPosition(): String {
        return when {
            binding.cbUpdateTeamLckClRoasterAddPositionTop.isChecked -> "탑"
            binding.cbUpdateTeamLckClRoasterAddPositionMid.isChecked -> "미드"
            binding.cbUpdateTeamLckClRoasterAddPositionJungle.isChecked -> "정글"
            binding.cbUpdateTeamLckClRoasterAddPositionBot.isChecked -> "원딜"
            binding.cbUpdateTeamLckClRoasterAddPositionSupport.isChecked -> "서폿"
            else -> ""
        }
    }
}

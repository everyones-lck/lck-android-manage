package umc.everyones.everyoneslckmanage.presentation.team

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterAddBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.util.UUID

class UpdateTeamInfoLckRoasterAddFragment : BaseFragment<FragmentUpdateTeamInfoLckRoasterAddBinding>(R.layout.fragment_update_team_info_lck_roaster_add) {

    private var selectedImageUri: String? = null
    private val viewModel: UpdateTeamInfoLckRoasterViewModel by activityViewModels()
    private val navigator by lazy {
        findNavController()
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivUpdateTeamLckRoasterAddPhoto.setImageURI(it)
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

        binding.ivUpdateTeamLckRoasterAddPhoto.setOnClickListener {
            openGallery()
        }
    }

    private fun setTeamName() {
        val teamName = viewModel.teamName ?: "Unknown Team"
        binding.tvUpdateTeamLckRoasterAddTeamName.text = teamName
    }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckRoasterAddPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckRoasterAddFragmentToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupCheckBoxListeners() {
        val checkBoxes = listOf(
            binding.cbUpdateTeamLckRoasterAddPositionTop,
            binding.cbUpdateTeamLckRoasterAddPositionMid,
            binding.cbUpdateTeamLckRoasterAddPositionJungle,
            binding.cbUpdateTeamLckRoasterAddPositionBot,
            binding.cbUpdateTeamLckRoasterAddPositionSupport
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
        binding.ivUpdateTeamLckRoasterAddNo.setOnSingleClickListener {
            val action = UpdateTeamInfoLckRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckRoasterAddFragmentToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckRoasterAddCheck.setOnSingleClickListener  {
            val playerName = binding.edUpdateTeamLckRoasterAddName.text.toString()
            val playerPosition = getSelectedPosition()

            val newPlayer = LckRoaster(
                id = UUID.randomUUID().toString(),
                name = playerName,
                position = playerPosition,
                imageUrl = selectedImageUri ?: ""
            )

            val action = UpdateTeamInfoLckRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckRoasterAddFragmentToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = newPlayer,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun getSelectedPosition(): String {
        return when {
            binding.cbUpdateTeamLckRoasterAddPositionTop.isChecked -> "탑"
            binding.cbUpdateTeamLckRoasterAddPositionMid.isChecked -> "미드"
            binding.cbUpdateTeamLckRoasterAddPositionJungle.isChecked -> "정글"
            binding.cbUpdateTeamLckRoasterAddPositionBot.isChecked -> "원딜"
            binding.cbUpdateTeamLckRoasterAddPositionSupport.isChecked -> "서폿"
            else -> ""
        }
    }
}

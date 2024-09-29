package umc.everyones.everyoneslckmanage.presentation.team

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckCoachAddBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterAddBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.util.UUID

class UpdateTeamInfoLckCoachAddFragment : BaseFragment<FragmentUpdateTeamInfoLckCoachAddBinding>(R.layout.fragment_update_team_info_lck_coach_add) {

    private var selectedImageUri: String? = null
    private val viewModel: UpdateTeamInfoLckCoachViewModel by activityViewModels()
    private val navigator by lazy {
        findNavController()
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivUpdateTeamLckCoachAddPhoto.setImageURI(it)
            selectedImageUri = it.toString()
        }
    }

    override fun initObserver() {}

    override fun initView() {
        setTeamName()
        setupSaveButtonListener()
        setupNoButtonListener()
        setupBackButtonListener()

        binding.ivUpdateTeamLckCoachAddPhoto.setOnClickListener {
            openGallery()
        }
    }

    private fun setTeamName() {
        val teamName = viewModel.teamName ?: "Unknown Team"
        binding.tvUpdateTeamLckCoachAddTeamName.text = teamName
    }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckCoachAddPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckCoachAddFragmentDirections
                .actionUpdateTeamInfoLckCoachAddFragmentToUpdateTeamInfoLckCoachFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckCoachAddNo.setOnSingleClickListener {
            val action = UpdateTeamInfoLckCoachAddFragmentDirections
                .actionUpdateTeamInfoLckCoachAddFragmentToUpdateTeamInfoLckCoachFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckCoachAddCheck.setOnSingleClickListener  {
            val playerName = binding.edUpdateTeamLckCoachAddName.text.toString()

            val newPlayer = LckCoach(
                id = UUID.randomUUID().toString(),
                name = playerName,
                imageUrl = selectedImageUri ?: ""
            )

            val action = UpdateTeamInfoLckCoachAddFragmentDirections
                .actionUpdateTeamInfoLckCoachAddFragmentToUpdateTeamInfoLckCoachFragment(
                    newPlayer = newPlayer,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }
}

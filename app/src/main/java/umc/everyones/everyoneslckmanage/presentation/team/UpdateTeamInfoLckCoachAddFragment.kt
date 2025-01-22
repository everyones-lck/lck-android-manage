package umc.everyones.everyoneslckmanage.presentation.team

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckCoachAddBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterAddBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.util.UUID

class UpdateTeamInfoLckCoachAddFragment : BaseFragment<FragmentUpdateTeamInfoLckCoachAddBinding>(R.layout.fragment_update_team_info_lck_coach_add) {

    private var playerId: Int? = null
    private var selectedImageUri: String? = null

    private val viewModel: UpdateTeamInfoLckCoachAddViewModel by activityViewModels()
    private val viewModel_team: UpdateTeamInfoLckCoachViewModel by activityViewModels()

    private val navigator by lazy {
        findNavController()
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivUpdateTeamLckCoachAddPhoto.setImageURI(it)
            selectedImageUri = it.toString()
        }
    }

    override fun initObserver() {

    }

    override fun initView() {
        playerId = generateUniquePlayerId()
        setTeamName()
        setupNoButtonListener()
        setupBackButtonListener()
        setupSaveButtonListener()

        binding.ivUpdateTeamLckCoachAddGallery.setOnSingleClickListener {
            openGallery()
        }
    }

    private fun generateUniquePlayerId(): Int {
        return UUID.randomUUID().hashCode()
    }



    private fun setTeamName() {
        val teamName = viewModel_team.teamName ?: "Unknown Team"
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
            val playerName = binding.etUpdateTeamLckCoachAddName.text.toString()

            val teamName = viewModel_team.teamName ?: "Unknown Team"

            val newPlayer = LckCoach(
                id = playerId ?: generateUniquePlayerId(),
                name = playerName,
                imageUrl = selectedImageUri ?: "",
                teamName = teamName
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

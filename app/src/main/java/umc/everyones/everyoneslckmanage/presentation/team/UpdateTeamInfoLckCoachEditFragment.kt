package umc.everyones.everyoneslckmanage.presentation.team

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckCoachEditBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterEditBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class UpdateTeamInfoLckCoachEditFragment: BaseFragment<FragmentUpdateTeamInfoLckCoachEditBinding>(R.layout.fragment_update_team_info_lck_coach_edit) {

    private var playerId: String? = null
    private var selectedImageUri: String? = null

    private val viewModel: UpdateTeamInfoLckCoachViewModel by activityViewModels()

    private val navigator by lazy {
        findNavController()
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivUpdateTeamLckCoachEditPhoto.setImageURI(it)
            selectedImageUri = it.toString()
        }
    }
    override fun initObserver() {

    }

    override fun initView() {
        setupInitialData()
        setupSaveButtonListener()
        setupNoButtonListener()
        setupBackButtonListener()
        setTeamName()

        binding.ivUpdateTeamLckCoachEditPhoto.setOnSingleClickListener  {
            openGallery()
        }
    }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setTeamName() {
        val teamName = viewModel.teamName ?: "Unknown Team"
        binding.tvUpdateTeamLckCoachEditTeamName.text = teamName
    }
    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckCoachEditPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckCoachEditFragmentDirections
                .actionUpdateTeamInfoLckCoachEditFragmentToUpdateTeamInfoLckCoachFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckCoachEditNo.setOnSingleClickListener {
            playerId?.let {
                viewModel.deletePlayer(it)
            }
            val action = UpdateTeamInfoLckCoachEditFragmentDirections
                .actionUpdateTeamInfoLckCoachEditFragmentToUpdateTeamInfoLckCoachFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupInitialData() {
        playerId = arguments?.getString("playerId")
        val playerName = arguments?.getString("playerName")
        val playerImageUrl = arguments?.getString("playerImageUrl")

        binding.edUpdateTeamLckCoachEditName.setText(playerName)

        Glide.with(binding.ivUpdateTeamLckCoachEditPhoto.context)
            .load(playerImageUrl)
            .into(binding.ivUpdateTeamLckCoachEditPhoto)
    }

    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckCoachEditCheck.setOnClickListener {
            val updatedName = binding.edUpdateTeamLckCoachEditName.text.toString()
            val updatedImageUrl = selectedImageUri ?: arguments?.getString("playerImageUrl") ?: ""

            val updatedRoaster = LckCoach(
                id = playerId ?: "",
                name = updatedName,
                imageUrl = updatedImageUrl
            )

            val action = UpdateTeamInfoLckCoachEditFragmentDirections
                .actionUpdateTeamInfoLckCoachEditFragmentToUpdateTeamInfoLckCoachFragment(
                    newPlayer = null,
                    updatedRoaster = updatedRoaster,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }
}

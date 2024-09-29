package umc.everyones.everyoneslckmanage.presentation.team

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterEditBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class UpdateTeamInfoLckRoasterEditFragment: BaseFragment<FragmentUpdateTeamInfoLckRoasterEditBinding>(R.layout.fragment_update_team_info_lck_roaster_edit) {

    private var playerId: String? = null
    private var selectedImageUri: String? = null

    private val viewModel: UpdateTeamInfoLckRoasterViewModel by activityViewModels()

    private val navigator by lazy {
        findNavController()
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivUpdateTeamLckRoasterEditPhoto.setImageURI(it)
            selectedImageUri = it.toString()
        }
    }
    override fun initObserver() {

    }

    override fun initView() {
        setupInitialData()
        setupCheckBoxListeners()
        setupSaveButtonListener()
        setupNoButtonListener()
        setupBackButtonListener()
        setTeamName()

        binding.ivUpdateTeamLckRoasterEditPhoto.setOnSingleClickListener  {
            openGallery()
        }
    }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setTeamName() {
        val teamName = viewModel.teamName ?: "Unknown Team"
        binding.tvUpdateTeamLckRoasterEditTeamName.text = teamName
    }
    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckRoasterEditPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckRoasterEditFragmentDirections
                .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckRoasterEditNo.setOnSingleClickListener {
            playerId?.let {
                viewModel.deletePlayer(it)
            }
            val action = UpdateTeamInfoLckRoasterEditFragmentDirections
                .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
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
        val playerPosition = arguments?.getString("playerPosition")
        val playerImageUrl = arguments?.getString("playerImageUrl")

        binding.edUpdateTeamLckRoasterEditName.setText(playerName)

        when (playerPosition) {
            "탑" -> binding.cbUpdateTeamLckRoasterEditPositionTop.isChecked = true
            "미드" -> binding.cbUpdateTeamLckRoasterEditPositionMid.isChecked = true
            "정글" -> binding.cbUpdateTeamLckRoasterEditPositionJungle.isChecked = true
            "원딜" -> binding.cbUpdateTeamLckRoasterEditPositionBot.isChecked = true
            "서폿" -> binding.cbUpdateTeamLckRoasterEditPositionSupport.isChecked = true
        }

        Glide.with(binding.ivUpdateTeamLckRoasterEditPhoto.context)
            .load(playerImageUrl)
            .into(binding.ivUpdateTeamLckRoasterEditPhoto)
    }

    private fun setupCheckBoxListeners() {
        val checkBoxes = listOf(
            binding.cbUpdateTeamLckRoasterEditPositionTop,
            binding.cbUpdateTeamLckRoasterEditPositionMid,
            binding.cbUpdateTeamLckRoasterEditPositionJungle,
            binding.cbUpdateTeamLckRoasterEditPositionBot,
            binding.cbUpdateTeamLckRoasterEditPositionSupport
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

    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckRoasterEditCheck.setOnClickListener {
            val updatedName = binding.edUpdateTeamLckRoasterEditName.text.toString()
            val updatedPosition = getSelectedPosition()
            val updatedImageUrl = selectedImageUri ?: arguments?.getString("playerImageUrl") ?: ""

            val updatedRoaster = LckRoaster(
                id = playerId ?: "",
                name = updatedName,
                position = updatedPosition,
                imageUrl = updatedImageUrl
            )

            val action = UpdateTeamInfoLckRoasterEditFragmentDirections
                .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = updatedRoaster,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun getSelectedPosition(): String {
        return when {
            binding.cbUpdateTeamLckRoasterEditPositionTop.isChecked -> "탑"
            binding.cbUpdateTeamLckRoasterEditPositionMid.isChecked -> "미드"
            binding.cbUpdateTeamLckRoasterEditPositionJungle.isChecked -> "정글"
            binding.cbUpdateTeamLckRoasterEditPositionBot.isChecked -> "원딜"
            binding.cbUpdateTeamLckRoasterEditPositionSupport.isChecked -> "서폿"
            else -> ""
        }
    }
}

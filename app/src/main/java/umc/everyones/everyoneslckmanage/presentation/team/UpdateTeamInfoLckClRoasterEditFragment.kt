package umc.everyones.everyoneslckmanage.presentation.team

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckClRoasterEditBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterEditBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class UpdateTeamInfoLckClRoasterEditFragment: BaseFragment<FragmentUpdateTeamInfoLckClRoasterEditBinding>(R.layout.fragment_update_team_info_lck_cl_roaster_edit) {

    private var playerId: String? = null
    private var selectedImageUri: String? = null

    private val viewModel: UpdateTeamInfoLckClRoasterViewModel by activityViewModels()

    private val navigator by lazy {
        findNavController()
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivUpdateTeamLckClRoasterEditPhoto.setImageURI(it)
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

        binding.ivUpdateTeamLckClRoasterEditPhoto.setOnSingleClickListener  {
            openGallery()
        }
    }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setTeamName() {
        val teamName = viewModel.teamName ?: "Unknown Team"
        binding.tvUpdateTeamLckClRoasterEditTeamName.text = teamName
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckClRoasterEditPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckClRoasterEditFragmentDirections
                .actionUpdateTeamInfoLckClRoasterEditFragmentToUpdateTeamInfoLckClRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckClRoasterEditNo.setOnSingleClickListener {
            playerId?.let {
                viewModel.deletePlayer(it)
            }
            val action = UpdateTeamInfoLckClRoasterEditFragmentDirections
                .actionUpdateTeamInfoLckClRoasterEditFragmentToUpdateTeamInfoLckClRoasterFragment(
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

        binding.edUpdateTeamLckClRoasterEditName.setText(playerName)

        when (playerPosition) {
            "탑" -> binding.cbUpdateTeamLckClRoasterEditPositionTop.isChecked = true
            "미드" -> binding.cbUpdateTeamLckClRoasterEditPositionMid.isChecked = true
            "정글" -> binding.cbUpdateTeamLckClRoasterEditPositionJungle.isChecked = true
            "원딜" -> binding.cbUpdateTeamLckClRoasterEditPositionBot.isChecked = true
            "서폿" -> binding.cbUpdateTeamLckClRoasterEditPositionSupport.isChecked = true
        }

        Glide.with(binding.ivUpdateTeamLckClRoasterEditPhoto.context)
            .load(playerImageUrl)
            .into(binding.ivUpdateTeamLckClRoasterEditPhoto)
    }

    private fun setupCheckBoxListeners() {
        val checkBoxes = listOf(
            binding.cbUpdateTeamLckClRoasterEditPositionTop,
            binding.cbUpdateTeamLckClRoasterEditPositionMid,
            binding.cbUpdateTeamLckClRoasterEditPositionJungle,
            binding.cbUpdateTeamLckClRoasterEditPositionBot,
            binding.cbUpdateTeamLckClRoasterEditPositionSupport
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
        binding.ivUpdateTeamLckClRoasterEditCheck.setOnClickListener {
            val updatedName = binding.edUpdateTeamLckClRoasterEditName.text.toString()
            val updatedPosition = getSelectedPosition()
            val updatedImageUrl = selectedImageUri ?: arguments?.getString("playerImageUrl") ?: ""

            val updatedRoaster = LckClRoaster(
                id = playerId ?: "",
                name = updatedName,
                position = updatedPosition,
                imageUrl = updatedImageUrl
            )

            val action = UpdateTeamInfoLckClRoasterEditFragmentDirections
                .actionUpdateTeamInfoLckClRoasterEditFragmentToUpdateTeamInfoLckClRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = updatedRoaster,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun getSelectedPosition(): String {
        return when {
            binding.cbUpdateTeamLckClRoasterEditPositionTop.isChecked -> "탑"
            binding.cbUpdateTeamLckClRoasterEditPositionMid.isChecked -> "미드"
            binding.cbUpdateTeamLckClRoasterEditPositionJungle.isChecked -> "정글"
            binding.cbUpdateTeamLckClRoasterEditPositionBot.isChecked -> "원딜"
            binding.cbUpdateTeamLckClRoasterEditPositionSupport.isChecked -> "서폿"
            else -> ""
        }
    }
}

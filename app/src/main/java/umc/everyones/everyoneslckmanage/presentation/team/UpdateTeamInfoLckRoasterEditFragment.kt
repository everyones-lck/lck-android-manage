package umc.everyones.everyoneslckmanage.presentation.team

import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterEditBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.io.File

class UpdateTeamInfoLckRoasterEditFragment: BaseFragment<FragmentUpdateTeamInfoLckRoasterEditBinding>(R.layout.fragment_update_team_info_lck_roaster_edit) {

    private var playerId: Int? = null
    private var selectedImageFile: File? = null

    private val viewModel: UpdateTeamInfoLckRoasterEditViewModel by activityViewModels()
    private val args: UpdateTeamInfoLckRoasterEditFragmentArgs by navArgs()

    private val navigator by lazy {
        findNavController()
    }

    override fun initObserver() {
        lifecycleScope.launch {
            viewModel.deletePlayer.collectLatest { result ->
                result?.onSuccess {
                    Log.d("DeletePlayer", "Player successfully deleted")
                    val action = UpdateTeamInfoLckRoasterEditFragmentDirections
                        .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
                            newPlayer = null,
                            updatedRoaster = null,
                            teamName = null,
                            teamId = -1
                        )
                    navigator.navigate(action)
                }?.onFailure { error ->
                    Log.e("DeletePlayer", "Error deleting player: ${error.message}")
                }
            }
        }
        lifecycleScope.launch {
            viewModel.updateResult.collectLatest { result ->
                result?.onSuccess {
                    Log.d("UpdatePlayer", "Player successfully updated")
                    val action = UpdateTeamInfoLckRoasterEditFragmentDirections
                        .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
                            newPlayer = null,
                            updatedRoaster = null,
                            teamName = args.teamName,
                            teamId = args.teamId
                        )
                    navigator.navigate(action)
                }?.onFailure { error ->
                    Log.e("UpdatePlayer", "Error updating player: ${error.message}")
                }
            }
        }
    }

    override fun initView() {
        playerId = arguments?.getInt("playerId")
        setupTeamName()
        setupInitialData()
        setupSaveButtonListener()
        setupNoButtonListener()
        setupBackButtonListener()
        setupTeamName()

        binding.ivUpdateTeamLckRoasterEditGallery.setOnSingleClickListener  {
            openGallery()
        }
    }

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.ivUpdateTeamLckRoasterEditPhoto.setImageURI(it)
                selectedImageFile = uriToFile(it) ?: run {
                    null
                }
            }
        }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setupTeamName() {
        val teamName = arguments?.getString("teamName") ?: "Unknown Team"
        binding.tvUpdateTeamLckRoasterEditTeamName.text = teamName
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckRoasterEditPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckRoasterEditFragmentDirections
                .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = args.teamName,
                    teamId = args.teamId
                )
            navigator.navigate(action)
        }
    }

    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckRoasterEditNo.setOnSingleClickListener {
            playerId?.let { id ->
                viewModel.deletePlayer(id)
            }
        }
    }

    private fun setupInitialData() {
        playerId = arguments?.getInt("playerId")
        val playerName = arguments?.getString("playerName")
        val playerPosition = arguments?.getString("playerPosition")
        val playerImageUrl = arguments?.getString("playerImageUrl")

        binding.etUpdateTeamLckRoasterEditName.setText(playerName)
        binding.etUpdateTeamLckRoasterEditPosition.setText(playerPosition)

        Glide.with(binding.ivUpdateTeamLckRoasterEditPhoto.context)
            .load(playerImageUrl)
            .into(binding.ivUpdateTeamLckRoasterEditPhoto)
    }

    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckRoasterEditCheck.setOnSingleClickListener {
            val updatedName = binding.etUpdateTeamLckRoasterEditName.text.toString()
            val updatedRealName = binding.etUpdateTeamLckRoasterEditNickName.text.toString()
            val updatedPosition = binding.etUpdateTeamLckRoasterEditPosition.text.toString()
            val updatedBirthday = binding.etUpdateTeamLckRoasterEditBirthDate.text.toString()

            playerId?.let { id ->
                viewModel.updatePlayer(
                    profileImageFile = selectedImageFile,
                    playerId = id,
                    name = updatedName,
                    realName = updatedRealName,
                    position = updatedPosition,
                    birthday = updatedBirthday
                )
            }
        }
    }

    private fun uriToFile(uri: Uri): File? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("temp_image", ".jpg", requireContext().cacheDir)
            tempFile.outputStream().use { outputStream ->
                inputStream?.copyTo(outputStream)
            }
            tempFile
        } catch (e: Exception) {
            Log.e("EditPlayer", "Error converting URI to File: ${e.message}")
            null
        }
    }

}

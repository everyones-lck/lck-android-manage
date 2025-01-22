package umc.everyones.everyoneslckmanage.presentation.team

import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.flow.collect
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterAddBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.io.File

class UpdateTeamInfoLckRoasterAddFragment :
    BaseFragment<FragmentUpdateTeamInfoLckRoasterAddBinding>(R.layout.fragment_update_team_info_lck_roaster_add) {

    private var selectedImageFile: File? = null

    private val viewModel: UpdateTeamInfoLckRoasterAddViewModel by activityViewModels()
    private val args: UpdateTeamInfoLckRoasterAddFragmentArgs by navArgs()

    private val navigator by lazy {
        findNavController()
    }


    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.addPlayerResult.collect { result ->
                result?.onSuccess {
                    val action = UpdateTeamInfoLckRoasterAddFragmentDirections
                        .actionUpdateTeamInfoLckRoasterAddFragmentToUpdateTeamInfoLckRoasterFragment(
                            newPlayer = null,
                            updatedRoaster = null,
                            teamName = args.teamName,
                            teamId = args.teamId
                        )
                    navigator.navigate(action)
                }?.onFailure { error ->
                    Log.e("AddPlayer", "Error adding player: ${error.message}")
                }
            }
        }
    }

    override fun initView() {
        setupSaveButtonListener()
        setupBackButtonListener()
        setupNoButtonListener()

        binding.ivUpdateTeamLckRoasterAddGallery.setOnClickListener {
            openGallery()
        }

        binding.tvUpdateTeamLckRoasterAddTeamName.text = args.teamName
    }

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.ivUpdateTeamLckRoasterAddPhoto.setImageURI(it)
                selectedImageFile = uriToFile(it) ?: run {
                    null
                }
            }
        }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckRoasterAddPrevious.setOnSingleClickListener {
            val action = UpdateTeamInfoLckRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckRoasterAddFragmentToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = args.teamName,
                    teamId = args.teamId
                )
            navigator.navigate(action)
        }
    }

    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckRoasterAddNo.setOnSingleClickListener {
            val action = UpdateTeamInfoLckRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckRoasterAddFragmentToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = args.teamName,
                    teamId = args.teamId
                )
            navigator.navigate(action)
        }
    }

    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckRoasterAddCheck.setOnSingleClickListener {
            val name = binding.etUpdateTeamLckRoasterAddName.text.toString()
            val realName = binding.etUpdateTeamLckRoasterAddNickName.text.toString()
            val position = binding.etUpdateTeamLckRoasterAddPosition.text.toString()
            val birth = binding.etUpdateTeamLckRoasterAddBirthDate.text.toString()

            if (name.isNotEmpty() && realName.isNotEmpty() && position.isNotEmpty() && birth.isNotEmpty()) {
                viewModel.addPlayer(
                    profileImageFile = selectedImageFile,
                    teamId = args.teamId,
                    name = name,
                    realName = realName,
                    position = position,
                    birth = birth
                )
            } else {
                Log.e("AddPlayer", "Invalid input fields")
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
            Log.e("AddPlayer", "Error converting URI to File: ${e.message}")
            null
        }
    }
}

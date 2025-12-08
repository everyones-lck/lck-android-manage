package umc.everyones.everyoneslckmanage.presentation.team

import android.net.Uri
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckCoachAddBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.io.File

class UpdateTeamInfoLckCoachAddFragment :
    BaseFragment<FragmentUpdateTeamInfoLckCoachAddBinding>(R.layout.fragment_update_team_info_lck_coach_add) {

    private var selectedImageFile: File? = null

    private val viewModel: UpdateTeamInfoLckCoachAddViewModel by activityViewModels()
    private val args: UpdateTeamInfoLckCoachAddFragmentArgs by navArgs()

    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.addCoachResult.collect { result ->
                result?.onSuccess {
                    viewModel.reset()

                    val action =
                        UpdateTeamInfoLckCoachAddFragmentDirections
                            .actionUpdateTeamInfoLckCoachAddFragmentToUpdateTeamInfoLckCoachFragment(
                                teamName = args.teamName,
                                teamId = args.teamId
                            )
                    navigator.navigate(action)

                }?.onFailure { error ->
                    Log.e("CoachAdd", "Error adding coach: ${error.message}")
                }
            }
        }
    }

    override fun initView() {
        binding.tvUpdateTeamLckCoachAddTeamName.text = args.teamName

        setupSaveButtonListener()
        setupBackButtonListener()
        setupNoButtonListener()

        binding.ivUpdateTeamLckCoachAddGallery.setOnClickListener {
            openGallery()
        }
    }

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.ivUpdateTeamLckCoachAddPhoto.setImageURI(it)
                selectedImageFile = uriToFile(it)
            }
        }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckCoachAddPrevious.setOnSingleClickListener {
            val action =
                UpdateTeamInfoLckCoachAddFragmentDirections
                    .actionUpdateTeamInfoLckCoachAddFragmentToUpdateTeamInfoLckCoachFragment(
                        teamName = args.teamName,
                        teamId = args.teamId
                    )
            navigator.navigate(action)
        }
    }

    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckCoachAddNo.setOnSingleClickListener {
            val action =
                UpdateTeamInfoLckCoachAddFragmentDirections
                    .actionUpdateTeamInfoLckCoachAddFragmentToUpdateTeamInfoLckCoachFragment(
                        teamName = args.teamName,
                        teamId = args.teamId
                    )
            navigator.navigate(action)
        }
    }

    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckCoachAddCheck.setOnSingleClickListener {
            val name = binding.etUpdateTeamLckCoachAddName.text.toString()
            val realName = binding.etUpdateTeamLckCoachAddNickName.text.toString()
            val birth = binding.etUpdateTeamLckCoachAddBirthDate.text.toString()

            if (name.isNotEmpty() && realName.isNotEmpty() && birth.isNotEmpty()) {
                viewModel.addCoach(
                    profileImageFile = selectedImageFile,
                    teamId = args.teamId,
                    name = name,
                    realName = realName,
                    birth = birth
                )
            } else {
                Log.e("CoachAdd", "Invalid input fields")
            }
        }
    }

    private fun uriToFile(uri: Uri): File? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("coach_image", ".jpg", requireContext().cacheDir)
            tempFile.outputStream().use { outputStream ->
                inputStream?.copyTo(outputStream)
            }
            tempFile
        } catch (e: Exception) {
            Log.e("CoachAdd", "Error converting URI to File: ${e.message}")
            null
        }
    }
}

package umc.everyones.everyoneslckmanage.presentation.team

import android.net.Uri
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckCoachEditBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterEditBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.io.File

@AndroidEntryPoint
class UpdateTeamInfoLckCoachEditFragment :
    BaseFragment<FragmentUpdateTeamInfoLckCoachEditBinding>(R.layout.fragment_update_team_info_lck_coach_edit) {

    private var playerId: Int? = null
    private var selectedImageFile: File? = null

    private var originalName: String? = null
    private var originalRealName: String? = null
    private var originalBirth: String? = null
    private var originalImageUrl: String? = null
    private var teamName: String? = null
    private var teamId: Int? = null

    private val viewModel: UpdateTeamInfoLckCoachEditViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        lifecycleScope.launch {
            viewModel.updateResult.collect { result ->
                result?.onSuccess {
                    viewModel.resetUpdateResult()
                    navigator.navigate(
                        UpdateTeamInfoLckCoachEditFragmentDirections
                            .actionUpdateTeamInfoLckCoachEditFragmentToUpdateTeamInfoLckCoachFragment(
                                teamName = teamName,
                                teamId = teamId ?: -1
                            )
                    )
                }
            }
        }

        lifecycleScope.launch {
            viewModel.deleteResult.collect { result ->
                result?.onSuccess {
                    navigator.navigate(
                        UpdateTeamInfoLckCoachEditFragmentDirections
                            .actionUpdateTeamInfoLckCoachEditFragmentToUpdateTeamInfoLckCoachFragment(
                                teamName = teamName,
                                teamId = teamId ?: -1
                            )
                    )
                }
            }
        }
    }

    override fun initView() {
        loadArgs()
        setupTeamName()
        setupInitialUI()
        setupClickListeners()
    }

    private fun loadArgs() {
        arguments?.let {
            playerId = it.getInt("playerId")
            originalName = it.getString("playerName")
            originalRealName = it.getString("playerRealName")
            originalBirth = it.getString("playerBirth")
            originalImageUrl = it.getString("playerImageUrl")
            teamName = it.getString("teamName")
            teamId = it.getInt("teamId")
        }
    }

    private fun setupTeamName() {
        binding.tvUpdateTeamLckCoachEditTeamName.text = teamName ?: "Unknown Team"
    }

    private fun setupInitialUI() {
        binding.etUpdateTeamLckCoachEditName.setText(originalName)
        binding.etUpdateTeamLckCoachEditNickName.setText(originalRealName)
        binding.etUpdateTeamLckCoachEditBirthDate.setText(originalBirth)

        Glide.with(this)
            .load(originalImageUrl)
            .into(binding.ivUpdateTeamLckCoachEditPhoto)
    }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                selectedImageFile = uriToFile(uri)
                binding.ivUpdateTeamLckCoachEditPhoto.setImageURI(uri)
            }
        }

    private fun openGallery() = galleryLauncher.launch("image/*")

    private fun setupClickListeners() {
        binding.ivUpdateTeamLckCoachEditGallery.setOnSingleClickListener { openGallery() }

        binding.ivUpdateTeamLckCoachEditPrevious.setOnSingleClickListener {
            navigator.navigate(
                UpdateTeamInfoLckCoachEditFragmentDirections
                    .actionUpdateTeamInfoLckCoachEditFragmentToUpdateTeamInfoLckCoachFragment(
                        teamName = teamName,
                        teamId = teamId ?: -1
                    )
            )
        }

        binding.ivUpdateTeamLckCoachEditCheck.setOnSingleClickListener {

            val newName = binding.etUpdateTeamLckCoachEditName.text.toString()
            val newRealName = binding.etUpdateTeamLckCoachEditNickName.text.toString()
            val newBirth = binding.etUpdateTeamLckCoachEditBirthDate.text.toString()

            playerId?.let { id ->
                viewModel.updateCoach(
                    profileImageFile = selectedImageFile,
                    playerId = id,
                    name = newName,
                    realName = newRealName,
                    birth = newBirth
                )
            }
        }

        binding.ivUpdateTeamLckCoachEditNo.setOnSingleClickListener {
            playerId?.let { id -> viewModel.deleteCoach(id) }
        }
    }

    private fun uriToFile(uri: Uri): File? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val file = File.createTempFile("coach_img", ".jpg", requireContext().cacheDir)
            inputStream?.copyTo(file.outputStream())
            file
        } catch (e: Exception) {
            null
        }
    }
}

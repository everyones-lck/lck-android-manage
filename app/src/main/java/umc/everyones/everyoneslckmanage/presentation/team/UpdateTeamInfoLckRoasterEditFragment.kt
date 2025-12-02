package umc.everyones.everyoneslckmanage.presentation.team

import android.net.Uri
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterEditBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.io.File

class UpdateTeamInfoLckRoasterEditFragment :
    BaseFragment<FragmentUpdateTeamInfoLckRoasterEditBinding>(R.layout.fragment_update_team_info_lck_roaster_edit) {

    private var playerId: Int? = null
    private var selectedImageFile: File? = null

    private var originalName: String? = null
    private var originalRealName: String? = null
    private var originalPosition: String? = null
    private var originalBirthday: String? = null
    private var originalImageUrl: String? = null
    private var teamName: String? = null
    private var teamId: Int? = null

    private val viewModel: UpdateTeamInfoLckRoasterEditViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        lifecycleScope.launch {
            viewModel.deletePlayer.collectLatest { result ->
                result?.onSuccess {
                    navigator.navigate(
                        UpdateTeamInfoLckRoasterEditFragmentDirections
                            .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
                                teamName = teamName,
                                teamId = teamId ?: -1
                            )
                    )
                }?.onFailure { error ->
                    Log.e("DeletePlayer", "Error deleting player: ${error.message}")
                }
            }
        }

        lifecycleScope.launch {
            viewModel.updateResult.collectLatest { result ->
                result?.onSuccess {
                    viewModel.resetUpdateResult()
                    navigator.navigate(
                        UpdateTeamInfoLckRoasterEditFragmentDirections
                            .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
                                teamName = teamName,
                                teamId = teamId ?: -1
                            )
                    )
                }?.onFailure { error ->
                    Log.e("UpdatePlayer", "Error updating player: ${error.message}")
                }
            }
        }
    }

    override fun initView() {
        loadBundleData()
        setupTeamName()
        setupInitialData()
        setupClickListeners()
        setupPositionDropdown()
    }

    private fun loadBundleData() {
        arguments?.let { bundle ->
            playerId = bundle.getInt("playerId")
            originalName = bundle.getString("playerName")
            originalRealName = bundle.getString("playerRealName")
            originalPosition = bundle.getString("playerPosition")   // 영어
            originalBirthday = bundle.getString("playerBirth")
            originalImageUrl = bundle.getString("playerImageUrl")
            teamName = bundle.getString("teamName")
            teamId = bundle.getInt("teamId")
        }
    }

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.ivUpdateTeamLckRoasterEditPhoto.setImageURI(it)
                selectedImageFile = uriToFile(it)
            }
        }

    private fun openGallery() = selectImageLauncher.launch("image/*")

    private fun setupTeamName() {
        binding.tvUpdateTeamLckRoasterEditTeamName.text = teamName ?: "Unknown Team"
    }

    private fun setupInitialData() {
        binding.etUpdateTeamLckRoasterEditName.setText(originalName)
        binding.etUpdateTeamLckRoasterEditNickName.setText(originalRealName)
        binding.etUpdateTeamLckRoasterEditBirthDate.setText(originalBirthday)

        binding.actvUpdateTeamLckRoasterEditPosition.setText(convertEngToKor(originalPosition))

        Glide.with(this)
            .load(originalImageUrl)
            .into(binding.ivUpdateTeamLckRoasterEditPhoto)
    }

    private fun setupPositionDropdown() {
        val items = resources.getStringArray(R.array.player_positions)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        binding.actvUpdateTeamLckRoasterEditPosition.setAdapter(adapter)
    }

    private fun setupClickListeners() {
        binding.ivUpdateTeamLckRoasterEditGallery.setOnSingleClickListener { openGallery() }

        binding.ivUpdateTeamLckRoasterEditPrevious.setOnSingleClickListener {
            navigator.navigate(
                UpdateTeamInfoLckRoasterEditFragmentDirections
                    .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
                        teamName = teamName,
                        teamId = teamId ?: -1
                    )
            )
        }

        binding.ivUpdateTeamLckRoasterEditCheck.setOnSingleClickListener {
            val newName = binding.etUpdateTeamLckRoasterEditName.text.toString()
            val newRealName = binding.etUpdateTeamLckRoasterEditNickName.text.toString()
            val newKorPosition = binding.actvUpdateTeamLckRoasterEditPosition.text.toString()
            val newEngPosition = convertKorToEng(newKorPosition)
            val newBirthday = binding.etUpdateTeamLckRoasterEditBirthDate.text.toString()

            val nameToSend = if (newName != originalName) newName else null
            val realNameToSend = if (newRealName != originalRealName) newRealName else null
            val positionToSend = if (newEngPosition != originalPosition) newEngPosition else null
            val birthdayToSend = if (newBirthday != originalBirthday) newBirthday else null

            playerId?.let { id ->
                viewModel.updatePlayer(
                    profileImageFile = selectedImageFile,
                    playerId = id,
                    name = nameToSend,
                    realName = realNameToSend,
                    position = positionToSend,
                    birthday = birthdayToSend
                )
            }
        }

        binding.ivUpdateTeamLckRoasterEditNo.setOnSingleClickListener {
            playerId?.let { id -> viewModel.deletePlayer(id.toLong()) }
        }
    }

    private fun uriToFile(uri: Uri): File? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("temp", ".jpg", requireContext().cacheDir)
            inputStream?.copyTo(tempFile.outputStream())
            tempFile
        } catch (e: Exception) {
            Log.e("EditPlayer", "URI → File 변환 실패: ${e.message}")
            null
        }
    }

    private fun convertKorToEng(kor: String?): String? = when (kor) {
        "탑" -> "TOP"
        "정글" -> "JUNGLE"
        "미드" -> "MID"
        "바텀" -> "BOT"
        "서포터" -> "SUPPORT"
        else -> null
    }

    private fun convertEngToKor(eng: String?): String = when (eng) {
        "TOP" -> "탑"
        "JUNGLE" -> "정글"
        "MID" -> "미드"
        "BOT" -> "바텀"
        "SUPPORT" -> "서포터"
        else -> ""
    }
}

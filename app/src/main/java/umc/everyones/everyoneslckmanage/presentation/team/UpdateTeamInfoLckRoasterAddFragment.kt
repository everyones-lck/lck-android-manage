package umc.everyones.everyoneslckmanage.presentation.team

import android.net.Uri
import android.util.Log
import android.widget.ArrayAdapter
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
                    viewModel.resetUpdateResult()  // 성공 후 상태 리셋
                    val action = UpdateTeamInfoLckRoasterAddFragmentDirections
                        .actionUpdateTeamInfoLckRoasterAddFragmentToUpdateTeamInfoLckRoasterFragment(
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
        setupPositionDropdown()

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
                    teamName = args.teamName,
                    teamId = args.teamId
                )
            navigator.navigate(action)
        }
    }

    private fun convertPositionToEnglish(kor: String): String {
        return when (kor) {
            "탑" -> "TOP"
            "미드" -> "MID"
            "정글" -> "JUNGLE"
            "바텀" -> "BOT"
            "서포터" -> "SUPPORT"
            else -> "TOP"
        }
    }

    private fun setupPositionDropdown() {
        val items = resources.getStringArray(R.array.player_positions)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)

        val actv = binding.actvUpdateTeamLckRoasterAddPosition
        val arrow = binding.ivPositionArrow
        val box = binding.llPositionBox

        actv.setAdapter(adapter)

        var isOpen = false
        var blockToggle = false

        fun toggle() {
            if (blockToggle) return

            if (isOpen) {
                actv.dismissDropDown()
                arrow.rotation = 0f
            } else {
                actv.showDropDown()
                arrow.rotation = 180f
            }
            isOpen = !isOpen
        }

        box.setOnClickListener { toggle() }
        arrow.setOnClickListener { toggle() }
        actv.setOnClickListener { toggle() }

        actv.setOnItemClickListener { _, _, _, _ ->
            actv.dismissDropDown()
        }

        actv.setOnDismissListener {
            isOpen = false
            arrow.rotation = 0f

            blockToggle = true
            actv.postDelayed({
                blockToggle = false
            }, 150)
        }
    }

    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckRoasterAddCheck.setOnSingleClickListener {
            val name = binding.etUpdateTeamLckRoasterAddName.text.toString()
            val realName = binding.etUpdateTeamLckRoasterAddNickName.text.toString()
            val korPosition = binding.actvUpdateTeamLckRoasterAddPosition.text.toString()
            val engPosition = convertPositionToEnglish(korPosition)
            val rawBirth = binding.etUpdateTeamLckRoasterAddBirthDate.text.toString().trim()

            val birth = if (rawBirth.length == 8 && rawBirth.all { it.isDigit() }) {
                "${rawBirth.substring(0, 4)}-${rawBirth.substring(4, 6)}-${rawBirth.substring(6, 8)}"
            } else {
                rawBirth
            }

            if (name.isNotEmpty() && realName.isNotEmpty() && engPosition.isNotEmpty() && birth.isNotEmpty()) {
                viewModel.addPlayer(
                    profileImageFile = selectedImageFile,
                    teamId = args.teamId,
                    name = name,
                    realName = realName,
                    position = engPosition,
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

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("FragmentLifecycle", "onDestroyView called for ${javaClass.simpleName}")
    }

}

package umc.everyones.everyoneslckmanage.presentation.team

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckClRoasterAddBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import umc.everyones.lck.data.dto.response.community.ReadCommunityResponseDto
import java.io.File

@AndroidEntryPoint
class UpdateTeamInfoLckClRoasterAddFragment :
    BaseFragment<FragmentUpdateTeamInfoLckClRoasterAddBinding>(R.layout.fragment_update_team_info_lck_cl_roaster_add) {

    private var selectedImageFile: File? = null

    private val viewModel: UpdateTeamInfoLckClRoasterAddViewModel by activityViewModels()
    private val args: UpdateTeamInfoLckClRoasterAddFragmentArgs by navArgs()

    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.addPlayerResult.collect { result ->
                result?.onSuccess {
                    viewModel.resetUpdateResult()
                    val action = UpdateTeamInfoLckClRoasterAddFragmentDirections
                        .actionUpdateTeamInfoLckClRoasterAddFragmentToUpdateTeamInfoLckClRoasterFragment(
                            teamName = args.teamName,
                            teamId = args.teamId
                        )
                    navigator.navigate(action)

                }?.onFailure { error ->
                    Log.e("AddPlayer_CL", "Error adding player: ${error.message}")
                }
            }
        }
    }

    override fun initView() {
        binding.tvUpdateTeamLckClRoasterAddTeamName.text = args.teamName

        setupSaveButtonListener()
        setupBackButtonListener()
        setupNoButtonListener()
        setupPositionDropdown(
            actv = binding.actvUpdateTeamLckRoasterAddPosition,
            arrow = binding.ivPositionArrow,
            box = binding.llPositionBox
        )

        binding.ivUpdateTeamLckClRoasterAddGallery.setOnClickListener {
            openGallery()
        }
    }

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.ivUpdateTeamLckClRoasterAddPhoto.setImageURI(it)
                selectedImageFile = uriToFile(uri)
            }
        }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckClRoasterAddPrevious.setOnSingleClickListener {
            navigator.navigate(
                UpdateTeamInfoLckClRoasterAddFragmentDirections
                    .actionUpdateTeamInfoLckClRoasterAddFragmentToUpdateTeamInfoLckClRoasterFragment(
                        teamName = args.teamName,
                        teamId = args.teamId
                    )
            )
        }
    }

    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckClRoasterAddNo.setOnSingleClickListener {
            navigator.navigate(
                UpdateTeamInfoLckClRoasterAddFragmentDirections
                    .actionUpdateTeamInfoLckClRoasterAddFragmentToUpdateTeamInfoLckClRoasterFragment(
                        teamName = args.teamName,
                        teamId = args.teamId
                    )
            )
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

    private fun setupPositionDropdown(
        actv: AutoCompleteTextView,
        arrow: ImageView,
        box: View
    ) {
        val items = resources.getStringArray(R.array.player_positions)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        actv.setAdapter(adapter)

        var isOpen = false
        var blockToggle = false

        /** 토글 함수 **/
        fun toggle() {
            if (blockToggle) return

            if (isOpen) {
                actv.dismissDropDown()
                arrow.animate().rotation(0f).setDuration(150).start()
            } else {
                actv.requestFocus()
                actv.showDropDown()
                arrow.animate().rotation(180f).setDuration(150).start()
            }

            isOpen = !isOpen
        }

        /** 클릭 이벤트 지정 (3곳 모두) **/
        box.setOnClickListener { toggle() }
        arrow.setOnClickListener { toggle() }
        actv.setOnClickListener { toggle() }

        /** 메뉴 아이템 선택 시 닫힘 **/
        actv.setOnItemClickListener { _, _, _, _ ->
            actv.dismissDropDown()
        }

        /** dismiss 되었을 때 상태 초기화 **/
        actv.setOnDismissListener {
            isOpen = false
            arrow.animate().rotation(0f).setDuration(150).start()

            blockToggle = true
            actv.postDelayed({
                blockToggle = false
            }, 150)
        }
    }


    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckClRoasterAddCheck.setOnSingleClickListener {
            val name = binding.etUpdateTeamLckClRoasterAddName.text.toString()
            val realName = binding.etUpdateTeamLckClRoasterAddNickName.text.toString()
            val birth = binding.etUpdateTeamLckClRoasterAddBirthDate.text.toString()
            val korPosition = binding.actvUpdateTeamLckRoasterAddPosition.text.toString()
            val engPosition = convertPositionToEnglish(korPosition)

            if (name.isNotEmpty() && realName.isNotEmpty() && engPosition.isNotEmpty() && birth.isNotEmpty()) {
                viewModel.addPlayer(
                    profileImageFile = selectedImageFile,
                    teamId = args.teamId,
                    name = name,
                    realName = realName,
                    position = engPosition,
                    birth = birth,
                    role = "LCK_CL_ROSTER"
                )
            } else {
                Log.e("AddPlayer_CL", "Invalid input fields")
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

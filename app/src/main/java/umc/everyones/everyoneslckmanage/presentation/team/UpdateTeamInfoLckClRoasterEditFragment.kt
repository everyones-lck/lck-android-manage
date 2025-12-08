package umc.everyones.everyoneslckmanage.presentation.team

import android.net.Uri
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckClRoasterEditBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterEditBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.io.File

class UpdateTeamInfoLckClRoasterEditFragment :
    BaseFragment<FragmentUpdateTeamInfoLckClRoasterEditBinding>(R.layout.fragment_update_team_info_lck_cl_roaster_edit) {

    private var playerId: Int? = null
    private var selectedImageFile: File? = null

    private var originalName: String? = null
    private var originalRealName: String? = null
    private var originalPosition: String? = null
    private var originalBirthday: String? = null
    private var originalImageUrl: String? = null
    private var teamName: String? = null
    private var teamId: Int? = null

    private val viewModel: UpdateTeamInfoLckClRoasterEditViewModel by activityViewModels()
    private val navigator by lazy { findNavController() }

    override fun initObserver() {
        lifecycleScope.launch {
            viewModel.deletePlayer.collectLatest { result ->
                result?.onSuccess {
                    navigator.navigate(
                        UpdateTeamInfoLckClRoasterEditFragmentDirections
                            .actionUpdateTeamInfoLckClRoasterEditFragmentToUpdateTeamInfoLckClRoasterFragment(
                                teamName = teamName,
                                teamId = teamId ?: -1
                            )
                    )
                }
            }
        }

        lifecycleScope.launch {
            viewModel.updateResult.collectLatest { result ->
                result?.onSuccess {
                    viewModel.resetUpdateResult()
                    navigator.navigate(
                        UpdateTeamInfoLckClRoasterEditFragmentDirections
                            .actionUpdateTeamInfoLckClRoasterEditFragmentToUpdateTeamInfoLckClRoasterFragment(
                                teamName = teamName,
                                teamId = teamId ?: -1
                            )
                    )
                }
            }
        }
    }

    override fun initView() {
        loadBundleData()
        setupTeamName()
        setupInitialData()
        setupClickListeners()
        setupPositionDropdown(
            actv = binding.actvUpdateTeamLckRoasterEditPosition,
            arrow = binding.ivPositionArrow,
            box = binding.llPositionBox
        )
    }

    private fun loadBundleData() {
        arguments?.let { bundle ->
            playerId = bundle.getInt("playerId")
            originalName = bundle.getString("playerName")
            originalRealName = bundle.getString("playerRealName")
            originalPosition = bundle.getString("playerPosition")
            originalBirthday = bundle.getString("playerBirth")
            originalImageUrl = bundle.getString("playerImageUrl")
            teamName = bundle.getString("teamName")
            teamId = bundle.getInt("teamId")
        }
    }

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding.ivUpdateTeamLckClRoasterEditPhoto.setImageURI(it)
                selectedImageFile = uriToFile(it)
            }
        }

    private fun openGallery() = selectImageLauncher.launch("image/*")

    private fun setupTeamName() {
        binding.tvUpdateTeamLckClRoasterEditTeamName.text = teamName ?: "Unknown Team"
    }

    private fun setupInitialData() {
        binding.etUpdateTeamLckClRoasterEditName.setText(originalName)
        binding.etUpdateTeamLckClRoasterEditNickName.setText(originalRealName)
        binding.etUpdateTeamLckClRoasterEditBirthDate.setText(originalBirthday)
        binding.actvUpdateTeamLckRoasterEditPosition.setText(convertEngToKor(originalPosition))

        Glide.with(this)
            .load(originalImageUrl)
            .into(binding.ivUpdateTeamLckClRoasterEditPhoto)
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

    private fun setupClickListeners() {
        binding.ivUpdateTeamLckClRoasterEditGallery
            .setOnSingleClickListener { openGallery() }

        binding.ivUpdateTeamLckClRoasterEditPrevious
            .setOnSingleClickListener {
                navigator.navigate(
                    UpdateTeamInfoLckClRoasterEditFragmentDirections
                        .actionUpdateTeamInfoLckClRoasterEditFragmentToUpdateTeamInfoLckClRoasterFragment(
                            teamName = teamName,
                            teamId = teamId ?: -1
                        )
                )
            }

        binding.ivUpdateTeamLckClRoasterEditCheck.setOnSingleClickListener {
            val newName = binding.etUpdateTeamLckClRoasterEditName.text.toString()
            val newRealName = binding.etUpdateTeamLckClRoasterEditNickName.text.toString()
            val newBirthday = binding.etUpdateTeamLckClRoasterEditBirthDate.text.toString()

            val newKorPosition = binding.actvUpdateTeamLckRoasterEditPosition.text.toString()
            val newEngPosition = convertKorToEng(newKorPosition)

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
                    birthday = birthdayToSend,
                    role = "LCK_CL_ROSTER"
                )
            }
        }

        binding.ivUpdateTeamLckClRoasterEditNo.setOnSingleClickListener {
            playerId?.let { id ->
                viewModel.deletePlayer(id.toLong())
            }
        }
    }

    private fun uriToFile(uri: Uri): File? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("tempCl", ".jpg", requireContext().cacheDir)
            inputStream?.copyTo(tempFile.outputStream())
            tempFile
        } catch (e: Exception) {
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

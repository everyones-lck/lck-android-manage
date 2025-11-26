    package umc.everyones.everyoneslckmanage.presentation.team

    import android.content.Context
    import android.net.Uri
    import android.util.Log
    import android.widget.ArrayAdapter
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
                        viewModel.resetUpdateResult()
                        val action = UpdateTeamInfoLckRoasterEditFragmentDirections
                            .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
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
            setupClickListeners()
            setupTeamName()
            setupPositionDropdown()
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

        private fun setupInitialData() {
            playerId = arguments?.getInt("playerId")
            val playerName = arguments?.getString("playerName")
            val playerPosition = arguments?.getString("playerPosition")
            val playerImageUrl = arguments?.getString("playerImageUrl")

            binding.etUpdateTeamLckRoasterEditName.setText(playerName)
            binding.actvUpdateTeamLckRoasterEditPosition.setText(playerPosition)
            Glide.with(binding.ivUpdateTeamLckRoasterEditPhoto.context)
                .load(playerImageUrl)
                .into(binding.ivUpdateTeamLckRoasterEditPhoto)

            savePlayerImageUrl(playerImageUrl)
        }

        private fun setupPositionDropdown() {
            val items = resources.getStringArray(R.array.player_positions)
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)

            val actv = binding.actvUpdateTeamLckRoasterEditPosition
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

        private fun setupClickListeners() {
            binding.ivUpdateTeamLckRoasterEditGallery.setOnSingleClickListener {
                openGallery()
            }

            binding.ivUpdateTeamLckRoasterEditPrevious.setOnSingleClickListener {
                val action = UpdateTeamInfoLckRoasterEditFragmentDirections
                    .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
                        teamName = args.teamName,
                        teamId = args.teamId
                    )
                navigator.navigate(action)
            }

            binding.ivUpdateTeamLckRoasterEditNo.setOnSingleClickListener {
                playerId?.let { id ->
                    viewModel.deletePlayer(id.toLong())
                }
            }

            binding.ivUpdateTeamLckRoasterEditCheck.setOnSingleClickListener {
                val updatedName = binding.etUpdateTeamLckRoasterEditName.text.toString()
                val updatedRealName = binding.etUpdateTeamLckRoasterEditNickName.text.toString()
                val updatedPosition = binding.actvUpdateTeamLckRoasterEditPosition.text.toString()
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

        private fun savePlayerImageUrl(imageUrl: String?) {
            val sharedPreferences = requireActivity().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().apply {
                putString("playerImageUrl", imageUrl)
                apply()
            }
        }

    }

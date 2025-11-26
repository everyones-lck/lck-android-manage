package umc.everyones.everyoneslckmanage.presentation.team

import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckClRoasterEditBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterEditBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class UpdateTeamInfoLckClRoasterEditFragment: BaseFragment<FragmentUpdateTeamInfoLckClRoasterEditBinding>(R.layout.fragment_update_team_info_lck_cl_roaster_edit) {

    private var playerId: Int? = null
    private var selectedImageUri: String? = null

    private val viewModel: UpdateTeamInfoLckClRoasterEditViewModel by activityViewModels()
    private val viewModel_team: UpdateTeamInfoLckClRoasterViewModel by activityViewModels()

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
        playerId = arguments?.getInt("playerId")
        setupInitialData()

        setupSaveButtonListener()
        setupNoButtonListener()
        setupBackButtonListener()
        setupPositionDropdown()
        setTeamName()

        binding.ivUpdateTeamLckClRoasterEditGallery.setOnSingleClickListener  {
            openGallery()
        }
    }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setTeamName() {
        val teamName = viewModel_team.teamName ?: "Unknown Team"
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
                viewModel_team.deletePlayer(id)
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

    private fun setupInitialData() {
        playerId = arguments?.getInt("playerId")
        val playerName = arguments?.getString("playerName")
        val playerPosition = arguments?.getString("playerPosition")
        val playerImageUrl = arguments?.getString("playerImageUrl")

        binding.etUpdateTeamLckClRoasterEditName.setText(playerName)
        binding.actvUpdateTeamLckRoasterEditPosition.setText(playerPosition)

        Glide.with(binding.ivUpdateTeamLckClRoasterEditPhoto.context)
            .load(playerImageUrl)
            .into(binding.ivUpdateTeamLckClRoasterEditPhoto)
    }


    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckClRoasterEditCheck.setOnSingleClickListener {
            val updatedName = binding.etUpdateTeamLckClRoasterEditName.text.toString()
            val updatedPosition = binding.actvUpdateTeamLckRoasterEditPosition.text.toString()
            val updatedImageUrl = selectedImageUri ?: arguments?.getString("playerImageUrl") ?: ""

            val teamName = arguments?.getString("teamName") ?: viewModel_team.teamName ?: "Unknown Team"

            playerId?.let { id ->
                val updatedRoaster = LckClRoaster(
                    id = id,
                    name = updatedName,
                    position = updatedPosition,
                    imageUrl = updatedImageUrl,
                    teamName = teamName
                )

                viewModel_team.updatePlayer(updatedRoaster)

                val action = UpdateTeamInfoLckClRoasterEditFragmentDirections
                    .actionUpdateTeamInfoLckClRoasterEditFragmentToUpdateTeamInfoLckClRoasterFragment(
                        newPlayer = null,
                        updatedRoaster = updatedRoaster,
                        teamName = teamName
                    )
                navigator.navigate(action)
            }
        }
    }
}

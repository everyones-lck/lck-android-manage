package umc.everyones.everyoneslckmanage.presentation.team

import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckClRoasterEditBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterEditBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class UpdateTeamInfoLckRoasterEditFragment: BaseFragment<FragmentUpdateTeamInfoLckRoasterEditBinding>(R.layout.fragment_update_team_info_lck_roaster_edit) {

    private var playerId: Int? = null
    private var selectedImageUri: String? = null

    private val viewModel: UpdateTeamInfoLckRoasterEditViewModel by activityViewModels()
    private val viewModel_team: UpdateTeamInfoLckRoasterViewModel by activityViewModels()

    private val navigator by lazy {
        findNavController()
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivUpdateTeamLckRoasterEditPhoto.setImageURI(it)
            selectedImageUri = it.toString()
        }
    }
    override fun initObserver() {
        playerId?.let { id ->
            lifecycleScope.launchWhenStarted {
                viewModel.playerWinningCareers.collect { winningCareerMap ->
                    val careerList = viewModel.getWinningCareerForPlayer(id)
                    (binding.rvUpdateTeamLckRoasterEditWinningCareer.adapter as WinningCareerRVA).submitList(careerList.toList())

                }
            }
        }
        playerId?.let { id ->
            lifecycleScope.launchWhenStarted {
                viewModel.playerHistoryOfTeam.collect { historyOfTeamMap ->
                    val historyTeamList = viewModel.getHistoryTeamsForPlayer(id)
                    (binding.rvUpdateTeamLckRoasterEditHistoryOfTeam.adapter as HistoryOfTeamRVA).submitList(historyTeamList.toList())

                }
            }
        }
    }

    override fun initView() {
        playerId = arguments?.getInt("playerId")
        setupInitialData()
        setupRecyclerView()
        setupHistoryTeamRecyclerView()
        setupAddButton()
        setupSaveButtonListener()
        setupNoButtonListener()
        setupBackButtonListener()
        setTeamName()

        binding.ivUpdateTeamLckRoasterEditGallery.setOnSingleClickListener  {
            openGallery()
        }
    }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setTeamName() {
        val teamName = viewModel_team.teamName ?: "Unknown Team"
        binding.tvUpdateTeamLckRoasterEditTeamName.text = teamName
    }

    private fun setupRecyclerView() {
        val adapter = WinningCareerRVA(
            onAddWinningCareer = { newCareer ->
                playerId?.let { id ->
                    viewModel.addWinningCareerToPlayer(id, newCareer)
                    updateRecyclerView(id)
                }
            },
            onSaveWinningCareer = { updatedCareer ->
                playerId?.let { id ->
                    viewModel.updateWinningCareerForPlayer(id, updatedCareer)
                    updateRecyclerView(id)
                }
            },
            onDeleteWinningCareer = { careerId ->
                playerId?.let { id ->
                    viewModel.deleteWinningCareerFromPlayer(id, careerId)
                    updateRecyclerView(id)
                }
            }
        )

        binding.rvUpdateTeamLckRoasterEditWinningCareer.layoutManager = LinearLayoutManager(context)
        binding.rvUpdateTeamLckRoasterEditWinningCareer.adapter = adapter

        playerId?.let { id ->
            updateRecyclerView(id)
        }
    }

    private fun updateRecyclerView(playerId: Int) {
        val careerList = viewModel.getWinningCareerForPlayer(playerId)
        (binding.rvUpdateTeamLckRoasterEditWinningCareer.adapter as WinningCareerRVA).submitList(careerList.toList())

    }
    private fun setupHistoryTeamRecyclerView() {
        val adapter = HistoryOfTeamRVA(
            onAddHistoryTeam = { newTeam ->
                playerId?.let { id ->
                    viewModel.addHistoryTeamToPlayer(id, newTeam)
                    updateHistoryTeamRecyclerView(id)
                }
            },
            onSaveHistoryTeam = { updatedTeam ->
                playerId?.let { id ->
                    viewModel.updateHistoryTeamForPlayer(id, updatedTeam)
                    updateHistoryTeamRecyclerView(id)
                }
            },
            onDeleteHistoryTeam = { teamId ->
                playerId?.let { id ->
                    viewModel.deleteHistoryTeamFromPlayer(id, teamId.id)
                    updateHistoryTeamRecyclerView(id)
                }
            }
        )

        binding.rvUpdateTeamLckRoasterEditHistoryOfTeam.layoutManager = LinearLayoutManager(context)
        binding.rvUpdateTeamLckRoasterEditHistoryOfTeam.adapter = adapter

        playerId?.let { id ->
            updateHistoryTeamRecyclerView(id)
        }
    }

    private fun updateHistoryTeamRecyclerView(playerId: Int) {
        val teamList = viewModel.getHistoryTeamsForPlayer(playerId)
        (binding.rvUpdateTeamLckRoasterEditHistoryOfTeam.adapter as HistoryOfTeamRVA).submitList(teamList.toList())
    }
    private fun setupAddButton() {
        binding.ivUpdateTeamLckRoasterEditWinningCareerAdd.setOnSingleClickListener {
            (binding.rvUpdateTeamLckRoasterEditWinningCareer.adapter as WinningCareerRVA).enterAddMode()
        }
        binding.ivUpdateTeamLckRoasterEditHistoryOfTeamAdd.setOnSingleClickListener {
            (binding.rvUpdateTeamLckRoasterEditHistoryOfTeam.adapter as HistoryOfTeamRVA).enterAddMode()
        }
    }
    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckRoasterEditPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckRoasterEditFragmentDirections
                .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckRoasterEditNo.setOnSingleClickListener {
            playerId?.let { id ->
                viewModel_team.deletePlayer(id)
            }

            val action = UpdateTeamInfoLckRoasterEditFragmentDirections
                .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
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
            val updatedPosition = binding.etUpdateTeamLckRoasterEditPosition.text.toString()
            val updatedImageUrl = selectedImageUri ?: arguments?.getString("playerImageUrl") ?: ""

            val teamName = arguments?.getString("teamName") ?: viewModel_team.teamName ?: "Unknown Team"

            playerId?.let { id ->
                val updatedRoaster = LckRoaster(
                    id = id,
                    name = updatedName,
                    position = updatedPosition,
                    imageUrl = updatedImageUrl,
                    teamName = teamName
                )

                viewModel_team.updatePlayer(updatedRoaster)

                val action = UpdateTeamInfoLckRoasterEditFragmentDirections
                    .actionUpdateTeamInfoLckRoasterEditFragmentToUpdateTeamInfoLckRoasterFragment(
                        newPlayer = null,
                        updatedRoaster = updatedRoaster,
                        teamName = teamName
                    )
                navigator.navigate(action)
            }
        }
    }
}

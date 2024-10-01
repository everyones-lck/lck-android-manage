package umc.everyones.everyoneslckmanage.presentation.team

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckCoachEditBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterEditBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class UpdateTeamInfoLckCoachEditFragment: BaseFragment<FragmentUpdateTeamInfoLckCoachEditBinding>(R.layout.fragment_update_team_info_lck_coach_edit) {

    private var playerId: Int? = null
    private var selectedImageUri: String? = null

    private val viewModel: UpdateTeamInfoLckCoachEditViewModel by activityViewModels()
    private val viewModel_team: UpdateTeamInfoLckCoachViewModel by activityViewModels()

    private val navigator by lazy {
        findNavController()
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivUpdateTeamLckCoachEditPhoto.setImageURI(it)
            selectedImageUri = it.toString()
        }
    }
    override fun initObserver() {
        playerId?.let { id ->
            lifecycleScope.launchWhenStarted {
                viewModel.playerWinningCareers.collect { winningCareerMap ->
                    val careerList = viewModel.getWinningCareerForPlayer(id)
                    (binding.rvUpdateTeamLckCoachEditWinningCareer.adapter as WinningCareerRVA).submitList(careerList.toList())

                }
            }
        }
        playerId?.let { id ->
            lifecycleScope.launchWhenStarted {
                viewModel.playerHistoryOfTeam.collect { historyOfTeamMap ->
                    val historyTeamList = viewModel.getHistoryTeamsForPlayer(id)
                    (binding.rvUpdateTeamLckCoachEditHistoryOfTeam.adapter as HistoryOfTeamRVA).submitList(historyTeamList.toList())

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

        binding.ivUpdateTeamLckCoachEditGallery.setOnSingleClickListener  {
            openGallery()
        }
    }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setTeamName() {
        val teamName = viewModel_team.teamName ?: "Unknown Team"
        binding.tvUpdateTeamLckCoachEditTeamName.text = teamName
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

        binding.rvUpdateTeamLckCoachEditWinningCareer.layoutManager = LinearLayoutManager(context)
        binding.rvUpdateTeamLckCoachEditWinningCareer.adapter = adapter

        playerId?.let { id ->
            updateRecyclerView(id)
        }
    }

    private fun updateRecyclerView(playerId: Int) {
        val careerList = viewModel.getWinningCareerForPlayer(playerId)
        (binding.rvUpdateTeamLckCoachEditWinningCareer.adapter as WinningCareerRVA).submitList(careerList.toList())

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

        binding.rvUpdateTeamLckCoachEditHistoryOfTeam.layoutManager = LinearLayoutManager(context)
        binding.rvUpdateTeamLckCoachEditHistoryOfTeam.adapter = adapter

        playerId?.let { id ->
            updateHistoryTeamRecyclerView(id)
        }
    }

    private fun updateHistoryTeamRecyclerView(playerId: Int) {
        val teamList = viewModel.getHistoryTeamsForPlayer(playerId)
        (binding.rvUpdateTeamLckCoachEditHistoryOfTeam.adapter as HistoryOfTeamRVA).submitList(teamList.toList())
    }
    private fun setupAddButton() {
        binding.ivUpdateTeamLckCoachEditWinningCareerAdd.setOnSingleClickListener {
            (binding.rvUpdateTeamLckCoachEditWinningCareer.adapter as WinningCareerRVA).enterAddMode()
        }
        binding.ivUpdateTeamLckCoachEditHistoryOfTeamAdd.setOnSingleClickListener {
            (binding.rvUpdateTeamLckCoachEditHistoryOfTeam.adapter as HistoryOfTeamRVA).enterAddMode()
        }
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckCoachEditPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckCoachEditFragmentDirections
                .actionUpdateTeamInfoLckCoachEditFragmentToUpdateTeamInfoLckCoachFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckCoachEditNo.setOnSingleClickListener {
            playerId?.let {
                viewModel_team.deletePlayer(id)
            }
            val action = UpdateTeamInfoLckCoachEditFragmentDirections
                .actionUpdateTeamInfoLckCoachEditFragmentToUpdateTeamInfoLckCoachFragment(
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
        val playerImageUrl = arguments?.getString("playerImageUrl")

        binding.etUpdateTeamLckCoachEditName.setText(playerName)

        Glide.with(binding.ivUpdateTeamLckCoachEditPhoto.context)
            .load(playerImageUrl)
            .into(binding.ivUpdateTeamLckCoachEditPhoto)
    }

    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckCoachEditCheck.setOnClickListener {
            val updatedName = binding.etUpdateTeamLckCoachEditName.text.toString()
            val updatedImageUrl = selectedImageUri ?: arguments?.getString("playerImageUrl") ?: ""

            val teamName = arguments?.getString("teamName") ?: viewModel_team.teamName ?: "Unknown Team"

            playerId?.let { id ->
                val updatedRoaster = LckCoach(
                    id = id,
                    name = updatedName,
                    imageUrl = updatedImageUrl,
                    teamName = teamName
                )

                viewModel_team.updatePlayer(updatedRoaster)

                val action = UpdateTeamInfoLckCoachEditFragmentDirections
                    .actionUpdateTeamInfoLckCoachEditFragmentToUpdateTeamInfoLckCoachFragment(
                        newPlayer = null,
                        updatedRoaster = updatedRoaster,
                        teamName = teamName
                    )
                navigator.navigate(action)
            }
        }
    }
}

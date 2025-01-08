package umc.everyones.everyoneslckmanage.presentation.team

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckCoachAddBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterAddBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.util.UUID

class UpdateTeamInfoLckCoachAddFragment : BaseFragment<FragmentUpdateTeamInfoLckCoachAddBinding>(R.layout.fragment_update_team_info_lck_coach_add) {

    private var playerId: Int? = null
    private var selectedImageUri: String? = null

    private val viewModel: UpdateTeamInfoLckCoachAddViewModel by activityViewModels()
    private val viewModel_team: UpdateTeamInfoLckCoachViewModel by activityViewModels()

    private val navigator by lazy {
        findNavController()
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivUpdateTeamLckCoachAddPhoto.setImageURI(it)
            selectedImageUri = it.toString()
        }
    }

    override fun initObserver() {
        playerId?.let { id ->
            lifecycleScope.launchWhenStarted {
                viewModel.playerWinningCareers.collect { winningCareerMap ->
                    val careerList = viewModel.getWinningCareerForPlayer(id)
                    (binding.rvUpdateTeamLckCoachAddWinningCareer.adapter as WinningCareerRVA).submitList(careerList.toList())
                }
            }
        }

        playerId?.let { id ->
            lifecycleScope.launchWhenStarted {
                viewModel.playerHistoryOfTeam.collect { historyOfTeamMap ->
                    val historyTeamList = viewModel.getHistoryTeamsForPlayer(id)
                    (binding.rvUpdateTeamLckCoachAddHistoryOfTeam.adapter as HistoryOfTeamRVA).submitList(historyTeamList.toList())
                }
            }
        }
    }

    override fun initView() {
        playerId = generateUniquePlayerId()
        setTeamName()
        setupRecyclerView()
        setupHistoryTeamRecyclerView()
        setupAddButton()
        setupNoButtonListener()
        setupBackButtonListener()
        setupSaveButtonListener()

        binding.ivUpdateTeamLckCoachAddGallery.setOnSingleClickListener {
            openGallery()
        }
    }

    private fun generateUniquePlayerId(): Int {
        return UUID.randomUUID().hashCode()
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

        binding.rvUpdateTeamLckCoachAddWinningCareer.layoutManager = LinearLayoutManager(context)
        binding.rvUpdateTeamLckCoachAddWinningCareer.adapter = adapter

        playerId?.let { id ->
            updateRecyclerView(id)
        }
    }

    private fun updateRecyclerView(playerId: Int) {
        val careerList = viewModel.getWinningCareerForPlayer(playerId)
        (binding.rvUpdateTeamLckCoachAddWinningCareer.adapter as WinningCareerRVA).submitList(careerList.toList())

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

        binding.rvUpdateTeamLckCoachAddHistoryOfTeam.layoutManager = LinearLayoutManager(context)
        binding.rvUpdateTeamLckCoachAddHistoryOfTeam.adapter = adapter

        playerId?.let { id ->
            updateHistoryTeamRecyclerView(id)
        }
    }

    private fun updateHistoryTeamRecyclerView(playerId: Int) {
        val teamList = viewModel.getHistoryTeamsForPlayer(playerId)
        (binding.rvUpdateTeamLckCoachAddHistoryOfTeam.adapter as HistoryOfTeamRVA).submitList(teamList.toList())
    }

    private fun setupAddButton() {
        binding.ivUpdateTeamLckCoachAddWinningCareerAdd.setOnSingleClickListener {
            (binding.rvUpdateTeamLckCoachAddWinningCareer.adapter as WinningCareerRVA).enterAddMode()
        }
        binding.ivUpdateTeamLckCoachAddHistoryOfTeamAdd.setOnSingleClickListener {
            (binding.rvUpdateTeamLckCoachAddHistoryOfTeam.adapter as HistoryOfTeamRVA).enterAddMode()
        }
    }

    private fun setTeamName() {
        val teamName = viewModel_team.teamName ?: "Unknown Team"
        binding.tvUpdateTeamLckCoachAddTeamName.text = teamName
    }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckCoachAddPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckCoachAddFragmentDirections
                .actionUpdateTeamInfoLckCoachAddFragmentToUpdateTeamInfoLckCoachFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckCoachAddNo.setOnSingleClickListener {
            val action = UpdateTeamInfoLckCoachAddFragmentDirections
                .actionUpdateTeamInfoLckCoachAddFragmentToUpdateTeamInfoLckCoachFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckCoachAddCheck.setOnSingleClickListener  {
            val playerName = binding.etUpdateTeamLckCoachAddName.text.toString()

            val teamName = viewModel_team.teamName ?: "Unknown Team"

            val newPlayer = LckCoach(
                id = playerId ?: generateUniquePlayerId(),
                name = playerName,
                imageUrl = selectedImageUri ?: "",
                teamName = teamName
            )

            val action = UpdateTeamInfoLckCoachAddFragmentDirections
                .actionUpdateTeamInfoLckCoachAddFragmentToUpdateTeamInfoLckCoachFragment(
                    newPlayer = newPlayer,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }
}

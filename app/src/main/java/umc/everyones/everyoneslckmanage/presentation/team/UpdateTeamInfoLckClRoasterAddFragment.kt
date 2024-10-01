package umc.everyones.everyoneslckmanage.presentation.team

import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckClRoasterAddBinding
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterAddBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.util.UUID

class UpdateTeamInfoLckClRoasterAddFragment : BaseFragment<FragmentUpdateTeamInfoLckClRoasterAddBinding>(R.layout.fragment_update_team_info_lck_cl_roaster_add) {

    private var playerId: Int? = null
    private var selectedImageUri: String? = null

    private val viewModel: UpdateTeamInfoLckClRoasterAddViewModel by activityViewModels()
    private val viewModel_team: UpdateTeamInfoLckClRoasterViewModel by activityViewModels()

    private val navigator by lazy {
        findNavController()
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivUpdateTeamLckClRoasterAddPhoto.setImageURI(it)
            selectedImageUri = it.toString()
        }
    }

    override fun initObserver() {
        playerId?.let { id ->
            lifecycleScope.launchWhenStarted {
                viewModel.playerWinningCareers.collect { winningCareerMap ->
                    val careerList = viewModel.getWinningCareerForPlayer(id)
                    (binding.rvUpdateTeamLckClRoasterAddWinningCareer.adapter as WinningCareerRVA).submitList(careerList.toList())
                }
            }
        }

        playerId?.let { id ->
            lifecycleScope.launchWhenStarted {
                viewModel.playerHistoryOfTeam.collect { historyOfTeamMap ->
                    val historyTeamList = viewModel.getHistoryTeamsForPlayer(id)
                    (binding.rvUpdateTeamLckClRoasterAddHistoryOfTeam.adapter as HistoryOfTeamRVA).submitList(historyTeamList.toList())
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

        binding.ivUpdateTeamLckClRoasterAddGallery.setOnClickListener {
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

        binding.rvUpdateTeamLckClRoasterAddWinningCareer.layoutManager = LinearLayoutManager(context)
        binding.rvUpdateTeamLckClRoasterAddWinningCareer.adapter = adapter

        playerId?.let { id ->
            updateRecyclerView(id)
        }
    }

    private fun updateRecyclerView(playerId: Int) {
        val careerList = viewModel.getWinningCareerForPlayer(playerId)
        (binding.rvUpdateTeamLckClRoasterAddWinningCareer.adapter as WinningCareerRVA).submitList(careerList.toList())

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

        binding.rvUpdateTeamLckClRoasterAddHistoryOfTeam.layoutManager = LinearLayoutManager(context)
        binding.rvUpdateTeamLckClRoasterAddHistoryOfTeam.adapter = adapter

        playerId?.let { id ->
            updateHistoryTeamRecyclerView(id)
        }
    }

    private fun updateHistoryTeamRecyclerView(playerId: Int) {
        val teamList = viewModel.getHistoryTeamsForPlayer(playerId)
        (binding.rvUpdateTeamLckClRoasterAddHistoryOfTeam.adapter as HistoryOfTeamRVA).submitList(teamList.toList())
    }

    private fun setupAddButton() {
        binding.ivUpdateTeamLckClRoasterAddWinningCareerAdd.setOnSingleClickListener {
            (binding.rvUpdateTeamLckClRoasterAddWinningCareer.adapter as WinningCareerRVA).enterAddMode()
        }
        binding.ivUpdateTeamLckClRoasterAddHistoryOfTeamAdd.setOnSingleClickListener {
            (binding.rvUpdateTeamLckClRoasterAddHistoryOfTeam.adapter as HistoryOfTeamRVA).enterAddMode()
        }
    }

    private fun setTeamName() {
        val teamName = viewModel_team.teamName ?: "Unknown Team"
        binding.tvUpdateTeamLckClRoasterAddTeamName.text = teamName
    }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckClRoasterAddPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckClRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckClRoasterAddFragmentToUpdateTeamInfoLckClRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckClRoasterAddNo.setOnSingleClickListener {
            val action = UpdateTeamInfoLckClRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckClRoasterAddFragmentToUpdateTeamInfoLckClRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckClRoasterAddCheck.setOnSingleClickListener  {
            val playerName = binding.etUpdateTeamLckClRoasterAddName.text.toString()
            val playerPosition = binding.etUpdateTeamLckClRoasterAddPosition.text.toString()

            val teamName = viewModel_team.teamName ?: "Unknown Team"

            val newPlayer = LckClRoaster(
                id = playerId ?: generateUniquePlayerId(),
                name = playerName,
                position = playerPosition,
                imageUrl = selectedImageUri ?: "",
                teamName = teamName
            )

            val action = UpdateTeamInfoLckClRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckClRoasterAddFragmentToUpdateTeamInfoLckClRoasterFragment(
                    newPlayer = newPlayer,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }
}

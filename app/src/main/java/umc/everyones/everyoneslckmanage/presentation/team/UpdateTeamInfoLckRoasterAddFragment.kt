package umc.everyones.everyoneslckmanage.presentation.team


import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentUpdateTeamInfoLckRoasterAddBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.util.UUID

class UpdateTeamInfoLckRoasterAddFragment : BaseFragment<FragmentUpdateTeamInfoLckRoasterAddBinding>(R.layout.fragment_update_team_info_lck_roaster_add) {

    private var playerId: Int? = null
    private var selectedImageUri: String? = null

    private val viewModel: UpdateTeamInfoLckRoasterAddViewModel by activityViewModels()
    private val viewModel_team: UpdateTeamInfoLckRoasterViewModel by activityViewModels()

    private val navigator by lazy {
        findNavController()
    }

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            binding.ivUpdateTeamLckRoasterAddPhoto.setImageURI(it)
            selectedImageUri = it.toString()
        }
    }

    override fun initObserver() {
        playerId?.let { id ->
            lifecycleScope.launchWhenStarted {
                viewModel.playerWinningCareers.collect { winningCareerMap ->
                    val careerList = viewModel.getWinningCareerForPlayer(id)
                    (binding.rvUpdateTeamLckRoasterAddWinningCareer.adapter as WinningCareerRVA).submitList(careerList.toList())
                }
            }
        }

        playerId?.let { id ->
            lifecycleScope.launchWhenStarted {
                viewModel.playerHistoryOfTeam.collect { historyOfTeamMap ->
                    val historyTeamList = viewModel.getHistoryTeamsForPlayer(id)
                    (binding.rvUpdateTeamLckRoasterAddHistoryOfTeam.adapter as HistoryOfTeamRVA).submitList(historyTeamList.toList())
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

        binding.ivUpdateTeamLckRoasterAddGallery.setOnClickListener {
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

        binding.rvUpdateTeamLckRoasterAddWinningCareer.layoutManager = LinearLayoutManager(context)
        binding.rvUpdateTeamLckRoasterAddWinningCareer.adapter = adapter

        playerId?.let { id ->
            updateRecyclerView(id)
        }
    }

    private fun updateRecyclerView(playerId: Int) {
        val careerList = viewModel.getWinningCareerForPlayer(playerId)
        (binding.rvUpdateTeamLckRoasterAddWinningCareer.adapter as WinningCareerRVA).submitList(careerList.toList())

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

        binding.rvUpdateTeamLckRoasterAddHistoryOfTeam.layoutManager = LinearLayoutManager(context)
        binding.rvUpdateTeamLckRoasterAddHistoryOfTeam.adapter = adapter

        playerId?.let { id ->
            updateHistoryTeamRecyclerView(id)
        }
    }

    private fun updateHistoryTeamRecyclerView(playerId: Int) {
        val teamList = viewModel.getHistoryTeamsForPlayer(playerId)
        (binding.rvUpdateTeamLckRoasterAddHistoryOfTeam.adapter as HistoryOfTeamRVA).submitList(teamList.toList())
    }

    private fun setupAddButton() {
        binding.ivUpdateTeamLckRoasterAddWinningCareerAdd.setOnSingleClickListener {
            (binding.rvUpdateTeamLckRoasterAddWinningCareer.adapter as WinningCareerRVA).enterAddMode()
        }
        binding.ivUpdateTeamLckRoasterAddHistoryOfTeamAdd.setOnSingleClickListener {
            (binding.rvUpdateTeamLckRoasterAddHistoryOfTeam.adapter as HistoryOfTeamRVA).enterAddMode()
        }
    }

    private fun setTeamName() {
        val teamName = viewModel_team.teamName ?: "Unknown Team"
        binding.tvUpdateTeamLckRoasterAddTeamName.text = teamName
    }

    private fun openGallery() {
        selectImageLauncher.launch("image/*")
    }

    private fun setupBackButtonListener() {
        binding.ivUpdateTeamLckRoasterAddPrevious.setOnSingleClickListener  {
            val action = UpdateTeamInfoLckRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckRoasterAddFragmentToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }


    private fun setupNoButtonListener() {
        binding.ivUpdateTeamLckRoasterAddNo.setOnSingleClickListener {
            val action = UpdateTeamInfoLckRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckRoasterAddFragmentToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = null,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }

    private fun setupSaveButtonListener() {
        binding.ivUpdateTeamLckRoasterAddCheck.setOnSingleClickListener  {
            val playerName = binding.etUpdateTeamLckRoasterAddName.text.toString()
            val playerPosition = binding.etUpdateTeamLckRoasterAddPosition.text.toString()

            val teamName = viewModel_team.teamName ?: "Unknown Team"

            val newPlayer = LckRoaster(
                id = playerId ?: generateUniquePlayerId(),
                name = playerName,
                position = playerPosition,
                imageUrl = selectedImageUri ?: "",
                teamName = teamName
            )

            val action = UpdateTeamInfoLckRoasterAddFragmentDirections
                .actionUpdateTeamInfoLckRoasterAddFragmentToUpdateTeamInfoLckRoasterFragment(
                    newPlayer = newPlayer,
                    updatedRoaster = null,
                    teamName = null
                )
            navigator.navigate(action)
        }
    }
}

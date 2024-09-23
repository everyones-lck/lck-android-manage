package umc.everyones.everyoneslckmanage.presentation.home

import android.widget.TextView
import androidx.navigation.fragment.findNavController
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentHomeBinding
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val navController by lazy {
        findNavController()
    }

    override fun initObserver() {

    }

    override fun initView() {
        navigateToMenu()
    }

    private fun navigateToMenu() {
        navigateMenuMap.forEach { (tvId, destination) ->
            binding.root.findViewById<TextView>(tvId).setOnSingleClickListener {
                navController.navigate(destination)
            }
        }
    }

    companion object{
        val navigateMenuMap = hashMapOf(
            Pair(R.id.tv_home_delete_community_content_menu, R.id.action_homeFragment_to_deleteCommunityContentFragment),
            Pair(R.id.tv_home_delete_viewing_party_menu, R.id.action_homeFragment_to_deleteViewingPartyFragment),
            Pair(R.id.tv_home_manage_match_predict_vote_time_menu, R.id.action_homeFragment_to_manageMatchPredictVoteTimeFragment),
            Pair(R.id.tv_home_manage_pog_vote_time_menu, R.id.action_homeFragment_to_managePOGVoteTimeFragment),
            Pair(R.id.tv_home_manage_report_menu, R.id.action_homeFragment_to_reportFragment),
            Pair(R.id.tv_home_update_ranking_menu, R.id.action_homeFragment_to_updateRankingFragment),
            Pair(R.id.tv_home_update_team_info_menu, R.id.action_homeFragment_to_updateTeamInfoFragment),
            Pair(R.id.tv_home_input_match_info_menu, R.id.action_homeFragment_to_inputMatchInfoFragment),
            Pair(R.id.tv_home_input_match_result_menu, R.id.action_homeFragment_to_inputMatchResultFragment),
        )
    }
}
package umc.everyones.everyoneslckmanage.presentation.match

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.FragmentInputMatchInfoBinding
import umc.everyones.everyoneslckmanage.domain.model.response.match.LckMatchDetailsModel
import umc.everyones.everyoneslckmanage.presentation.base.BaseFragment
import umc.everyones.everyoneslckmanage.presentation.match.adapter.MatchInfoRVA
import umc.everyones.everyoneslckmanage.util.calender.CustomDatePickerDialog
import umc.everyones.everyoneslckmanage.util.extension.getFormattedDate
import umc.everyones.everyoneslckmanage.util.extension.repeatOnStarted
import umc.everyones.everyoneslckmanage.util.extension.setOnSingleClickListener
import java.util.Calendar

@AndroidEntryPoint
class InputMatchInfoFragment : BaseFragment<FragmentInputMatchInfoBinding>(R.layout.fragment_input_match_info) {
    private val viewModel: InputMatchInfoViewModel by activityViewModels()
    private lateinit var adapter: MatchInfoRVA
    private var selectedDate: Calendar = Calendar.getInstance()
    private var datePickerDialog: CustomDatePickerDialog? = null
    private var isDatePickerDialogVisible = false
    private var isSelectedByCalendar = false
    override fun initObserver() {
        viewLifecycleOwner.repeatOnStarted {
            viewModel.matchDetails.collect { matchDetails ->
                if (matchDetails != null) {
                    updateMatchDetails(matchDetails)
                } else {
                    Timber.tag("initObserver").e("Failed to fetch match details")
                }
            }
        }
    }

    override fun initView() {
        goBackButton()
        addMatchButton()
        setupRecyclerView()
        initCalendarButton()
    }

    private fun goBackButton() {
        binding.ivMatchInfoBackBtn.setOnSingleClickListener {
            findNavController().navigateUp()
        }
    }

    private fun addMatchButton() {
        binding.ivMatchInfoAddBtn.setOnSingleClickListener {
            findNavController().navigate(R.id.action_inputMatchInfoFragment_to_inputMatchDetailFragment)
        }
    }

    private fun setupRecyclerView() {
        adapter = MatchInfoRVA()
        binding.rvMatchInfoContainer.adapter = adapter
        binding.rvMatchInfoContainer.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updateMatchDetails(matchDetailsModel: LckMatchDetailsModel) {
        val formattedDate = selectedDate.getFormattedDate()
        val matchDetailList = matchDetailsModel.matchByDateList
            .flatMap { it.matchDetailList }
            .filter { it.matchDate == formattedDate } // 오늘 날짜만 필터링
            .sortedBy { (it.matchDate) }

        if (matchDetailList.isNotEmpty()) {
            adapter.submitList(matchDetailList)
            binding.rvMatchInfoContainer.visibility = View.VISIBLE
        } else {
            adapter.submitList(emptyList())
            binding.rvMatchInfoContainer.visibility = View.GONE
        }
    }

    private fun initCalendarButton() {
        binding.tvMatchInfoSelectDate.setOnSingleClickListener {
            toggleDatePickerDialog()
        }

        updateSelectedDate(
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
    }

    private fun toggleDatePickerDialog() {
        if (datePickerDialog?.isShowing == true) {
            datePickerDialog?.dismiss()
        } else {
            showCustomDatePickerDialog()
        }
    }

    private fun showCustomDatePickerDialog() {
        val year = selectedDate.get(Calendar.YEAR)
        val month = selectedDate.get(Calendar.MONTH)
        val day = selectedDate.get(Calendar.DAY_OF_MONTH)

        datePickerDialog = CustomDatePickerDialog(
            requireContext(),
            year,
            month,
            day
        ) { selectedYear, selectedMonth, selectedDay ->
            updateSelectedDate(selectedYear, selectedMonth, selectedDay)
        }

        datePickerDialog?.show()
        isDatePickerDialogVisible = true
    }

    private fun updateSelectedDate(year: Int, month: Int, day: Int) {
        selectedDate.set(year, month, day)
        val formattedDate = selectedDate.getFormattedDate()
        Timber.d(formattedDate)
        binding.tvMatchInfoDate.text = formattedDate
        isSelectedByCalendar = true
        viewModel.fetchLckMatchDetails(formattedDate)
        viewModel.updateSelectedDate(formattedDate)

    }


}
package umc.everyones.everyoneslckmanage.presentation.match

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import umc.everyones.everyoneslckmanage.domain.repository.InputMatchRepository
import javax.inject.Inject

@HiltViewModel
class InputMatchResultViewModel @Inject constructor(
    private val repository: InputMatchRepository
): ViewModel(){

}
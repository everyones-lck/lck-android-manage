package umc.everyones.everyoneslckmanage.presentation.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import umc.everyones.everyoneslckmanage.domain.repository.UpdateTeamInfoRepository
import umc.everyones.everyoneslckmanage.util.extension.Constants
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateTeamInfoLckCoachAddViewModel @Inject constructor(
    private val repository: UpdateTeamInfoRepository
) : ViewModel() {

    private val _addCoachResult = MutableStateFlow<Result<Unit>?>(null)
    val addCoachResult: StateFlow<Result<Unit>?> get() = _addCoachResult

    fun addCoach(
        profileImageFile: File?,
        teamId: Int,
        name: String,
        realName: String,
        birth: String,
        isCaptain: Boolean = false
    ) {
        viewModelScope.launch {

            val profilePart = profileImageFile?.let {
                MultipartBody.Part.createFormData(
                    "profileImage",
                    it.name,
                    it.asRequestBody("image/*".toMediaTypeOrNull())
                )
            }

            val jsonBody = """
            {
                "teamId": $teamId,
                "name": "$name",
                "realName": "$realName",
                "position": "COACH",
                "season": "${Constants.CURRENT_SEASON}",
                "role": "COACH",
                "isCaptain": $isCaptain,
                "birth": "$birth"
            }
        """.trimIndent()
                .toRequestBody("application/json".toMediaTypeOrNull())

            val result = repository.addPlayer(profilePart, jsonBody)
            _addCoachResult.value = result
        }
    }

    fun reset() {
        _addCoachResult.value = null
    }
}


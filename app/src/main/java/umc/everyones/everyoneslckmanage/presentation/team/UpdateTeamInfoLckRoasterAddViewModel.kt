package umc.everyones.everyoneslckmanage.presentation.team

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import umc.everyones.everyoneslckmanage.domain.repository.UpdateTeamInfoRepository
import umc.everyones.everyoneslckmanage.util.extension.Constants
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateTeamInfoLckRoasterAddViewModel @Inject constructor(
    private val repository: UpdateTeamInfoRepository
) : ViewModel() {

    private val _addPlayerResult = MutableStateFlow<Result<Unit>?>(null)
    val addPlayerResult: StateFlow<Result<Unit>?> get() = _addPlayerResult

    fun addPlayer(
        profileImageFile: File?,
        teamId: Int,
        name: String,
        realName: String,
        position: String,
        birth: String,
        role: String = "LCK_ROSTER",
        isCaptain: Boolean = false
    ) {
        viewModelScope.launch {

            val profileImagePart = profileImageFile?.let {
                MultipartBody.Part.createFormData(
                    name = "profileImage",
                    filename = it.name,
                    body = it.asRequestBody("image/*".toMediaTypeOrNull())
                )
            }

            val jsonRequestBody = """
                {
                    "teamId": $teamId,
                    "name": "$name",
                    "realName": "$realName",
                    "position": "$position",
                    "season": "${Constants.CURRENT_SEASON}",
                    "role": "$role",
                    "isCaptain": $isCaptain,
                    "birth": "$birth"
                }
            """.trimIndent().toRequestBody("application/json".toMediaTypeOrNull())

            val result = repository.addPlayer(profileImagePart, jsonRequestBody)
            _addPlayerResult.value = result
        }
    }
}

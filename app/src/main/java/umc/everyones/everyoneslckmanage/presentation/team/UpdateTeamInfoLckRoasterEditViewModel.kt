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
import umc.everyones.everyoneslckmanage.data.dto.request.team.PlayerDeleteRequestDto
import umc.everyones.everyoneslckmanage.domain.repository.UpdateTeamInfoRepository
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateTeamInfoLckRoasterEditViewModel @Inject constructor(
    private val repository: UpdateTeamInfoRepository
) : ViewModel() {

    private val _deletePlayer = MutableStateFlow<Result<Unit>?>(null)
    val deletePlayer: StateFlow<Result<Unit>?> get() = _deletePlayer

    private val _updateResult = MutableStateFlow<Result<Unit>?>(null)
    val updateResult: StateFlow<Result<Unit>?> get() = _updateResult

    fun updatePlayer(
        profileImageFile: File?,
        playerId: Int,
        name: String,
        realName: String,
        position: String,
        birthday: String,
    ) {
        viewModelScope.launch {

            // 이미지 Multipart 생성
            val profileImagePart = profileImageFile?.let {
                MultipartBody.Part.createFormData(
                    name = "profileImage",
                    filename = it.name,
                    body = it.asRequestBody("image/*".toMediaTypeOrNull())
                )
            }

            // JSON RequestBody 생성
            val jsonRequestBody = """
                {
                    "playerId": $playerId,
                    "name": "$name",
                    "realName": "$realName",
                    "position": "$position",
                    "birthday": "$birthday"
                }
            """.trimIndent().toRequestBody("application/json".toMediaTypeOrNull())

            val result = repository.updatePlayer(profileImagePart, jsonRequestBody)
            _updateResult.value = result
        }
    }


    fun deletePlayer(playerId: Int) {
        viewModelScope.launch {
            val request = PlayerDeleteRequestDto(playerId)
            val result = repository.deletePlayer(request)
            _deletePlayer.value = result
        }
    }
}

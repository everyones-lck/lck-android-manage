package umc.everyones.everyoneslckmanage.presentation.team

import android.content.SharedPreferences
import android.util.Log
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
    private val repository: UpdateTeamInfoRepository,
    private val spf: SharedPreferences
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
            val profileImagePart = profileImageFile?.let {
                if (it.length() > 0) {
                    MultipartBody.Part.createFormData(
                        name = "profileImage",
                        filename = it.name,
                        body = it.asRequestBody("image/*".toMediaTypeOrNull())
                    )
                } else {
                    null
                }
            }
            val jsonString = """
            {
                "playerId": $playerId,
                "name": "$name",
                "realName": "$realName",
                "position": "$position",
                "birthday": "$birthday"
            }
            """.trimIndent()

            val jsonRequestBody = jsonString.toRequestBody("application/json".toMediaTypeOrNull())

            val existingImageUrl = getExistingProfileImageUrl()

            val result = if (profileImagePart != null) {
                val updateResult = repository.updatePlayer(profileImagePart, jsonRequestBody)
                saveProfileImageUrl(profileImageFile.absolutePath)
                updateResult
            } else {
                val existingProfileImagePart = existingImageUrl?.let {
                    try {
                        val imageFile = File(it)
                        if (imageFile.exists()) {
                            MultipartBody.Part.createFormData(
                                name = "profileImage",
                                filename = imageFile.name,
                                body = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
                            )
                        } else {
                            Log.e("UpdatePlayer", "Local image file does not exist.")
                            null
                        }
                    } catch (e: Exception) {
                        Log.e("UpdatePlayer", "Error converting local file to Multipart: ${e.message}")
                        null
                    }
                }

                if (existingProfileImagePart != null) {
                    repository.updatePlayer(existingProfileImagePart, jsonRequestBody)
                } else {
                    _updateResult.value = Result.failure(Exception("No image available"))
                    return@launch
                }
            }
            _updateResult.value = result
        }
    }

    fun resetUpdateResult() {
        _updateResult.value = null
    }

    private fun getExistingProfileImageUrl(): String? {
        val imageUrl = spf.getString("profileImage", null)
        return imageUrl
    }

    private fun saveProfileImageUrl(url: String?) {
        spf.edit().apply {
            putString("profileImage", url)
            apply()
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

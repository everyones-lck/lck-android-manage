package umc.everyones.everyoneslckmanage.presentation.team

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import umc.everyones.everyoneslckmanage.domain.repository.UpdateTeamInfoRepository
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UpdateTeamInfoLckCoachEditViewModel @Inject constructor(
    private val repository: UpdateTeamInfoRepository,
    private val spf: SharedPreferences
) : ViewModel() {

    private val _updateResult = MutableStateFlow<Result<Unit>?>(null)
    val updateResult: StateFlow<Result<Unit>?> get() = _updateResult

    private val _deleteResult = MutableStateFlow<Result<Unit>?>(null)
    val deleteResult: StateFlow<Result<Unit>?> get() = _deleteResult


    fun updateCoach(
        profileImageFile: File?,
        playerId: Int,
        name: String?,
        realName: String?,
        birth: String?,
    ) {
        viewModelScope.launch {

            val jsonMap = mutableMapOf<String, Any>(
                "playerId" to playerId,
                "position" to "COACH",
                "role" to "COACH"
            )

            if (!name.isNullOrEmpty()) jsonMap["name"] = name
            if (!realName.isNullOrEmpty()) jsonMap["realName"] = realName
            if (!birth.isNullOrEmpty()) jsonMap["birth"] = birth

            val jsonRequestBody =
                Gson().toJson(jsonMap).toRequestBody("application/json".toMediaTypeOrNull())

            val profileImagePart: MultipartBody.Part? =
                if (profileImageFile != null && profileImageFile.length() > 0) {

                    MultipartBody.Part.createFormData(
                        name = "profileImage",
                        filename = profileImageFile.name,
                        body = profileImageFile.asRequestBody("image/*".toMediaTypeOrNull())
                    ).also {
                        saveProfileImageUrl(profileImageFile.absolutePath)
                    }

                } else {
                    val existingPath = getExistingProfileImageUrl()

                    if (existingPath.isNullOrEmpty()) {
                        _updateResult.value =
                            Result.failure(IllegalStateException("기존 프로필 이미지가 없습니다."))
                        return@launch
                    }

                    val existingFile = File(existingPath)
                    if (!existingFile.exists()) {
                        _updateResult.value =
                            Result.failure(IllegalStateException("기존 프로필 이미지 파일이 없습니다."))
                        return@launch
                    }

                    MultipartBody.Part.createFormData(
                        name = "profileImage",
                        filename = existingFile.name,
                        body = existingFile.asRequestBody("image/*".toMediaTypeOrNull())
                    )
                }

            val result = repository.updatePlayer(profileImagePart, jsonRequestBody)
            _updateResult.value = result
        }
    }

    private fun getExistingProfileImageUrl(): String? {
        return spf.getString("profileImage", null)
    }

    private fun saveProfileImageUrl(url: String?) {
        spf.edit().apply {
            putString("profileImage", url)
            apply()
        }
    }


    fun deleteCoach(playerId: Int) {
        viewModelScope.launch {
            val result = repository.deletePlayer(playerId.toLong())
            _deleteResult.value = result
        }
    }

    fun resetUpdateResult() {
        _updateResult.value = null
    }
}

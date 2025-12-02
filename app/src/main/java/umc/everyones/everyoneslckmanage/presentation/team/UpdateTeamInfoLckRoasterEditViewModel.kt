package umc.everyones.everyoneslckmanage.presentation.team

import android.content.SharedPreferences
import android.util.Log
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
        name: String?,
        realName: String?,
        position: String?,
        birthday: String?,
    ) {
        viewModelScope.launch {

            val jsonMap = mutableMapOf<String, Any>(
                "playerId" to playerId
            )

            if (!name.isNullOrEmpty()) jsonMap["name"] = name
            if (!realName.isNullOrEmpty()) jsonMap["realName"] = realName
            if (!position.isNullOrEmpty()) jsonMap["position"] = position
            if (!birthday.isNullOrEmpty()) jsonMap["birthday"] = birthday

            val jsonRequestBody =
                Gson().toJson(jsonMap).toRequestBody("application/json".toMediaTypeOrNull())

            val profileImagePart: MultipartBody.Part? =
                if (profileImageFile != null && profileImageFile.length() > 0) {
                    // ➤ 새로운 이미지 선택한 경우
                    MultipartBody.Part.createFormData(
                        name = "profileImage",
                        filename = profileImageFile.name,
                        body = profileImageFile.asRequestBody("image/*".toMediaTypeOrNull())
                    ).also {
                        // 로컬 경로 저장
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
                            Result.failure(IllegalStateException("기존 프로필 이미지 파일이 로컬에 없습니다."))
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

    fun deletePlayer(playerId: Long) {
        viewModelScope.launch {
            val result = repository.deletePlayer(playerId)
            _deletePlayer.value = result
        }
    }
}

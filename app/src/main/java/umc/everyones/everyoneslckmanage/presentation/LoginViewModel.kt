package umc.everyones.everyoneslckmanage.presentation

import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import umc.everyones.everyoneslckmanage.domain.model.request.LoginRequestModel
import umc.everyones.everyoneslckmanage.domain.repository.LoginRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val spf: SharedPreferences
): ViewModel() {

    fun login(kakaoUserId: String){
        val login = LoginRequestModel(kakaoUserId)
        viewModelScope.launch {
            repository.login(login)
                .onSuccess { response ->
                    val accessToken = response.accessToken
                        spf.edit{
                            putString("jwt", accessToken)
                    }
                    Log.d("SplashActivity", "테스트 토큰 : $accessToken")
                }
                .onFailure { exception ->
                    Log.e("SplashActivity", "로그인 실패: ${exception.message}")
                }
        }
    }
}
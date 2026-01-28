package umc.everyones.everyoneslckmanage.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import umc.everyones.everyoneslckmanage.R
import umc.everyones.everyoneslckmanage.databinding.ActivitySplashBinding
import umc.everyones.everyoneslckmanage.util.extension.navigateToScreenClear
import javax.inject.Inject
import androidx.core.content.edit

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding

    @Inject
    lateinit var spf: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        binding.ivSplashIcon.setOnClickListener {
            viewModel.login("123")
        }
        lifecycleScope.launch {
            delay(1500L)
            navigateToScreenClear<MainActivity>()
        }
    }
}
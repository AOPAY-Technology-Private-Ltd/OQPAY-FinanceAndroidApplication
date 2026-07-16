package com.bosandroidapp.oqmobilefinance.ui.slideshow.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.bosandroidapp.oqmobilefinance.databinding.SplashMainBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isDevModeEnabled
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isInternetAvailable
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.showDevModeSnackbar
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.view.activity.ChooseYourRolePage
import com.bosandroidapp.oqmobilefinance.workmanager.EmiNotificationWorker
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit


class SplashScreen : AppCompatActivity() {
    lateinit var binding: SplashMainBinding
    lateinit var preference: SharedPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = SplashMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preference = SharedPreference(this)


        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }


        Handler(Looper.getMainLooper()).postDelayed({
            if (!isInternetAvailable(this)) {
                Snackbar.make(findViewById(android.R.id.content),
                    "No internet connection. Please check your network.",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Retry") {
                    val intent = Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }.show()
            }
            else{
                // Code to run after delay
                if (preference.getBoolanValue(ConstantClass.LoggedIn, false)) {
                    val mainIntent = Intent(this@SplashScreen, DashBoard::class.java)
                    startActivity(mainIntent)
                    finish()
                }
                else {
                    val mainIntent = Intent(this@SplashScreen, ChooseYourRolePage::class.java)
                    startActivity(mainIntent)
                    finish()
                }
            }

        }, 3000)



    }







}
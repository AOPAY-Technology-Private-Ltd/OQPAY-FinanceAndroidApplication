package com.bosandroidapp.oqmobilefinance.kioskmode

import android.Manifest
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bos.payment.appName.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AppVersion
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Customer
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.DeviceBrand
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.DeviceName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.DeviceOSVersion
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.deviceManufacturer
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.deviceModel
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.loginType
import com.bosandroidapp.oqmobilefinance.data.model.UploadDeviceInfoReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.databinding.ActivityPermissionSetupBinding
import com.bosandroidapp.oqmobilefinance.databinding.LayoutPermissionItemBinding
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.slideshow.activity.LoginPage
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson

class PermissionSetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPermissionSetupBinding
    var preference: SharedPreference? = null
    var count = 0 // Tracks accessibility redirection state
    lateinit var viewModel: AuthenticationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionSetupBinding.inflate(layoutInflater)
        preference = SharedPreference(this)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]
        setupUI()
    }

    private fun setupUI() {
        // 1. Basic Permissions (Phone, Location)
        binding.itemStandardPermissions.tvTitle.text = "Basic Permissions"
        binding.itemStandardPermissions.tvDescription.text = "Required for Location and Phone state."
        binding.itemStandardPermissions.btnGrant.setOnClickListener {
            val permissions = mutableListOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 101)
        }

        // 2. Notification Permission
        binding.itemNotification.tvTitle.text = "Notification Permission"
        binding.itemNotification.tvDescription.text = "Required to show status and alerts."

        binding.itemNotification.btnGrant.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), 102)
            } else {
                Toast.makeText(this, "Automatically granted on this version", Toast.LENGTH_SHORT).show()
            }
        }


        // 3. Accessibility Service (Stacked Intents for Restricted Settings)
        binding.itemAccessibility.tvTitle.text = "Accessibility Service"
        binding.itemAccessibility.tvDescription.text = "Required to monitor app changes. App will open settings and App Info to allow restricted access."

        binding.itemAccessibility.btnGrant.setOnClickListener {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && count == 0) {
                    // Launch App Info first (this will be in the back stack)
                    val appInfoIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = android.net.Uri.parse("package:$packageName")
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(appInfoIntent)

                    // Launch Accessibility Settings immediately after (this will be on top)
                    val accessibilityIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    accessibilityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(accessibilityIntent)

                    count = 1 // Ensure we stack only once
                   // Toast.makeText(this, "Tap 3 dots -> 'Allow restricted settings', then press back twice.", Toast.LENGTH_LONG).show()
                } else {
                    // Subsequent times or older Android: just open Accessibility
                    setAppasAccessibility()
                }
            } catch (e: Exception) {
                Log.e("PermissionSetup", "Error opening settings: ${e.message}")
                setAppasAccessibility() // Fallback
            }
        }


        // 4. Overlay Permission
        binding.itemOverlay.tvTitle.text = "Display Over Other Apps"
        binding.itemOverlay.tvDescription.text = "Required to keep the app on top."

        binding.itemOverlay.btnGrant.setOnClickListener {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, android.net.Uri.parse("package:$packageName"))
            startActivity(intent)
        }


        binding.btnContinue.setOnClickListener {
            hitApiForUploadCustomerDeviceInfo()
        }

    }

    override fun onResume() {
        super.onResume()
        updatePermissionsStatus()
    }

    private fun updatePermissionsStatus() {
        val allPermissionsGranted = checkAndSetStatus(binding.itemStandardPermissions, checkBasicPermissions()) and
                checkAndSetStatus(binding.itemNotification, checkNotificationPermission()) and
                checkAndSetStatus(binding.itemOverlay, isOverLay()) and
                checkAndSetStatus(binding.itemAccessibility, isAccessibilityServiceEnabled(this, MyAccessibilityService::class.java))

        binding.btnContinue.isEnabled = allPermissionsGranted
        binding.btnContinue.alpha = if (allPermissionsGranted) 1.0f else 0.5f
    }

    private fun checkBasicPermissions(): Boolean {
        val phoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        val fineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        return phoneState == PackageManager.PERMISSION_GRANTED &&
                fineLocation == PackageManager.PERMISSION_GRANTED &&
                coarseLocation == PackageManager.PERMISSION_GRANTED
    }

    private fun checkNotificationPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }



    private fun checkAndSetStatus(itemBinding: LayoutPermissionItemBinding, isGranted: Boolean): Boolean {
        if (isGranted) {
            itemBinding.ivStatus.visibility = View.VISIBLE
            itemBinding.btnGrant.visibility = View.INVISIBLE
            itemBinding.tvDescription.alpha = 0.5f
            itemBinding.tvTitle.alpha = 0.5f
        } else {
            itemBinding.ivStatus.visibility = View.GONE
            itemBinding.btnGrant.visibility = View.VISIBLE
            itemBinding.tvDescription.alpha = 1.0f
            itemBinding.tvTitle.alpha = 1.0f
        }
        return isGranted
    }

    fun hitApiForUploadCustomerDeviceInfo() {
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        try {
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                ConstantClass.IMEI = telephonyManager.imei
            }
        } catch (e: Exception) {
            Log.e("DeviceInfo", "IMEI Error: ${e.message}")
        }

        ConstantClass.deviceManufacturer = android.os.Build.MANUFACTURER
        deviceModel = android.os.Build.MODEL
        DeviceBrand = android.os.Build.BRAND
        DeviceOSVersion = android.os.Build.VERSION.RELEASE
        AppVersion = android.os.Build.VERSION.SDK_INT.toString()
        try {
            DeviceName = android.provider.Settings.Global.getString(contentResolver, android.provider.Settings.Global.DEVICE_NAME) ?: "Android Device"
        } catch (e: Exception) {
            DeviceName = "Android Device"
        }

        val request = UploadDeviceInfoReq(
            appVersion = AppVersion,
            imeiNumber = ConstantClass.IMEI,
            osVersion = DeviceOSVersion,
            model = deviceModel,
            sdkVersion = AppVersion,
            deviceID = deviceId,
            brand = DeviceBrand,
            deviceName = DeviceName,
            manufacturer = deviceManufacturer
        )

        viewModel.uploadDeviceInfo(request).observe(this) { it ->
            when (it.apiStatus) {
                ApiStatus.LOADING -> ConstantClass.OpenPopUpForVeryfyOTP(this)
                ApiStatus.SUCCESS -> {
                    ConstantClass.dialog.dismiss()
                    val response = it.data?.body()
                    if (response != null && response.status == "200") {
                        intentNextPage()
                    } else {
                        Toast.makeText(this, "Kindly transfer the ownership to OQ Pay.", Toast.LENGTH_SHORT).show()
                    }
                }
                ApiStatus.ERROR -> {
                    ConstantClass.dialog.dismiss()
                    Log.e("LoginError", it.message ?: "Something went wrong")
                }
            }
        }
    }

    fun intentNextPage() {
        loginType = Customer
        val mainIntent = Intent(this@PermissionSetupActivity, LoginPage::class.java)
        startActivity(mainIntent)
        finish()
    }
}

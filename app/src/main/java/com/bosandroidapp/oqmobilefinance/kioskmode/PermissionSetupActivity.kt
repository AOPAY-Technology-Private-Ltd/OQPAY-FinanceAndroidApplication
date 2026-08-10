package com.bosandroidapp.oqmobilefinance.kioskmode

import android.Manifest
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bosandroidapp.oqmobilefinance.databinding.ActivityPermissionSetupBinding
import com.bosandroidapp.oqmobilefinance.databinding.LayoutPermissionItemBinding

class PermissionSetupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPermissionSetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        // Standard Permissions
        binding.itemStandardPermissions.tvTitle.text = "Basic Permissions"
        binding.itemStandardPermissions.tvDescription.text = "Required for Location, Phone state, and Notifications."
        binding.itemStandardPermissions.btnGrant.setOnClickListener {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.POST_NOTIFICATIONS,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
        }

        // Overlay Permission
        binding.itemOverlay.tvTitle.text = "Display Over Other Apps"
        binding.itemOverlay.tvDescription.text = "Required to keep the app on top and block other apps."
        binding.itemOverlay.btnGrant.setOnClickListener {
            val intent = android.content.Intent(
                android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                android.net.Uri.parse("package:$packageName")
            )
            startActivity(intent)
        }

        // Accessibility Service
        binding.itemAccessibility.tvTitle.text = "Accessibility Service"
        binding.itemAccessibility.tvDescription.text = "Required to monitor app changes and enforce kiosk mode."
        binding.itemAccessibility.btnGrant.setOnClickListener {
            setAppasAccessibility()
        }

        // Usage Stats
        binding.itemUsageStats.tvTitle.text = "Usage Access"
        binding.itemUsageStats.tvDescription.text = "Required to detect which app is currently running."
        binding.itemUsageStats.btnGrant.setOnClickListener {
            askforUsagePermission()
        }

        // Battery Optimization
        binding.itemBattery.tvTitle.text = "Ignore Battery Optimization"
        binding.itemBattery.tvDescription.text = "Ensures the app runs reliably in the background."
        binding.itemBattery.btnGrant.setOnClickListener {
            optimizeBatterry()
        }

        // Device Admin
        binding.itemDeviceAdmin.tvTitle.text = "Device Administrator"
        binding.itemDeviceAdmin.tvDescription.text = "Required for advanced device management features."
        binding.itemDeviceAdmin.btnGrant.setOnClickListener {
            setAppasAdmin()
        }

        binding.btnContinue.setOnClickListener {
            startActivity(Intent(this, KioskActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        updatePermissionsStatus()
    }

    private fun updatePermissionsStatus() {
        val allPermissionsGranted = checkAndSetStatus(
            binding.itemStandardPermissions,
            checkStandardPermissions()
        ) and checkAndSetStatus(
            binding.itemOverlay,
            isOverLay()
        ) and checkAndSetStatus(
            binding.itemAccessibility,
            isAccessibilityServiceEnabled(this, MyAccessibilityService::class.java)
        ) and checkAndSetStatus(
            binding.itemUsageStats,
            isStatsPermissionGranted()
        ) and checkAndSetStatus(
            binding.itemBattery,
            isBatteryOptimized()
        ) and checkAndSetStatus(
            binding.itemDeviceAdmin,
            isDeviceAdminActive()
        )

        binding.btnContinue.isEnabled = allPermissionsGranted
    }

    private fun checkAndSetStatus(itemBinding: LayoutPermissionItemBinding, isGranted: Boolean): Boolean {
        if (isGranted) {
            itemBinding.ivStatus.visibility = View.VISIBLE
            itemBinding.btnGrant.visibility = View.GONE
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

    private fun checkStandardPermissions(): Boolean {
        val phoneState = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        val notifications = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        } else {
            PackageManager.PERMISSION_GRANTED
        }
        val fineLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocation = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        return phoneState == PackageManager.PERMISSION_GRANTED &&
                notifications == PackageManager.PERMISSION_GRANTED &&
                fineLocation == PackageManager.PERMISSION_GRANTED &&
                coarseLocation == PackageManager.PERMISSION_GRANTED
    }

    private fun isDeviceAdminActive(): Boolean {
        val dpm = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val admin = ComponentName(this, KioskDeviceAdminReceiver::class.java)
        return dpm.isAdminActive(admin)
    }
}

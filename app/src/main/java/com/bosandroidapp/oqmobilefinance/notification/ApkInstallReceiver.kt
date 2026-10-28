package com.bosandroidapp.oqmobilefinance.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInstaller
import android.util.Log
import android.widget.Toast

import com.bosandroidapp.oqmobilefinance.utils.AppUpdateManager

class ApkInstallReceiver : BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null) return

        val status = intent.getIntExtra(PackageInstaller.EXTRA_STATUS, -1)
        val message = intent.getStringExtra(PackageInstaller.EXTRA_STATUS_MESSAGE)

        Log.d("ApkInstallReceiver", "Install Status: $status, Message: $message")

        when (status) {
            PackageInstaller.STATUS_SUCCESS -> {
                Log.d("ApkInstallReceiver", "App updated successfully!")
                Toast.makeText(context, "App updated successfully!", Toast.LENGTH_SHORT).show()
                AppUpdateManager.hitApiForDeviceLockStatus(context, true, "APP_UPDATED")
            }
            PackageInstaller.STATUS_PENDING_USER_ACTION -> {
                val confirmIntent = intent.getParcelableExtra<Intent>(Intent.EXTRA_INTENT)
                if (confirmIntent != null) {
                    confirmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(confirmIntent)
                }
            }
            else -> {
                Log.e("ApkInstallReceiver", "Installation failed ($status): $message")
                Toast.makeText(context, "App update failed: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

package com.bosandroidapp.oqmobilefinance.kioskmode

import android.Manifest
import android.content.Context.MODE_PRIVATE
import android.app.Activity
import android.app.ActivityManager
import android.app.admin.DevicePolicyManager
import android.app.usage.UsageStatsManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.UserManager
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

fun Context.isLocked(): Boolean {
    val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
    return sharedPref.getBoolean("isLocked", false)
}

fun Context.checkStandardPermissions(): Boolean {
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



fun Context.checkAllPermissionsGranted(): Boolean {
    return checkStandardPermissions() &&
            this.isOverLay() &&
            isAccessibilityServiceEnabled(this, MyAccessibilityService::class.java)
}


// for logout condition
fun Context.isEMIsCompleted(): Boolean {
    val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
    return sharedPref.getBoolean("isEMIsCompleted", true)
}


fun Context.stopLockSituation() {
    val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putBoolean("isLocked", false) // key: isLoggedIn, value: true
    editor.apply()

    if (this is KioskActivity) {
        try {
            stopLockIfRunning()
        } catch (_: Exception) {
            try {
                stopLockTask()
            } catch (_: Exception) {}
        }
        finish()
    }

    KioskActivity.finishKiosk()

    try {
        val intent = Intent("com.bosandroidapp.oqmobilefinance.ACTION_UNLOCK_KIOSK").apply {
            setPackage(packageName)
        }
        sendBroadcast(intent)
    }
    catch (e: Exception) {
        Log.e("stopLockSituation", "Error sending unlock broadcast", e)
    }

}


fun Context.startLockSituation() {
    val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putBoolean("isLocked", true) // key: isLoggedIn, value: true
    editor.apply()
}


fun Context.startInternetAlertSituation() {
    val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putBoolean("isInternet", true) // key: isLoggedIn, value: true
    editor.apply()
}

fun Context.stopInternetAlertSituation() {
    val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putBoolean("isInternet", false) // key: isLoggedIn, value: true
    editor.apply()
}


fun Context.isInternetAlertSituationCompleted(): Boolean {
    val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
    return sharedPref.getBoolean("isInternet", true)
}

fun Context.setEMICompleted() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        removeRestrictions()
    } else {
        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val admin = ComponentName(this, KioskDeviceAdminReceiver::class.java)
        if (dpm.isDeviceOwnerApp(packageName)) {
            dpm.clearUserRestriction(admin, UserManager.DISALLOW_FACTORY_RESET)
            dpm.clearUserRestriction(admin, UserManager.DISALLOW_DEBUGGING_FEATURES)
            dpm.clearDeviceOwnerApp(packageName)
            dpm.removeActiveAdmin(admin)
        }
    }
    val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putBoolean("isEMIsCompleted", true) // key: isLoggedIn, value: true
    editor.apply()
    stopLockSituation()
}

fun Context.setEMINotCompleted() {
    imposeRestrictions()
    val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putBoolean("isEMIsCompleted", false) // key: isLoggedIn, value: true
    editor.apply()
}

fun Context.showToast(message: String) {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}

fun Context.isAdmin():Boolean{
    val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    val componentName = ComponentName(this, KioskDeviceAdminReceiver::class.java)
    return dpm.isAdminActive(componentName)
}


fun Context.isDeviceAdmin():Boolean{
    val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
    return dpm.isDeviceOwnerApp(packageName)
}

fun Context.saveToken(data: ByteArray) {
    val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val encoded = Base64.encodeToString(data, Base64.DEFAULT)
    prefs.edit().putString("token", encoded).apply()
}

fun Context.getByteArray(): ByteArray? {
    val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val encoded = prefs.getString("token", null) ?: return null
    return Base64.decode(encoded, Base64.DEFAULT)
}


fun RecyclerView.fillApps(context: Context,apps:List<PaymentApp>){
    val adapter = AppAdapter(context, apps)
    this.adapter = adapter
}


fun String.isAllowedApp():Boolean{
    val list= arrayListOf<String>().apply {
        this.add("com.google.android.apps.nbu.paisa.user")
        this.add("com.phonepe.app")
        this.add("net.one97.paytm")
        this.add("in.org.npci.upiapp")
        this.add("com.mobikwik_new")
        this.add("com.freecharge.mobile")
        this.add("com.icici.pockets")
        this.add("com.axis.axispay")
        this.add("com.hdfcbank.payzapp")
        this.add("sbi.mobile.apps.in")
        this.add("in.amazon.mShop.android.shopping")
    }

    return list.contains(this)
}


fun Context.isPaymentAppRunning(): Boolean {
    return getForegroundApp().isAllowedApp()
}


fun Context.getForegroundApp(): String {
    val usm = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    val time = System.currentTimeMillis()
    // get app usage for last 5 seconds
    val appList = usm.queryUsageStats(
        UsageStatsManager.INTERVAL_DAILY,
        time - 60000,
        time
    )
    if (appList != null && appList.isNotEmpty()) {
        val sorted = appList.sortedByDescending { it.lastTimeUsed }
        Log.d("Accessibility",sorted[0].packageName)
        return sorted[0].packageName
    }
    Log.d("Accessibility","No App")
    return ""
}
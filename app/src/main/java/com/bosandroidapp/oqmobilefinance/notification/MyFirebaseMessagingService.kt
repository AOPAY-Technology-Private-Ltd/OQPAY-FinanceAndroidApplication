package com.bosandroidapp.oqmobilefinance.notification

import android.app.admin.DevicePolicyManager
import android.app.admin.FactoryResetProtectionPolicy
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.UserManager
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bosandroidapp.aopayfinance.data.model.customerreq.CustomerDeviceLockStatusReq
import com.bosandroidapp.oqmobilefinance.BuildConfig
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.convertDate
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetCustomerLoanDetailsReq
import com.bosandroidapp.oqmobilefinance.kioskmode.KioskDeviceAdminReceiver
import com.bosandroidapp.oqmobilefinance.kioskmode.KioskPolicyService
import com.bosandroidapp.oqmobilefinance.kioskmode.*
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.utils.forceSyncEmis
import com.bosandroidapp.oqmobilefinance.utils.getLoansStringObject
import com.bosandroidapp.oqmobilefinance.utils.hasDateChanged
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MyFirebaseMessagingService : FirebaseMessagingService() {




    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val notificationCode = remoteMessage.data["NotificationCode"] ?: ""

        passNotificationCode(notificationCode)

        Log.d("notificationcode", notificationCode)

        Log.e("FCM_TEST", "onMessageReceived called")

    }



    fun passNotificationCode(notificationCode: String){
        val dpm = applicationContext.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val admin = ComponentName(applicationContext, KioskDeviceAdminReceiver::class.java)

        when(notificationCode){

            ConstantClass.LOAN_REJECT , ConstantClass.DISBURSMENT_REJECT , ConstantClass.UNINSTALLED->{

                // 1. Mark EMIs completed and stop lock situation to remove restrictions
                setEMICompleted()
                stopLockSituation()
                // 2. Turn off Accessibility Service
                MyAccessibilityService.stopService()

                // 3. Clear Device Owner & Admin if active
                if (dpm.isDeviceOwnerApp(packageName)) {
                    try {
                        dpm.setLockTaskPackages(admin, arrayOf())
                    } catch (e: Exception) {
                        Log.e("FCM_NOTIFICATION", "Error clearing lock task packages", e)
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val policy = FactoryResetProtectionPolicy.Builder()
                            .setFactoryResetProtectionAccounts(emptyList())
                            .build()
                        dpm.setFactoryResetProtectionPolicy(admin, policy)
                    }
                    dpm.clearUserRestriction(admin, UserManager.DISALLOW_FACTORY_RESET)
                    dpm.clearUserRestriction(admin, UserManager.DISALLOW_DEBUGGING_FEATURES)
                    dpm.clearDeviceOwnerApp(getPackageName())
                    dpm.removeActiveAdmin(admin)
                    hitApiForDeviceLockStatus(true, "APP_UNINSTALLED")
                }

                // 4. Open App Info settings screen easily
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.parse("package:$packageName")
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    }
                    applicationContext.startActivity(intent)
                }
                catch (e: Exception) {
                    Log.e("FCM_NOTIFICATION", "Error opening App Info screen", e)
                }


            }


            ConstantClass.UNLOCK , ConstantClass.LOCK -> {
                Log.d("FCM_UNLOCK", "Unlock notification received")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    runBlocking {
                        applicationContext.forceSyncEmis()
                        if(ConstantClass.UNLOCK==notificationCode){
                            hitApiForDeviceLockStatus(true,"PHONE_UNLOCKED")
                        }
                    }
                }
                else {
                    // Handle older versions if necessary, or just log
                    Log.w("FCM_UNLOCK", "Forced sync skipped: API level < 30")
                }

            }


        }

    }


    fun hitApiForDeviceLockStatus(status: Boolean, actionName: String) {
        val preference = SharedPreference(this)

        val req = CustomerDeviceLockStatusReq(
            deviceModel = Build.MODEL,
            status = status,
            actionType = "DEVICE",
            customerCode = preference.getStringValue(ConstantClass.CustomerCode, ""),
            deviceId = preference.getStringValue(ConstantClass.DEVICEID, ""),
            androidVersion = Build.VERSION.RELEASE,
            retailerCode = preference.getStringValue(ConstantClass.RetailerCode, ""),
            appVersion = BuildConfig.VERSION_NAME,
            clientCode = preference.getStringValue(ConstantClass.ClientCode, ""),
            manufacturer = Build.MANUFACTURER,
            performedAt = ConstantClass.getCurrentUtcTimestamp(),
            appPackageName = packageName,
            actionName = actionName
        )

        Log.d("DeviceUninstallReq", Gson().toJson(req))

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = RetrofitClient.apiInterface.uploadDeviceLockStatusReq(req)
                Log.d("DeviceUninstallRes", Gson().toJson(response?.body()))
            } catch (e: Exception) {
                Log.e("DeviceUninstallError", e.message ?: "Unknown error")
            }
        }
    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }


}
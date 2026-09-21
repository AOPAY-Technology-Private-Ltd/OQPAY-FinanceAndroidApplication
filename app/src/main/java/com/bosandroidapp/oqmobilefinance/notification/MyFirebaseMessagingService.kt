package com.bosandroidapp.oqmobilefinance.notification

import android.app.admin.DevicePolicyManager
import android.app.admin.FactoryResetProtectionPolicy
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.UserManager
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.convertDate
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetCustomerLoanDetailsReq
import com.bosandroidapp.oqmobilefinance.kioskmode.KioskDeviceAdminReceiver
import com.bosandroidapp.oqmobilefinance.kioskmode.KioskPolicyService
import com.bosandroidapp.oqmobilefinance.kioskmode.*
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.utils.forceSyncEmis
import com.bosandroidapp.oqmobilefinance.utils.getLoansStringObject
import com.bosandroidapp.oqmobilefinance.utils.hasDateChanged
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking


class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val notificationCode = remoteMessage.data["NotificationCode"] ?: ""

        passNotificationCode(notificationCode)

        Log.e("FCM_TEST", "onMessageReceived called")

    }


    fun passNotificationCode(notificationCode: String){
        val dpm = applicationContext.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        val admin = ComponentName(applicationContext, KioskDeviceAdminReceiver::class.java)

        when(notificationCode){

            ConstantClass.LOAN_REJECT ->{

                if (dpm.isDeviceOwnerApp(packageName)) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val policy = FactoryResetProtectionPolicy.Builder()
                            .setFactoryResetProtectionAccounts(emptyList())
                            .build()
                        dpm.setFactoryResetProtectionPolicy(admin, policy)
                    }
                    dpm.clearUserRestriction(admin, UserManager.DISALLOW_FACTORY_RESET)
                    dpm.clearDeviceOwnerApp(getPackageName())
                    dpm.removeActiveAdmin(admin)
                }

            }


            ConstantClass.DISBURSMENT_REJECT-> {

                if (dpm.isDeviceOwnerApp(packageName)) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        val policy = FactoryResetProtectionPolicy.Builder()
                            .setFactoryResetProtectionAccounts(emptyList())
                            .build()
                        dpm.setFactoryResetProtectionPolicy(admin, policy)
                    }

                    dpm.clearUserRestriction(admin, UserManager.DISALLOW_FACTORY_RESET)
                    dpm.clearDeviceOwnerApp(getPackageName())
                    dpm.removeActiveAdmin(admin)

                }

            }


            ConstantClass.UNLOCK -> {
                Log.d("FCM_UNLOCK", "Unlock notification received")

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    runBlocking {
                        applicationContext.forceSyncEmis()
                    }
                }
                else {
                    // Handle older versions if necessary, or just log
                    Log.w("FCM_UNLOCK", "Forced sync skipped: API level < 30")
                }

            }


        }

    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }



}
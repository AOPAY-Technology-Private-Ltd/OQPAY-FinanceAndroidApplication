package com.bosandroidapp.oqmobilefinance.notification

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bosandroidapp.oqmobilefinance.kioskmode.KioskDeviceAdminReceiver
import com.bosandroidapp.oqmobilefinance.kioskmode.KioskPolicyService
import com.bosandroidapp.oqmobilefinance.kioskmode.isAdmin
import com.bosandroidapp.oqmobilefinance.kioskmode.startLockSituation
import com.bosandroidapp.oqmobilefinance.kioskmode.stopLockSituation
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {



    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.e("FCM_TEST", "onMessageReceived called")


    }


    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }



}
package com.bosandroidapp.oqmobilefinance.internetchecker

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.utils.ApplicationClass
import kotlinx.coroutines.launch

open class BaseActivity : AppCompatActivity() {
    private var dialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val activityName = this::class.java.simpleName
            com.bosandroidapp.oqmobilefinance.utils.CrashlyticsUtils.setCustomKey("last_opened_activity", activityName)
            com.bosandroidapp.oqmobilefinance.utils.CrashlyticsUtils.log("Activity Created: $activityName")

            val pref = com.bosandroidapp.oqmobilefinance.localdb.SharedPreference(this)
            val customerCode = pref.getStringValue(ConstantClass.CustomerCode, "")
            val retailerCode = pref.getStringValue(ConstantClass.RetailerCode, "")
            val userId = customerCode
            if (userId.isNotBlank()) {
                com.bosandroidapp.oqmobilefinance.utils.CrashlyticsUtils.setUserId(userId)
            }
        } catch (e: Exception) {
            android.util.Log.e("BaseActivity", "Error setting Crashlytics keys", e)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                ApplicationClass.isNetworkAvailable.collect { connected ->
                    if (connected) {
                        dialog?.dismiss()
                    }
                    else {
                        ConstantClass.showNoInternetDialog(this@BaseActivity)
                    }
                }
            }
        }

    }


    override fun onPause() {
        super.onPause()
        try {
            if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                ConstantClass.dialog.dismiss()
            }

            if (dialog != null && dialog!!.isShowing) {
                dialog!!.dismiss()
            }
        } catch (e: Exception) {
        }
    }




}
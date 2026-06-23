package com.bosandroidapp.oqmobilefinance.kioskmode

import android.app.ActivityOptions
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bos.payment.appName.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.databinding.ActivityKioskBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.RetailerProfileReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson

class KioskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKioskBinding
    lateinit var preference: SharedPreference
    lateinit var viewModel: AuthenticationViewModel
    var retailerCode : String = ""
    var  MobileNumber : String= ""
    var  EmailId : String= ""
    var  FName : String= ""
    var  LName : String= ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.exitTransition = null
        window.returnTransition = null
        window.reenterTransition = null
        binding = ActivityKioskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preference= SharedPreference(this)
        retailerCode = preference.getStringValue(ConstantClass.RetailerCode,"")
        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]
        hitapiforGetUpdateProfile()

        val apps = findViewById<RecyclerView>(R.id.paymentApps)
        apps.layoutManager = GridLayoutManager(this, 3)
        apps.visibility=View.GONE

        Log.d("Check","Kiosk")

        if (isLocked()) {
            apps.visibility=View.VISIBLE
            apps.fillApps(this,getPaymentApps())
        }
        else {
            apps.visibility=View.GONE
        }


        // for testing transferOwnerShip.....................................................
        val dpm = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager

        if (dpm.isLockTaskPermitted(packageName)) {
            startLockTask() // 🔒 Enter kiosk mode
        }

    }

    override fun onPause() {
        super.onPause()
        if(isLocked()) finish()
    }

    override fun onStop() {
        super.onStop()
        Log.d("Accessibility","onStop")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) enterImmersiveMode()
    }


    fun hitapiforGetUpdateProfile(){

        var req = RetailerProfileReq(
            mode="GET",
            customerType="Retailer",
            customerCode=retailerCode,
            firstName="",
            lastName="",
            mobileNo="",
            emailid="",
            address="",
            aadharNumber="",
            panNumber="",
            activeStatus=""
        )

        Log.d("retailergetprofileReq", Gson().toJson(req))

        viewModel.getRetailerProfileReq(req).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data.let { users ->
                            users!!.body().let { response ->

                                if (response!!.statuss.equals("True")) {
                                    Log.d("retailerDetailsResponse", Gson().toJson(response))
                                    ConstantClass.dialog.dismiss()
                                    EmailId= response.emailid.toString()
                                    MobileNumber= response.mobileNo.toString()
                                    FName= response.firstName.toString()
                                    LName= response.lastName.toString()
                                    binding.infomasg.text = "This device is locked due to a pending payment. " + "To unlock, please contact your retailer ${FName} ${LName} at $MobileNumber or email at $EmailId"
                                }
                                else {

                                    finish()
                                }

                            }

                        }

                    }

                    ApiStatus.ERROR -> {

                    }

                    ApiStatus.LOADING -> {

                    }
                }
            }
        }
    }


}
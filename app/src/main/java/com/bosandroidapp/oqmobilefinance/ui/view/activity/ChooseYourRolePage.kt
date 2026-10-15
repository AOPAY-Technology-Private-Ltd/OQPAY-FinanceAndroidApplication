package com.bosandroidapp.oqmobilefinance.ui.view.activity

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
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AppVersion
import com.bosandroidapp.oqmobilefinance.databinding.ActivityChooseYourRolePageBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Customer
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.DeviceBrand
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.DeviceName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.DeviceOSVersion
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Retailer
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.deviceManufacturer
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.deviceModel
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.loginType
import com.bosandroidapp.oqmobilefinance.data.model.UploadDeviceInfoReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.kioskmode.KioskDeviceAdminReceiver
import com.bosandroidapp.oqmobilefinance.kioskmode.PermissionSetupActivity
import com.bosandroidapp.oqmobilefinance.kioskmode.checkAllPermissionsGranted
import com.bosandroidapp.oqmobilefinance.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.ui.slideshow.activity.DashBoard
import com.bosandroidapp.oqmobilefinance.ui.slideshow.activity.LoginPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.NewCustomerRegistrationPage
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson

class ChooseYourRolePage : BaseActivity() {
    lateinit var binding : ActivityChooseYourRolePageBinding
    lateinit var viewModel: AuthenticationViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityChooseYourRolePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]

        setOnClickListner()

    }



    fun setOnClickListner(){


        binding.retailerid.setOnClickListener{
            loginType = Retailer
            binding.retailerid.strokeColor = resources.getColor(R.color.darkpurple)
            binding.customerid.strokeColor = resources.getColor(R.color.white)
            val mainIntent = Intent(this@ChooseYourRolePage, LoginPage::class.java)
            startActivity(mainIntent)
        }


        binding.customerid.setOnClickListener{
            try {
                val dpm = getSystemService(DEVICE_POLICY_SERVICE) as DevicePolicyManager
                val admin = ComponentName(this, KioskDeviceAdminReceiver::class.java)

                if (dpm.isDeviceOwnerApp(packageName)) {
                    dpm.setLockTaskPackages(admin, arrayOf(packageName))
                }

                if (dpm.isDeviceOwnerApp(packageName) && dpm.isAdminActive(admin)){
                    if (!checkAllPermissionsGranted()) {
                        startActivity(Intent(this, PermissionSetupActivity::class.java))
                        finish()
                    }
                    else{
                        if(!checkPermissionsrRetailer()){
                            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), 101)
                        }
                        else{
                            hitApiForUploadCustomerDeviceInfo()
                        }
                    }

                }
                else{
                   // intentNextPage()
                    Toast.makeText(this,"Kindly transfer the ownership to OQ Pay.",Toast.LENGTH_SHORT).show()
                }
            }
            catch (e: Exception) {
                Log.e("DPM_ERROR", "Remote exception or Security error: ${e.message}")
                Toast.makeText(this,"Kindly transfer the ownership to OQ Pay.",Toast.LENGTH_SHORT).show()
            }
        }


    }


    private fun checkPermissionsrRetailer(): Boolean {
        val phoneStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        return phoneStatePermission == PackageManager.PERMISSION_GRANTED
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show()
                hitApiForUploadCustomerDeviceInfo()
            }
            else {
                Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show()
            }
        }

    }


    fun intentNextPage(){
        loginType = Customer
        binding.customerid.strokeColor = resources.getColor(R.color.darkpurple)
        binding.retailerid.strokeColor = resources.getColor(R.color.white)
        val mainIntent = Intent(this@ChooseYourRolePage, LoginPage::class.java)
        startActivity(mainIntent)


    }


    fun hitApiForUploadCustomerDeviceInfo(){
        val deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        try {
            val telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            ConstantClass.IMEI = telephonyManager.imei
            // Toast.makeText(this, "IMEI: ${telephonyManager.imei}", Toast.LENGTH_LONG).show()
        }

        catch (e: Exception) {
           // Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
        }

        ConstantClass.deviceManufacturer = android.os.Build.MANUFACTURER
        deviceModel= android.os.Build.MODEL
        DeviceBrand = android.os.Build.BRAND
        DeviceOSVersion = android.os.Build.VERSION.RELEASE
        AppVersion = android.os.Build.VERSION.SDK_INT.toString()
        DeviceName = android.provider.Settings.Global.getString(contentResolver, android.provider.Settings.Global.DEVICE_NAME)

        Log.d("DeviceName", "$DeviceName")
        Log.d("DeviceInfo", "$deviceManufacturer $deviceModel")
        Log.d("DeviceInfo", "Android Version: $DeviceOSVersion (SDK $AppVersion) (brand $DeviceBrand)")


        var request = UploadDeviceInfoReq(
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

        Log.d("DeviceInfoReq", Gson().toJson(request))

        viewModel.uploadDeviceInfo(request).observe(this) { it ->

            when (it.apiStatus) {

                ApiStatus.LOADING -> {
                    ConstantClass.OpenPopUpForVeryfyOTP(this)
                }

                ApiStatus.SUCCESS -> {
                    ConstantClass.dialog.dismiss()
                    val response = it.data?.body()
                    Log.d("DeviceInfoResponse", Gson().toJson(response))

                    if (response != null && response.status.equals("200")) {
                        intentNextPage()
                     }
                    else {
                        Toast.makeText(this,"Kindly transfer the ownership to OQ Pay.",Toast.LENGTH_SHORT).show()
                       // intentNextPage() // for working
                    }
                }

                ApiStatus.ERROR -> {
                    ConstantClass.dialog.dismiss()
                    val errorMessage = it.message ?: "Something went wrong"
                    Toast.makeText(this@ChooseYourRolePage, errorMessage, Toast.LENGTH_SHORT).show()
                    Log.e("LoginError", errorMessage)
                }

            }
        }

    }


}
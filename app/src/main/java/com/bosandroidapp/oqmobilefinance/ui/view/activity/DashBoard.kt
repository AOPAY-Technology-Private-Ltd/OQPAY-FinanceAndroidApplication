package com.bosandroidapp.oqmobilefinance.ui.slideshow.activity

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.bos.payment.appName.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.databinding.ActivityDashBoardBinding
import com.bosandroidapp.oqmobilefinance.databinding.NavHeaderDashBoardBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CheckCompleteEmiStatus
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Customer
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.HoldAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoanSecurityHoldAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.MaxHoldingAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.MinHoldingAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanAddress
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanBuilding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanCity
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanCountry
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanEmailId
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanFirstName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanLastName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanMiddleName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanMobileNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanPinCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanState
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Retailer
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.WalletBalance
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.clickMakePaymentPage
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.convertDate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.formatDateToDDMMYYYY
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.formatIndianAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.getPublicIpAddress
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isInternetAvailable
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.latitude
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.longitude
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.scheduleLocationWorker
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.scheduleOneTimeLocationWorker
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.uploadDataOnFirebaseConsole
import com.bosandroidapp.oqmobilefinance.data.model.CustomerlocationUploadReq
import com.bosandroidapp.oqmobilefinance.data.model.GenerateAccessTokenRequest
import com.bosandroidapp.oqmobilefinance.data.model.NavParentItem
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletAmountReq
import com.bosandroidapp.oqmobilefinance.data.model.SessionOutReq
import com.bosandroidapp.oqmobilefinance.data.model.ValidateSessionRequest
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetCustomerLoanDetailsReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LoginReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LogoutReq
import com.bosandroidapp.oqmobilefinance.data.notification.SendNotificationFeatureNameRequest
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.kioskmode.initiateBlocking
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.view.activity.ChooseYourRolePage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.CustomerEMIPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.CustomerReportsPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.BankDetailsPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.CustomerAppInstall
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.IDVerificationPage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.MapActivity
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.reports.LowCibilScoreCustomerReports
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.MobileSelectionActivity
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.RetailerProfilePage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.WalletAccountDetails
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.reports.ReportSelectionPage
import com.bosandroidapp.oqmobilefinance.ui.view.adapter.NavAdapter
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.bosandroidapp.oqmobilefinance.utils.LocationPermissionHelper
import com.bosandroidapp.oqmobilefinance.utils.MonthsAndPayables
import com.bosandroidapp.oqmobilefinance.utils.getCurrentLastPaidDueDate
import com.bosandroidapp.oqmobilefinance.workmanager.EmiNotificationWorker
import com.google.android.gms.common.wrappers.Wrappers.packageManager
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit


class DashBoard : BaseActivity() {
    private lateinit var binding: ActivityDashBoardBinding
    private lateinit var headerBinding: NavHeaderDashBoardBinding
    lateinit var preference: SharedPreference
    var count: Int = 0
    lateinit var dialog: Dialog
    lateinit var logintype: String
    lateinit var viewModel: AuthenticationViewModel
    val items = listOf(NavParentItem("Reports", listOf("Low Cibil Customer")))
    private lateinit var navAdapter: NavAdapter
    private val notificationPermission = 1001
    var listOfDueWithGraceDate : ArrayList<MonthsAndPayables> = arrayListOf()
    private lateinit var fusedClient: FusedLocationProviderClient
    private var countDownTimer: android.os.CountDownTimer? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.appBarDashBoard.root) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, systemBarsInsets.top, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]
        preference = SharedPreference(this)

        headerBinding = NavHeaderDashBoardBinding.bind(binding.headerlayout.root)

        logintype = preference.getStringValue(ConstantClass.LoginType, "").orEmpty()
        val versionName = packageManager.getPackageInfo(packageName, 0).versionName
        binding.appVersionText.text = "Version $versionName"

        if (logintype.equals(Customer)) {
            if(!checkPermissions()){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.POST_NOTIFICATIONS, Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION), 101)
            }
            binding.navRecyclerViewlayout.visibility=View.GONE
            binding.installAppLayout.visibility=View.GONE
            binding.logout.visibility = View.GONE // for testing
        }

        else {
            if(!checkPermissionsrRetailer()){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_PHONE_STATE), 101)
            }

            binding.navRecyclerViewlayout.visibility=View.GONE
            binding.navRecyclerView.layoutManager = LinearLayoutManager(this)

            navAdapter = NavAdapter(this, items) { clickedChild ->
                // Handle child item clicks here
                when (clickedChild) {
                    ConstantClass.CibilReports -> startActivity(Intent(this@DashBoard, LowCibilScoreCustomerReports::class.java))
                }
            }

            binding.navRecyclerView.adapter = navAdapter
            binding.logout.visibility = View.VISIBLE
            binding.installAppLayout.visibility=View.VISIBLE

          }

        setonclickListner()


    }



    private fun checkPermissions(): Boolean {
        val phoneStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        val notificationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
        val fineLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarLocationPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        return phoneStatePermission == PackageManager.PERMISSION_GRANTED && notificationPermission == PackageManager.PERMISSION_GRANTED && fineLocationPermission== PackageManager.PERMISSION_GRANTED && coarLocationPermission== PackageManager.PERMISSION_GRANTED
    }


    private fun checkPermissionsrRetailer(): Boolean {
        val phoneStatePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
        return phoneStatePermission == PackageManager.PERMISSION_GRANTED
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        getFirebaseToken()
        setDataHeader()
        checkAndStartKeyTimer()
        if (logintype.equals(Customer)) {
            hitApiForCustomerLogin(preference.getStringValue(ConstantClass.CustomerCode, ""))
            HitApiForEmiList()
            hitApiForUploadLatLong()
            // 🔁 Setup periodic once only
            if(latitude > 0.0 && longitude > 0.0){
                val lastLat = preference
                    .getStringValue(ConstantClass.CUREENTLAT, "")
                    ?.toDoubleOrNull()

                val lastLong = preference
                    .getStringValue(ConstantClass.CUREENTLONGG, "")
                    ?.toDoubleOrNull()


                val hasLocationChanged = lastLat == null || lastLong == null || kotlin.math.abs(latitude - lastLat) > 0.0001 || kotlin.math.abs(longitude - lastLong) > 0.0001

                if (hasLocationChanged) {
                    scheduleOneTimeLocationWorker(latitude, longitude)
                }
                scheduleLocationWorker(latitude, longitude)
            }
            else {
                getCurrentLocation()
            }
            setupPeriodicWork()
            initiateBlocking(CheckCompleteEmiStatus)
        }
        else {
            PanFirstName = ""
            PanMiddleName = ""
            PanLastName = ""
            PanMobileNumber = ""
            PanEmailId = ""
            PanBuilding = ""
            PanAddress = ""
            PanPinCode = ""
            PanAddress = ""
            PanState = ""
            PanCity = ""
            PanCountry = ""

            if (isInternetAvailable(this@DashBoard)) {
                hitApiForRetailerWalletAmount()
            }

            hitApiForLogin(preference.getStringValue(ConstantClass.RetailerCode, ""))
            var request = SendNotificationFeatureNameRequest(
                clientCode = ConstantClass.ClientCode,
                customerCode =  preference.getStringValue(ConstantClass.CustomerCode,""),
                retailerCode = preference.getStringValue(ConstantClass.RetailerCode,""),
                title = "EMI Overdue",
                message = "Hello",
                notificationCode = "EMI_OVERDUE"
            )

            sendDataOnServerForFeatureActivate(request)
        }

    }

    override fun onPause() {
        super.onPause()
        countDownTimer?.cancel()
    }

    fun getFirebaseToken(){
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.e("FCM", "Fetching token failed", task.exception)
                    return@addOnCompleteListener
                }
                val fcmToken = task.result
                preference.setStringValue(ConstantClass.FCMTOKEN,fcmToken)
                Log.d("FCM_TOKEN", fcmToken)
            }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun setDataHeader() {
        val firstName = preference.getStringValue(ConstantClass.FirstName, "").orEmpty()
        val lastName = preference.getStringValue(ConstantClass.LastName, "").orEmpty()
        val emailId = preference.getStringValue(ConstantClass.CustomerEmailID, "").orEmpty()
        val logintype = preference.getStringValue(ConstantClass.LoginType, "").orEmpty()

        val safeLastName = if (!lastName.isNullOrBlank() && lastName != "null") lastName else ""
        val safeEmail = if (!emailId.isNullOrBlank() && emailId != "null") emailId else ""

        headerBinding.username.text = firstName.plus(" ").plus(safeLastName)
        headerBinding.emailid.text = safeEmail

        if (logintype.equals(Retailer)) {
            binding.appBarDashBoard.deskdesign.retailersDashboard.visibility = View.VISIBLE
            binding.appBarDashBoard.deskdesign.customerDashboard.visibility = View.GONE
             binding.navWallet.visibility = View.VISIBLE
             binding.navAddaccount.visibility = View.VISIBLE
            binding.appBarDashBoard.deskdesign.subtitle.text = "One Tap to Your Next Loan"

        } else {
            binding.navWallet.visibility = View.GONE
            binding.navAddaccount.visibility = View.GONE
            binding.appBarDashBoard.deskdesign.retailersDashboard.visibility = View.GONE
            binding.appBarDashBoard.deskdesign.customerDashboard.visibility = View.VISIBLE
            binding.appBarDashBoard.deskdesign.subtitle.text = "Track Your Loan. Pay with Ease"

            var accessKey = preference.getBoolanValue(ConstantClass.CustomerAccessKey,false)
            var generateKey = preference.getStringValue(ConstantClass.GENERATEKEY,"")

            if(accessKey){
                binding.appBarDashBoard.deskdesign.customerGenerateKeyLayout.visibility = View.GONE
                binding.appBarDashBoard.deskdesign.customerdashboardItemlayout.visibility = View.VISIBLE
            }
            else{
                binding.appBarDashBoard.deskdesign.customerGenerateKeyLayout.visibility = View.VISIBLE
                binding.appBarDashBoard.deskdesign.customerdashboardItemlayout.visibility = View.GONE
                if(generateKey.isNotEmpty()){
                    binding.appBarDashBoard.deskdesign.generatedkey.visibility = View.VISIBLE
                    binding.appBarDashBoard.deskdesign.clicktologin.visibility = View.VISIBLE
                    binding.appBarDashBoard.deskdesign.generatedkey.text = generateKey
                }else{
                    binding.appBarDashBoard.deskdesign.generatedkey.visibility = View.GONE
                    binding.appBarDashBoard.deskdesign.clicktologin.visibility = View.GONE
                }

            }


        }


    }

    fun setonclickListner() {


        binding.appBarDashBoard.swiperefresh.setOnRefreshListener {
            if (isInternetAvailable(this@DashBoard)) {
                hitApiForRetailerWalletAmount()
                getFirebaseToken()
                binding.appBarDashBoard.swiperefresh.isRefreshing = true
            }
        }


        binding.installAppLayout.setOnClickListener {
            startActivity(Intent(this, CustomerAppInstall::class.java))
        }


        binding.appBarDashBoard.deskdesign.customerGenerateKeyLayout.setOnClickListener{

            val currentCount = preference.getIntValue(ConstantClass.GENERATE_KEY_COUNT, 0)

            if (currentCount >3) {
                binding.appBarDashBoard.deskdesign.tvTimer.visibility = View.VISIBLE
                binding.appBarDashBoard.deskdesign.tvTimer.text = "Maximum attempts reached"
                binding.appBarDashBoard.deskdesign.generatedkey.visibility = View.GONE
                binding.appBarDashBoard.deskdesign.clicktologin.visibility = View.VISIBLE
                return@setOnClickListener
            }

            var generateKey = preference.getStringValue(ConstantClass.GENERATEKEY, "")

            if (generateKey.isNotEmpty() && binding.appBarDashBoard.deskdesign.generatedkey.text != "Key Expired") {
                val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                val loanDetails = sharedPref.getString("LoanData", "")
                if(loanDetails.isNullOrBlank()){
                    Toast.makeText(this,resources.getString(R.string.customerdashboard), Toast.LENGTH_LONG).show()
                }
                else {
                   // showContinueDialog()
                }
            }

            else {
                if (canGenerateKey()) {
                    hitApiForGetAndCheckAccessToken()
                }
            }


        }


        binding.appBarDashBoard.deskdesign.clicktologin.setOnClickListener {
           /* val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val loanDetails = sharedPref.getString("LoanData", "")
            if(loanDetails.isNullOrBlank()){
                Toast.makeText(this,resources.getString(R.string.customerdashboard), Toast.LENGTH_LONG).show()
            }
            else{

            }*/
            showContinueDialog()
        }


        binding.logout.setOnClickListener {
            OpenPopUpForVeryfyOTP()
        }


        binding.navProfile.setOnClickListener {
            binding.drawerLayout.closeDrawers()
            startActivity(Intent(this, RetailerProfilePage::class.java))
        }


        binding.navWallet.setOnClickListener {
            binding.drawerLayout.closeDrawers()
            startActivity(Intent(this, WalletAccountDetails::class.java))
        }


        binding.navAddaccount.setOnClickListener {
            binding.drawerLayout.closeDrawers()
            startActivity(Intent(this, BankDetailsPage::class.java))
        }


        binding.appBarDashBoard.deskdesign.walletcard.setOnClickListener {
            startActivity(Intent(this, WalletAccountDetails::class.java))
        }


        binding.appBarDashBoard.deskdesign.profile.setOnClickListener {
            startActivity(Intent(this, RetailerProfilePage::class.java))
        }


        binding.appBarDashBoard.deskdesign.customerprofile.setOnClickListener {
            startActivity(Intent(this, RetailerProfilePage::class.java))
        }


        binding.appBarDashBoard.deskdesign.menuicon.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }


        headerBinding.drawableclose.setOnClickListener {
            binding.drawerLayout.closeDrawers()
        }


        binding.appBarDashBoard.deskdesign.emicard.setOnClickListener {
            ConstantClass.ClickOnCardDashboard = "Product"
            startActivity(Intent(this@DashBoard, MobileSelectionActivity::class.java))
        }


        binding.appBarDashBoard.deskdesign.customer.setOnClickListener {
            ConstantClass.ClickOnCardDashboard = "Customer"
            startActivity(Intent(this@DashBoard, IDVerificationPage::class.java))
        }


        binding.appBarDashBoard.deskdesign.customeremicard.setOnClickListener {
            startActivity(Intent(this@DashBoard, CustomerEMIPage::class.java))
        }


       /* binding.appBarDashBoard.deskdesign.customerMakePaymentCard.setOnClickListener {
            if (clickMakePaymentPage) {
                startActivity(Intent(this@DashBoard, MakePaymentPage::class.java))
            } else {
                Toast.makeText(this@DashBoard, "All your EMIs are completed.", Toast.LENGTH_SHORT)
                    .show()
            }

        }*/


        binding.appBarDashBoard.deskdesign.credit.setOnClickListener {
            startActivity(Intent(this@DashBoard, ReportSelectionPage::class.java))
        }


        binding.appBarDashBoard.deskdesign.customerreports.setOnClickListener {
            startActivity(Intent(this@DashBoard, CustomerReportsPage::class.java))
        }


    }

    private fun canGenerateKey(): Boolean {
        val currentCount = preference.getIntValue(ConstantClass.GENERATE_KEY_COUNT, 0)
        val lastTime = preference.getLongValue(ConstantClass.LAST_GENERATE_TIME, 0L)
        val currentTime = System.currentTimeMillis()

        if (currentCount > 3) {
            binding.appBarDashBoard.deskdesign.tvTimer.visibility = View.VISIBLE
            binding.appBarDashBoard.deskdesign.tvTimer.text = "Maximum attempts reached"
            binding.appBarDashBoard.deskdesign.generatedkey.visibility = View.GONE
            binding.appBarDashBoard.deskdesign.clicktologin.visibility = View.GONE
            return false
        }

        val diff = currentTime - lastTime
        val waitTime = 2 * 60 * 1000 // 2 minutes

        if (diff < waitTime) {
            val remainingMillis = waitTime - diff
            startTimer(remainingMillis)
            return false
        }

        return true
    }

    private fun checkAndStartKeyTimer() {
        val currentCount = preference.getIntValue(ConstantClass.GENERATE_KEY_COUNT, 0)
        val lastTime = preference.getLongValue(ConstantClass.LAST_GENERATE_TIME, 0L)
        val currentTime = System.currentTimeMillis()
        val waitTime = 2 * 60 * 1000 // 2 minutes
        val diff = currentTime - lastTime

        if (currentCount > 3) {
            binding.appBarDashBoard.deskdesign.tvTimer.visibility = View.VISIBLE
            binding.appBarDashBoard.deskdesign.tvTimer.text = "Maximum attempts reached"
            binding.appBarDashBoard.deskdesign.generatekeyButton.isEnabled = false
            binding.appBarDashBoard.deskdesign.generatekeyButton.alpha = 0.5f
            binding.appBarDashBoard.deskdesign.generatedkey.visibility = View.GONE
            binding.appBarDashBoard.deskdesign.clicktologin.visibility = View.GONE
            return
        }

        if (diff < waitTime) {
            startTimer(waitTime - diff)
        } else {
            binding.appBarDashBoard.deskdesign.tvTimer.visibility = View.GONE
            binding.appBarDashBoard.deskdesign.generatekeyButton.isEnabled = true
            binding.appBarDashBoard.deskdesign.generatekeyButton.alpha = 1.0f
            if (currentCount > 0) {
                binding.appBarDashBoard.deskdesign.tvGenerateKey.text = "Regenerate Key"
                binding.appBarDashBoard.deskdesign.generatedkey.text = "Key Expired"
                binding.appBarDashBoard.deskdesign.generatedkey.visibility = View.VISIBLE
                binding.appBarDashBoard.deskdesign.clicktologin.visibility = View.GONE
            }
        }
    }

    private fun startTimer(duration: Long) {
        countDownTimer?.cancel()
        binding.appBarDashBoard.deskdesign.tvTimer.visibility = View.VISIBLE
        binding.appBarDashBoard.deskdesign.generatekeyButton.isEnabled = false
        binding.appBarDashBoard.deskdesign.generatekeyButton.alpha = 0.5f

        countDownTimer = object : android.os.CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = (millisUntilFinished / 1000) / 60
                val seconds = (millisUntilFinished / 1000) % 60
                binding.appBarDashBoard.deskdesign.tvTimer.text = 
                    String.format("Next attempt in %02d:%02d", minutes, seconds)
            }

            override fun onFinish() {
                val currentCount = preference.getIntValue(ConstantClass.GENERATE_KEY_COUNT, 0)
                binding.appBarDashBoard.deskdesign.tvTimer.visibility = View.GONE
                binding.appBarDashBoard.deskdesign.generatekeyButton.isEnabled = true
                binding.appBarDashBoard.deskdesign.generatekeyButton.alpha = 1.0f
                if (currentCount > 0) {
                    binding.appBarDashBoard.deskdesign.tvGenerateKey.text = "Regenerate Key"
                    binding.appBarDashBoard.deskdesign.generatedkey.text = "Key Expired"
                    binding.appBarDashBoard.deskdesign.generatedkey.visibility = View.VISIBLE
                    binding.appBarDashBoard.deskdesign.clicktologin.visibility = View.GONE
                }
            }
        }.start()
    }
    

    private fun showContinueDialog() {
        AlertDialog.Builder(this)
            .setTitle("Confirmation")
            .setMessage("Are you sure you want to proceed to the dashboard?")
            .setPositiveButton("Ok") { dialog, _ ->
                preference.setBooleanValue(ConstantClass.CustomerAccessKey, true)
                binding.appBarDashBoard.deskdesign.customerGenerateKeyLayout.visibility = View.GONE
                binding.appBarDashBoard.deskdesign.customerdashboardItemlayout.visibility = View.VISIBLE
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }


    fun hitApiForGetAndCheckAccessToken(){

        val token = if (preference.getStringValue(ConstantClass.FCMTOKEN, "").isNullOrBlank()) {
            Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        } else {
            preference.getStringValue(ConstantClass.FCMTOKEN, "")
        }

        val generateTokenReq = GenerateAccessTokenRequest(fcmToken = token)

        Log.d("tokenreq", Gson().toJson(generateTokenReq))

        viewModel.getAccessKeyForValidateAPKReq(generateTokenReq).observe(this) { resources ->
            when (resources.apiStatus) {

                ApiStatus.SUCCESS -> {
                    resources.data?.body()?.let { response ->

                        Log.d("tokenresp", response.message ?: "")
                        Log.d("tokenmessage", Gson().toJson(response))

                        ConstantClass.dialog.dismiss()

                        uploadDataOnFirebaseConsole(
                            Gson().toJson(response),
                            "CurrentLocation"
                        )

                        if (response.success == true) {
                            val currentCount = preference.getIntValue(ConstantClass.GENERATE_KEY_COUNT, 0)
                            preference.setIntValue(ConstantClass.GENERATE_KEY_COUNT, currentCount + 1)
                            preference.setLongValue(ConstantClass.LAST_GENERATE_TIME, System.currentTimeMillis())
                            checkAndStartKeyTimer()

                            binding.appBarDashBoard.deskdesign.generatedkey.visibility = View.VISIBLE
                            binding.appBarDashBoard.deskdesign.clicktologin.visibility = View.VISIBLE
                            binding.appBarDashBoard.deskdesign.generatedkey.text = response.data?.apiacessKey ?: ""
                            preference.setStringValue(ConstantClass.GENERATEKEY,response.data?.apiacessKey ?: "")
                        }
                        else{
                            // If response is false, maybe it's because of server side limit
                            // You can also force count to 3 here if you want to block forever based on server response
                            preference.setIntValue(ConstantClass.GENERATE_KEY_COUNT, 3)
                           // preference.setBooleanValue(ConstantClass.CustomerAccessKey, true)
                            checkAndStartKeyTimer()
                        }
                    }
                }

                ApiStatus.ERROR -> {
                    ConstantClass.dialog.dismiss()
                }

                ApiStatus.LOADING -> {
                    ConstantClass.OpenPopUpForVeryfyOTP(this)
                }
            }
        }



    }


    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        else{
            if (count == 0) {
                count++
                Toast.makeText(this@DashBoard, "Press again to exit", Toast.LENGTH_SHORT).show()
            } else {
                super.onBackPressed()
            }
        }


    }


    fun OpenPopUpForVeryfyOTP() {
        dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.signoutalert)

        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        dialog.setCanceledOnTouchOutside(false)

        val cancel = dialog.findViewById<Button>(R.id.btnCancel)
        val done = dialog.findViewById<Button>(R.id.btnLogout)

        done.setOnClickListener {
            if (logintype.equals(Retailer)){
                hitApiForRetailerLogout()
            }
            else{
                preference.setBooleanValue(ConstantClass.LoggedIn, false)
                preference.setStringValue(ConstantClass.LoginType, "")
                ConstantClass.ClickOnCardDashboard = ""
                val intent = Intent(this@DashBoard, ChooseYourRolePage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
           
        }

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


    fun hitApiForRetailerWalletAmount() {

        var registrationID = preference.getStringValue(ConstantClass.RetailerCode, "")

        if(registrationID.isNotEmpty()) {

            var request = RetailerWalletAmountReq(
                retailerID = registrationID,
                amountType = "CreditBalance"
            )

            Log.d("walletAmountReq", Gson().toJson(request))
            viewModel.getRetailerWalletAmountReq(request).observe(this) { resources ->
                resources.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {

                            it.data?.let { users ->
                                users.body()?.let { response ->

                                    /* if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                    ConstantClass.dialog.dismiss()
                                    }*/

                                    Log.d("Walletamount", response.walletBalance!!)
                                    binding.appBarDashBoard.swiperefresh.isRefreshing = false

                                    val walletAmount = response.walletBalance!!.toDoubleOrNull() ?: 0.0
                                    val holdAmount = response.holdAmount!!.toDoubleOrNull() ?: 0.0
                                    val maxholdAmount = response.maxholdAmount!!.toDoubleOrNull() ?: 0.0
                                    val minholdAmount = response.miniholdamountrequest!!.toDoubleOrNull() ?: 0.0
                                    val loanSecurityHoldAmount = response.loanSecurityHoldAmount!!.toDoubleOrNull() ?: 0.0

                                    val myWalletAmount = walletAmount
                                    val myWalletAmountStr = String.format("%.2f", myWalletAmount)

                                    WalletBalance = myWalletAmountStr
                                    HoldAmount = String.format("%.2f", holdAmount)
                                    MaxHoldingAmount = String.format("%.2f", maxholdAmount)
                                    MinHoldingAmount = String.format("%.2f", minholdAmount)
                                    LoanSecurityHoldAmount = String.format("%.2f", loanSecurityHoldAmount)
                                    binding.appBarDashBoard.deskdesign.walletamount.text = formatIndianAmount(myWalletAmountStr)

                                }
                            }

                        }

                        ApiStatus.ERROR -> {
                            if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                ConstantClass.dialog.dismiss()
                            }
                            hitApiForRetailerWalletAmount()

                            // ✅ Print the full error details
                            Log.e("API_ERROR", "Status: ERROR")
                            Log.e("API_ERROR_CODE", resources.data?.code().toString())
                            Log.e("API_ERROR_MSG", resources.message ?: "Unknown Error")

                            /*Toast.makeText(
                                this,
                                "Server error occurred (Code: ${resources.data?.code() ?: "Unknown"})",
                                Toast.LENGTH_LONG
                            ).show()*/

                            // Optional: Handle specific 500 error
                            if (resources.data?.code() == 500) {
                                Log.e("API_ERROR", "Internal Server Error from backend.")
                            }
                            binding.appBarDashBoard.swiperefresh.isRefreshing = false

                        }

                        ApiStatus.LOADING -> {
                            // ConstantClass.OpenPopUpForVeryfyOTP(this)
                        }

                    }
                }
            }

        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun HitApiForEmiList() {
        var loanemireq = GetCustomerLoanDetailsReq(
            loancode = "",
            customercode = preference.getStringValue(ConstantClass.CustomerCode, "")
        )
        Log.d("customerloanEmireq", Gson().toJson(loanemireq))

        viewModel.getCustomerLoanEmiDetailsReq(loanemireq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("customerLoanemiresp", Gson().toJson(response))
                                val loanList = response.data
                                val currentDate = response.indiaTimeIST
                                Log.d("DashboardCurrentDate","$currentDate")

                                listOfDueWithGraceDate.clear()

                                lifecycleScope.launch {

                                    loanList!!.forEach { item ->
                                        var startDate = item?.startDate?.toString()?:""

                                        if(startDate.isNotBlank()){
                                            val dueData = formatDateToDDMMYYYY(startDate).getCurrentLastPaidDueDate(this@DashBoard, item?.paidEMI!!.toLong(), item?.duesEMI!!.toLong(), item.gracePeriod!!.toInt(),item.customerGracePeriod!!.toInt(),currentDate!!)
                                            listOfDueWithGraceDate.addAll(dueData)
                                            Log.d("DueDataAlert", "Data $dueData")
                                        }
                                    }

                                    preference.setStringValue(ConstantClass.EMILIST,Gson().toJson(listOfDueWithGraceDate))
                                    uploadDataOnFirebaseConsole(Gson().toJson(listOfDueWithGraceDate),"listOfDueWithGraceDateForAlert")

                                    Log.d("currentDate", "$currentDate")

                                    val currentmillis = convertDateToMillis(currentDate!!.convertDate())

                                    val sdf = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
                                    sdf.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
                                    Log.d("CHECK", sdf.format(Date(currentmillis)))


                                    listOfDueWithGraceDate.forEach { dueItem ->
                                        val dueMillis = convertDateToMillis(dueItem.dueDateWithGross)

                                        val diffDays = TimeUnit.MILLISECONDS.toDays(dueMillis - currentmillis)

                                        Log.d("EMI_CHECK", "DueDate=${dueItem.dueDateWithGross}, diffDays=$diffDays")

                                        if (diffDays in 1..3) {
                                            setupEmiWorkManager(dueMillis)
                                        }
                                    }

                               /*
                                 // doing for testing purpose.................................................
                                 val testDueMillis = System.currentTimeMillis() + 15000 // after 15 seconds
                                    setupEmiWorkManager(testDueMillis)*/

                                }


                                val allEmiDone = loanList!!.all { loan ->
                                    loan!!.tenure.toString() == loan.paidEMI
                                }

                                if (allEmiDone) {
                                    clickMakePaymentPage = false
                                    Log.d("CheckEMI", "$clickMakePaymentPage")

                                }
                                else {
                                    clickMakePaymentPage = true
                                    Log.d("CheckEMI", "$clickMakePaymentPage")
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


    fun convertDateToMillis(date: String): Long {
        val format = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        format.timeZone = TimeZone.getTimeZone("Asia/Kolkata") // or UTC (be consistent)
        return format.parse(date)?.time ?: 0L
    }


    private fun setupEmiWorkManager(dueMillis: Long) {
        val delay = dueMillis - System.currentTimeMillis()
        WorkManager.getInstance(this).enqueue(OneTimeWorkRequestBuilder<EmiNotificationWorker>().setInitialDelay(delay, TimeUnit.MILLISECONDS).
            setInputData(androidx.work.Data.Builder().putLong("due_date", dueMillis).build()).build()
        )

    }

    private fun setupPeriodicWork() {
        val workRequest = PeriodicWorkRequestBuilder<EmiNotificationWorker>(6, TimeUnit.HOURS).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("EMI_ALERT_WORK", ExistingPeriodicWorkPolicy.UPDATE, workRequest)
    }

    fun hitApiForCustomerLogin(retailerOrCustomerCode: String) {

       var deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
       preference.setStringValue(ConstantClass.DEVICEID,deviceId)

        var sessionOutReq = SessionOutReq(
            retailerCode = retailerOrCustomerCode,
        )

        Log.d("SessionOutReq", Gson().toJson(sessionOutReq))

        viewModel.getSessionReq(sessionOutReq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("SessionOutResponse", Gson().toJson(response))
                                if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                    ConstantClass.dialog.dismiss()
                                }
                                ConstantClass.checkActiveStatusAndLogout(this@DashBoard, response.status, preference)
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

    fun hitApiForLogin(retailerOrCustomerCode: String) {

        var deviceId = Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
        preference.setStringValue(ConstantClass.DEVICEID,deviceId)

        var sessionOutReq = SessionOutReq(
            retailerCode = retailerOrCustomerCode,
        )

        Log.d("SessionOutReq", Gson().toJson(sessionOutReq))

        viewModel.getSessionReq(sessionOutReq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("SessionOutResponse", Gson().toJson(response))
                                if (ConstantClass.dialog != null && ConstantClass.dialog.isShowing) {
                                    ConstantClass.dialog.dismiss()
                                }
                                ConstantClass.checkActiveStatusAndLogout(this@DashBoard, response.status, preference)
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


        var request = ValidateSessionRequest(
            preference.getStringValue(ConstantClass.RetailerCode, ""),
            deviceId,
            preference.getStringValue(ConstantClass.FCMTOKEN, "")
        )

        Log.d("validaterequest", Gson().toJson(request))
        viewModel.getSessionExpiredReq(request).observe(this){resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("validateresp", Gson().toJson(response))
                                if(response.status==0){
                                    hitApiForRetailerLogout()
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



    fun hitApiForRetailerLogout() {
        var loginRequest = LogoutReq(
            retailerCode = preference.getStringValue(ConstantClass.RetailerCode, ""),
        )

        Log.d("LogoutReq", Gson().toJson(loginRequest))

        viewModel.getLogout(loginRequest).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("LogoutResponse", Gson().toJson(response))
                                preference.setBooleanValue(ConstantClass.LoggedIn, false)
                                preference.setStringValue(ConstantClass.LoginType, "")
                                ConstantClass.ClickOnCardDashboard = ""
                                val intent = Intent(this@DashBoard, ChooseYourRolePage::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
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

    fun hitApiForUploadLatLong() {
        val modelName = Build.MODEL
        val product = Build.PRODUCT
        val brand = Build.BRAND
        val manufecturer = Build.MANUFACTURER
        Log.d("DeviceDetails","$modelName $product $brand $manufecturer")
        /*   var locationRequest = CustomerlocationUploadReq (
            taskType = "INS",
            locationAuditID = 0,
            latitude = lat,
            ipAddress = deviceIp,
            customerCode = preference.getStringValue(ConstantClass.CustomerCode, ""),
            retailerCode = preference.getStringValue(ConstantClass.RetailerCode, ""),
            loanCode = "",
            userName = preference.getStringValue(ConstantClass.CustomerMobileNumber, ""),
            longitude = long,
        )

        Log.d("locationReq", Gson().toJson(locationRequest))

        viewModel.uploadcustomerlocation(locationRequest).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("LocationResponse", Gson().toJson(response))
                            }
                        }
                    }

                    ApiStatus.ERROR -> {

                    }

                    ApiStatus.LOADING -> {

                    }
                }
            }
        }*/

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentLocation() {

        val fused = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        if (fused.lastLocation != null) {
            fused.lastLocation.addOnSuccessListener {
                it?.let { location ->
                    latitude = location.latitude
                    longitude = location.longitude

                    Log.d("LatLongg", "$latitude $longitude")
                    Log.d("CurrentLocation", "Lat: $latitude, Long: $longitude")

                    uploadDataOnFirebaseConsole(
                        "Lat: $latitude, Long: $longitude",
                        "CurrentLocation"
                    )
                } ?: run {
                    Log.e("CurrentLocation", "Location is null. Check GPS or Permissions.")
                }
            }

        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show()
                getCurrentLocation()
            } else {
                Toast.makeText(this, "Permissions Denied", Toast.LENGTH_SHORT).show()
            }
        }

    }



    fun sendDataOnServerForFeatureActivate(request : SendNotificationFeatureNameRequest){

        viewModel.sendNotificationFeatureNameReq(request).observe(this) { it ->

            when (it.apiStatus) {
                ApiStatus.LOADING -> {

                }

                ApiStatus.SUCCESS ->{
                    val response = it.data?.body()
                    Log.d("LoginResponse", Gson().toJson(response))

                }

                ApiStatus.ERROR -> {
                    ConstantClass.dialog.dismiss()
                    // 👇 Show proper error from ViewModel (404, 500 etc.)
                    val errorMessage = it.message ?: "Something went wrong"

                    Log.e("LoginError", errorMessage)
                }
            }

        }


    }


}
package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

import com.bosandroidapp.oqmobilefinance.databinding.ActivityAadharCardWebViewDigilockerPageBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadhaarResponse
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharTransactionIdNo
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanDOB
import com.bosandroidapp.oqmobilefinance.data.model.SessionOutReq
import com.bosandroidapp.oqmobilefinance.data.model.ValidateSessionRequest
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LogoutReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.AAdhaarDetailesReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.repository.CibilRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CibilViewModelFactory
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.ui.view.activity.ChooseYourRolePage
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.CibilViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AadharCardWebViewDIGILockerPage : BaseActivity() {
    lateinit var binding: ActivityAadharCardWebViewDigilockerPageBinding
    lateinit var viewModel: AuthenticationViewModel
    private lateinit var viewCibilModel: CibilViewModel
    lateinit var preference: SharedPreference


    companion object {
        var digilockerLink: String = ""
        var VerifiedID: String = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAadharCardWebViewDigilockerPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]

        viewCibilModel = ViewModelProvider(this, CibilViewModelFactory(CibilRepository(RetrofitClient.apiInterfacePAN)))[CibilViewModel::class.java]
        preference = SharedPreference(this)

        setDataInWebView()

    }


    override fun onResume() {
        super.onResume()

        hitApiForLogin()
    }


    fun setDataInWebView() {

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.domStorageEnabled = true

        binding.webview.addJavascriptInterface(object {

            var isCalled = false

            @JavascriptInterface
            fun onUrlChange(url: String) {
                Log.d("JS_URL", url)

                if (url.contains("success") && !isCalled) {
                    isCalled = true

                    (this@AadharCardWebViewDIGILockerPage).runOnUiThread {
                        hitApiForAadharVerification(AadharTransactionIdNo)
                    }
                }
            }
        }, "Android")

        binding.webview.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                Log.d("WEBVIEW", "Loaded URL: $url")

                // ✅ Inject JS AFTER page load
                injectJs(view)
            }
        }

        // ✅ Load URL AFTER setup
        binding.webview.loadUrl(digilockerLink)
    }


    fun injectJs(webView: WebView?) {
        webView?.evaluateJavascript("""
        (function() {

            function notify() {
                Android.onUrlChange(window.location.href);
            }

            var pushState = history.pushState;
            history.pushState = function() {
                pushState.apply(history, arguments);
                notify();
            };

            var replaceState = history.replaceState;
            history.replaceState = function() {
                replaceState.apply(history, arguments);
                notify();
            };

            window.addEventListener('popstate', notify);

            notify(); // initial trigger
        })();
    """.trimIndent(), null)
    }


    override fun onBackPressed() {

        if (binding.webview.canGoBack()) {
            binding.webview.goBack()
        } else {
            if (VerifiedID.startsWith("Error")) {
                ConstantClass.AadharVerified = "no"
            }
            super.onBackPressed()
        }


    }


    fun hitApiForLogin() {

        var sessionOutReq = SessionOutReq(
            retailerCode = preference.getStringValue(ConstantClass.RetailerCode, ""),
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
                                ConstantClass.checkActiveStatusAndLogout(this@AadharCardWebViewDIGILockerPage, response.status, preference)
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
            preference.getStringValue(ConstantClass.DEVICEID, ""),
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
                                val intent = Intent(this@AadharCardWebViewDIGILockerPage, ChooseYourRolePage::class.java)
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


    fun hitApiForAadharVerification(transactionId: String) {

        var aadharverificationreq = AAdhaarDetailesReq(
            transactionID = transactionId,
            registrationID =  if (ConstantClass.CheckOnlineOrOffline == ConstantClass.online) {
                ConstantClass.PAN_VERIFICATION_REGISTRATION_ID
            } else {
                ConstantClass.PAN_VERIFICATION_REGISTRATION_ID_OFFLINE
            }
        )

        Log.d("AadharDetailsreq", Gson().toJson(aadharverificationreq))

       viewCibilModel.getAAdhaarDetailesReq(aadharverificationreq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data.let { users ->
                            users!!.body().let { response ->
                                ConstantClass.dialog.dismiss()
                                Log.d("AadharDetailsResp", Gson().toJson(response))
                                if (response!!.code.equals("200")) {
                                    ConstantClass.AadharDOB = response.model!!.dob!!
                                    val apiSdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                                    val apiDobDate: Date = apiSdf.parse(ConstantClass.AadharDOB)!!
                                    val formattedApiDob = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(apiDobDate)

                                    if(formattedApiDob != PanDOB){
                                        Toast.makeText(this,"The Date of Birth  does not match the PAN card details. Please use offline mode to continue with your loan process .",Toast.LENGTH_LONG).show()
                                        ConstantClass.AadharDOB=""
                                        ConstantClass.AadharVerified= ""
                                        finish()
                                    }
                                    else{
                                        ConstantClass.AadhaarName = response.model!!.name!!
                                        ConstantClass.AadharHouse = response.model!!.address!!.house!!
                                        ConstantClass.AadharStreet = response.model!!.address!!.street!!
                                        ConstantClass.AadharLoc = response.model!!.address!!.loc!!
                                        ConstantClass.Aadhardist = response.model!!.address!!.dist!!
                                        ConstantClass.AadharPin = response.model!!.address!!.pc!!
                                        ConstantClass.AadharState = response.model!!.address!!.state!!
                                        ConstantClass.AadharCountry = response.model!!.address!!.country!!
                                        ConstantClass.AadharImage = response.model!!.image!!
                                        AadharNumber =  response.model!!.maskedAdharNumber!!
                                        AadhaarResponse = Gson().toJson(response)
                                        ConstantClass.AadharVerified="yes"
                                        val intent = Intent(this, NewCustomerRegistrationPage::class.java)
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                                else{
                                    Toast.makeText(this, "aadhaar not verified", Toast.LENGTH_SHORT).show()
                                    ConstantClass.AadharVerified = ""
                                    finish()
                                }
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(this@AadharCardWebViewDIGILockerPage, resources.message ?: "Error occurred", Toast.LENGTH_SHORT).show()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }
            }
        }

    }

}
package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.WebStorage
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
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.IsAadhaarVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanDOB
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefAadharTransactionIdNo
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ReferenceAadharNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ReferenceAddress
import com.bosandroidapp.oqmobilefinance.data.model.SessionOutReq
import com.bosandroidapp.oqmobilefinance.data.model.ValidateSessionRequest
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LogoutReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.verification.AAdhaarDetailesReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.repository.CibilRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CibilViewModelFactory
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.databinding.ActivityReferenceAadharCardWebViewDigilockerPageBinding
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.ui.view.activity.ChooseYourRolePage
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.PaymentInformation.Companion.checkKYC
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.CibilViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AadharCardReferenceWebViewDIGILockerPage : BaseActivity() {
    lateinit var binding: ActivityReferenceAadharCardWebViewDigilockerPageBinding
    lateinit var viewModel: AuthenticationViewModel
    private lateinit var viewCibilModel: CibilViewModel
    lateinit var preference: SharedPreference


    companion object {
        var digilockerLink: String = ""
        var VerifiedID: String = ""
        var checkAdharForRef: String = ""

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReferenceAadharCardWebViewDigilockerPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]

        viewCibilModel = ViewModelProvider(this, CibilViewModelFactory(CibilRepository(RetrofitClient.apiInterfacePAN)))[CibilViewModel::class.java]
        preference = SharedPreference(this)
        clearWebViewData(binding.refwebview)
        setDataInWebView()

    }

    fun clearWebViewData(webView: WebView) {
        webView.clearCache(true)
        webView.clearHistory()
        webView.clearFormData()

        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
        WebStorage.getInstance().deleteAllData()
    }


    override fun onResume() {
        super.onResume()

        hitApiForLogin()

    }


    fun setDataInWebView() {

        binding.refwebview.settings.javaScriptEnabled = true
        binding.refwebview.settings.domStorageEnabled = true

        binding.refwebview.addJavascriptInterface(object {

            var isCalled = false

            @JavascriptInterface
            fun onUrlChange(url: String) {
                Log.d("JS_URL", url)

                if (url.contains("success") && !isCalled) {
                    isCalled = true

                    (this@AadharCardReferenceWebViewDIGILockerPage).runOnUiThread {
                        hitApiForAadharVerification(RefAadharTransactionIdNo)
                    }
                }
            }
        }, "Android")

        binding.refwebview.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                Log.d("WEBVIEW", "Loaded URL: $url")

                // ✅ Inject JS AFTER page load
                injectJs(view)
            }
        }

        // ✅ Load URL AFTER setup
        binding.refwebview.loadUrl( AadharCardReferenceWebViewDIGILockerPage.digilockerLink)
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

        if (binding.refwebview.canGoBack()) {
            binding.refwebview.goBack()
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
                                ConstantClass.checkActiveStatusAndLogout(this@AadharCardReferenceWebViewDIGILockerPage, response.status, preference)
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
                                val intent = Intent(this@AadharCardReferenceWebViewDIGILockerPage, ChooseYourRolePage::class.java)
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
        val aadharverificationreq = AAdhaarDetailesReq(
            transactionID = transactionId,
            registrationID = if (ConstantClass.CheckOnlineOrOffline == ConstantClass.online) {
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
                                    checkKYC = true
                                    IsAadhaarVerified = "1"
                                    checkAdharForRef="yes"
                                    ConstantClass.ReferenceAadharVerified="yes"
                                    ReferenceAadharNumber =  response.model!!.maskedAdharNumber!!
                                    ConstantClass.ReferenceAadhaarName = response.model!!.name!!
                                    ConstantClass.ReferenceAadharHouse = response.model!!.address!!.house!!
                                    ConstantClass.ReferenceAadharStreet = response.model!!.address!!.street!!
                                    ConstantClass.ReferenceAadharLoc = response.model!!.address!!.loc!!
                                    ConstantClass.ReferenceAadhardist = response.model!!.address!!.dist!!
                                    ConstantClass.ReferenceAadharPin = response.model!!.address!!.pc!!
                                    ConstantClass.ReferenceAadharState = response.model!!.address!!.state!!
                                    ConstantClass.ReferenceAadharCountry = response.model!!.address!!.country!!
                                    ConstantClass.ReferenceAadharImage = response.model!!.image!!
                                    ConstantClass.AadhaarDOB = response.model!!.dob!!
                                    Log.d("Aadhaar", ReferenceAadharNumber)
                                    finish()
                                }
                                else{
                                    checkKYC = false
                                    Toast.makeText(this, "aadhaar not verified", Toast.LENGTH_SHORT).show()
                                    ConstantClass.ReferenceAadharVerified = "no"
                                    IsAadhaarVerified = ""
                                    finish()
                                }
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(this@AadharCardReferenceWebViewDIGILockerPage, resources.message ?: "Error occurred", Toast.LENGTH_SHORT).show()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }
            }
        }

    }



}
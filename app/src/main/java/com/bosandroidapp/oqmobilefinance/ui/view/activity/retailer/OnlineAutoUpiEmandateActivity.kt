package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer

import android.R
import android.app.Activity
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebStorage
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountType
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankIFSCCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustomerCodeForEnach
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.EmiAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RetailerCodeForEnach
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isMandate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isPannydropVerified
import com.bosandroidapp.oqmobilefinance.data.enach.EnachDateUploadReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.repository.DikshifinsureRepository
import com.bosandroidapp.oqmobilefinance.data.upiautomandate.UpiAutoOrderStatusRequest
import com.bosandroidapp.oqmobilefinance.data.upiautomandate.UpiAutoTransactionRequest
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.DikshifinsureOnlinePGModelFactory
import com.bosandroidapp.oqmobilefinance.databinding.ActivityOnlineAutoUpiEmandateBinding
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.CongratulationPage.Companion.loaneCode
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.DikshifinsureViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson
import kotlin.math.roundToInt

class OnlineAutoUpiEmandateActivity : BaseActivity() {
    private lateinit var binding: ActivityOnlineAutoUpiEmandateBinding
    private lateinit var viewModel: AuthenticationViewModel
    private lateinit var dikshifinsureViewModel: DikshifinsureViewModel
    
    private var merchandId: String = ""
    private var registrationId: String = ""
    private var isStatusCheckInProgress = false
    private var isEmandateVerified: String = ""

    lateinit var dialog: Dialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineAutoUpiEmandateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,
            CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface))
        )[AuthenticationViewModel::class.java]
        dikshifinsureViewModel = ViewModelProvider(this,
            DikshifinsureOnlinePGModelFactory(DikshifinsureRepository(RetrofitClient.apiInterfaceOnlinePG))
        )[DikshifinsureViewModel::class.java]

        if (intent.hasExtra(ConstantClass.MarchentOrderID_UPIAUTOPAY) && intent.hasExtra(ConstantClass.RegistrationID_UPIAUTOPAY)) {
            merchandId = intent.getStringExtra(ConstantClass.MarchentOrderID_UPIAUTOPAY).toString()
            registrationId = intent.getStringExtra(ConstantClass.RegistrationID_UPIAUTOPAY).toString()
        }

        val webUrl = RetailerEMandateVerifyPage.webUrl ?: ""
        
        val checkoutUrl = if (webUrl.isNotEmpty()) {
            if (webUrl.contains("?")) {
                "$webUrl&isChromeWV=true"
            } else {
                "$webUrl?isChromeWV=true"
            }
        }
        else {
            ""
        }

        clearWebView(binding.webview)

        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.domStorageEnabled = true
        binding.webview.settings.databaseEnabled = true
        binding.webview.settings.loadsImagesAutomatically = true
        binding.webview.settings.javaScriptCanOpenWindowsAutomatically = true
        binding.webview.settings.allowFileAccess = true
        binding.webview.settings.allowContentAccess = true
        binding.webview.settings.cacheMode = WebSettings.LOAD_DEFAULT
        binding.webview.settings.userAgentString = WebSettings.getDefaultUserAgent(this)

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(binding.webview, true)

        binding.webview.addJavascriptInterface(object {
            @JavascriptInterface
            fun onUrlChange(url: String) {
                Log.d("AUTOUPI_JS_URL", "URL Changed: $url")
                try {
                    if (merchandId.isNotEmpty() && registrationId.isNotEmpty()) {
                        doUpdateUpiAutoMandateStatus()
                    }
                } catch (e: Exception) {
                    Log.e("AUTOUPI_JS_ERROR", "Error handling URL change", e)
                }
            }
        }, "Android")

        binding.webview.webViewClient = MerchantWebViewClient(this)
        
        if (checkoutUrl.isNotEmpty()) {
            Log.d("AUTOUPI_FLOW", "Loading URL: $checkoutUrl")
            binding.webview.loadUrl(checkoutUrl)
        } else {
            Toast.makeText(this, "Invalid checkout URL", Toast.LENGTH_SHORT).show()
        }

    }


    fun clearWebView(webView: WebView) {
        webView.apply {
            clearHistory()
            clearCache(true)
            clearFormData()
            clearSslPreferences()
            CookieManager.getInstance().removeAllCookies(null)
            CookieManager.getInstance().flush()
            WebStorage.getInstance().deleteAllData()
        }
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
            notify(); 
        })();
    """.trimIndent(), null)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2001) {
            Log.d("AUTOUPI", "Returned from UPI app")
            doUpdateUpiAutoMandateStatus()
        }

    }


    fun doUpdateUpiAutoMandateStatus() {
        if (isStatusCheckInProgress) return
        isStatusCheckInProgress = true
        runOnUiThread {
            hitApiForUpiAutoMandateOrderStatus(registrationId, merchandId)
        }
    }


    fun hitApiForUpiAutoMandateOrderStatus(registrationId: String, merchandId: String) {
        val request = UpiAutoOrderStatusRequest(
            registrationID = registrationId,
            merchantOrderId = merchandId
        )
        Log.d("UpiAutoStatusReq", Gson().toJson(request))

        dikshifinsureViewModel.getUpiAutoMandateOrderStatusRequest(request).observe(this) { resources ->

            when (resources.apiStatus) {
                ApiStatus.SUCCESS -> {
                    isStatusCheckInProgress = false

                    if (ConstantClass.dialog?.isShowing == true) {
                        ConstantClass.dialog!!.dismiss()
                    }

                    val response = resources.data?.body()
                    Log.d("UpiAutoStatusRes", Gson().toJson(response))


                    if (response?.state?.lowercase().equals("failed", ignoreCase = true)) {
                        isEmandateVerified= "No"
                        showingRejectioneMandatePopUp(response!!.paymentDetails?.filterNotNull()?.firstOrNull()?.rail?.umn ?: "")
                        return@observe
                    }

                    if (response?.state?.lowercase().equals("completed", ignoreCase = true) == true) {
                        if (response?.paymentDetails.isNullOrEmpty()) {
                            hitApiForUpiAutoMandateTransaction(registrationId)
                        }
                        else {
                            val umn = response?.paymentDetails?.filterNotNull()?.firstOrNull()?.rail?.umn ?: ""
                            isEmandateVerified = isMandate
                            val uploadReq = EnachDateUploadReq(
                                isEmandateVerified = isEmandateVerified,
                                emAccountType = AccountType,
                                isPannydropVerified = isPannydropVerified,
                                emAccountNumber = AccountNumber,
                                customerCode = CustomerCodeForEnach,
                                retailerCode = RetailerCodeForEnach,
                                loanCode = loaneCode,
                                emBankName = BankName,
                                emIfscCode = BankIFSCCode,
                                emumrn = umn
                            )
                            hitApiForUploadEnachMandateDataResponse(uploadReq, isEmandateVerified)
                        }
                    }

                }

                ApiStatus.ERROR -> {
                    isStatusCheckInProgress = false
                    if (ConstantClass.dialog?.isShowing == true) {
                        ConstantClass.dialog!!.dismiss()
                    }
                    Toast.makeText(this, resources.message ?: "Status Check Failed", Toast.LENGTH_SHORT).show()
                }

                ApiStatus.LOADING -> {
                    if (ConstantClass.dialog?.isShowing != true) {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }
                }

            }
        }
    }


    fun hitApiForUpiAutoMandateTransaction(registrationId: String) {
        val emiNumbers = "1"
        val amount = EmiAmount.toDouble().roundToInt()

        val request = UpiAutoTransactionRequest(
            registrationID = registrationId,
            amount = amount,
            eMINumbers = emiNumbers,
            customerCode = CustomerCodeForEnach,
            loanCode = loaneCode
        )
        Log.d("UpiAutoTransReq", Gson().toJson(request))

        dikshifinsureViewModel.getUpiAutoMandateTransactionRequest(request).observe(this) { resources ->
            when (resources.apiStatus) {
                ApiStatus.SUCCESS -> {
                    isStatusCheckInProgress = false
                    if (ConstantClass.dialog?.isShowing == true) {
                        ConstantClass.dialog!!.dismiss()
                    }
                    val response = resources.data?.body()
                    Log.d("UpiAutoTransRes", Gson().toJson(response))

                    if (!response?.intentUrl.isNullOrEmpty()) {
                        merchandId = response?.marchentOrderID ?: ""
                        binding.webview.loadUrl(response?.intentUrl!!)
                    } else {
                        Toast.makeText(this, response?.errorMessage ?: "Transaction trigger failed", Toast.LENGTH_SHORT).show()
                    }
                }
                ApiStatus.ERROR -> {
                    isStatusCheckInProgress = false
                    if (ConstantClass.dialog?.isShowing == true) {
                        ConstantClass.dialog!!.dismiss()
                    }
                    Toast.makeText(this, resources.message ?: "Transaction Failed", Toast.LENGTH_SHORT).show()
                }
                ApiStatus.LOADING -> {
                    if (ConstantClass.dialog?.isShowing != true) {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }
                }
            }
        }
    }


    fun hitApiForUploadEnachMandateDataResponse(request: EnachDateUploadReq, isMandate: String) {

        viewModel.UpdateEmandateDetails(request).observe(this) { resources ->
            when (resources.apiStatus) {
                ApiStatus.SUCCESS -> {
                    if (ConstantClass.dialog?.isShowing == true) {
                        ConstantClass.dialog!!.dismiss()
                    }
                    if (::dialog.isInitialized && dialog.isShowing) {
                        dialog.dismiss()
                    }
                    if (isMandate == ConstantClass.isMandate) {
                        startActivity(Intent(this, AppScanInstallPage::class.java))
                        finish()
                    } else {
                        QRCodePage.isEnachCancelled = true
                        finish()
                    }
                }
                ApiStatus.ERROR -> {
                    if (ConstantClass.dialog?.isShowing == true) {
                        ConstantClass.dialog!!.dismiss()
                    }
                    Toast.makeText(this, "Upload failed: ${resources.message}", Toast.LENGTH_SHORT).show()
                }
                ApiStatus.LOADING -> {
                    if (ConstantClass.dialog?.isShowing != true) {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }
                }
            }
        }

    }


    class MerchantWebViewClient(private val activity: OnlineAutoUpiEmandateActivity) : WebViewClient() {

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            Log.d("AUTOUPI_WEBVIEW", "Page Finished: $url")
            activity.injectJs(view)
        }

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()
            Log.d("AUTOUPI", "URL Loading: $url")

            if (!url.startsWith("https") && !url.startsWith("http")) {
                try {
                    val i = Intent(Intent.ACTION_VIEW)
                    i.setData(Uri.parse(url))
                    activity.startActivityForResult(i, 2001)
                } catch (ignored: ActivityNotFoundException) {
                    Toast.makeText(activity, "No UPI app found", Toast.LENGTH_SHORT).show()
                }
                return true
            }
            return false
        }

    }


    override fun onBackPressed() {
        isEmandateVerified= "No"
        showingRejectioneMandatePopUp("")
    }


    fun showingRejectioneMandatePopUp(emumrn: String){
        dialog = Dialog(this, R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(com.bosandroidapp.oqmobilefinance.R.layout.enach_reject_alert)

        dialog!!.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
        }

        var Ok = dialog!!.findViewById<Button>(com.bosandroidapp.oqmobilefinance.R.id.btnOk)


        Ok.setOnClickListener {

            if(!isEmandateVerified.isNullOrBlank()){

                var request = EnachDateUploadReq(
                    isEmandateVerified = isEmandateVerified,
                    emAccountType = AccountType,
                    isPannydropVerified = isPannydropVerified,
                    emAccountNumber = AccountNumber,
                    customerCode = CustomerCodeForEnach,
                    retailerCode= RetailerCodeForEnach,
                    loanCode= loaneCode,
                    emBankName=BankName,
                    emIfscCode =BankIFSCCode,
                    emumrn = emumrn
                )

                hitApiForUploadEnachMandateDataResponse(request,isEmandateVerified)
            }



        }

        dialog!!.setCanceledOnTouchOutside(false)

        dialog!!.show()

    }


}
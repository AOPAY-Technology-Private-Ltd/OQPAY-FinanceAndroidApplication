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
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.AppCompatButton
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
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.PGWebViewActivity.Companion.EMIamountPG
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.PGWebViewActivity.Companion.LoanCodePG
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.PGWebViewActivity.Companion.emiList
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

    private var isPaymentProcessed = false

    fun handleUrlChange(url: String) {
        if (isPaymentProcessed) return

        try {
            val uri = Uri.parse(url)
            val status = (uri.getQueryParameter("Status") ?: uri.getQueryParameter("status"))?.uppercase()
            val merchantOrderId = uri.getQueryParameter("MerchantOrderId")

            Log.d("AUTOUPI_STATUS", "Payment Status: $status")
            Log.d("AUTOUPI_UTR", "Merchant Order ID: $merchantOrderId")

            when {
                // Payment Success
                status == "SUCCESS" || uri.path?.contains("/success", ignoreCase = true) == true -> {
                    isPaymentProcessed = true
                    Log.d("PAYU", "Payment Success: $url")

                    runOnUiThread {
                        binding.webview.stopLoading()
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
                            emumrn = merchantOrderId
                        )

                        hitApiForUploadEnachMandateDataResponse(uploadReq, isEmandateVerified)
                    }
                }

                // Payment Failed
                status == "FAILED" || uri.path?.contains("/failure", ignoreCase = true) == true -> {
                    isPaymentProcessed = true
                    Log.d("PAYU", "Payment Failed: $url")

                    runOnUiThread {
                        binding.webview.stopLoading()
                        isEmandateVerified = "No"
                        showingRejectioneMandatePopUp("",isEmandateVerified)
                    }
                }

                // Payment Cancelled
                status == "CANCEL" || status == "CANCELLED" || uri.path?.contains("/cancel", ignoreCase = true) == true || uri.getQueryParameter("action")?.equals("userCancel", ignoreCase = true) == true -> {
                    isPaymentProcessed = true
                    Log.d("PAYU", "Payment Cancelled: $url")

                    runOnUiThread {
                        binding.webview.stopLoading()
                        isEmandateVerified = "No"
                        showingRejectioneMandatePopUp("",isEmandateVerified)
                    }
                }

                else -> {
                    Log.d("PAYU", "Other URL: $url")
                }
            }

        } catch (e: Exception) {
            Log.e("AUTOUPI_JS_ERROR", "Error handling URL change", e)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnlineAutoUpiEmandateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                isEmandateVerified = "No"
                showingRejectioneMandatePopUp("",isEmandateVerified)
            }
        })

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface))
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
                handleUrlChange(url)
            }

        }, "Android")

        binding.webview.webViewClient = MerchantWebViewClient(this)
        
        if (checkoutUrl.isNotEmpty()) {
            Log.d("AUTOUPI_FLOW", "Loading URL: $checkoutUrl")
            binding.webview.loadUrl(checkoutUrl)
        }
        else {
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
        webView?.evaluateJavascript(
            """
    (function() {

        // -----------------------------
        // URL CHANGE LISTENER
        // -----------------------------

        function notify() {
            try {
                Android.onUrlChange(window.location.href);
            } catch (e) {
                console.log("Android.onUrlChange error:", e);
            }
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


        // -----------------------------
        // PAYMENT REQUEST CHECK
        // -----------------------------

        console.log(
            "PaymentRequest available:",
            typeof window.PaymentRequest !== "undefined"
        );

        console.log(
            "PaymentRequest:",
            window.PaymentRequest
        );


        // -----------------------------
        // INITIAL URL TRIGGER
        // -----------------------------

        notify();

    })();
    """.trimIndent(),
            null
        )
    }


    fun hitApiForUploadEnachMandateDataResponse(request: EnachDateUploadReq, isMandate: String) {

        Log.d("UpiAutoTransReq", Gson().toJson(request))

        viewModel.UpdateEmandateDetails(request).observe(this) { resources ->
            when (resources.apiStatus) {
                ApiStatus.SUCCESS -> {
                    if (ConstantClass.dialog?.isShowing == true) {
                        ConstantClass.dialog!!.dismiss()
                    }
                    if (::dialog.isInitialized && dialog.isShowing) {
                        dialog.dismiss()
                    }
                    val response = resources.data?.body()

                    Log.d("UpiAutoTransRes", Gson().toJson(response))

                    if (response?.success!!.toLowerCase().equals("true", ignoreCase = true)|| response.code==200) {
                        if (isMandate == ConstantClass.isMandate) {
                            startActivity(Intent(this, AppScanInstallPage::class.java))
                            finish()
                        } else {
                            QRCodePage.isEnachCancelled = true
                            finish()
                        }
                    } else {
                        val msg = response?.message ?: "Upload failed"
                        Log.e("EmandateUploadError", "Response error: $msg")
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                ApiStatus.ERROR -> {
                    if (ConstantClass.dialog?.isShowing == true) {
                        ConstantClass.dialog!!.dismiss()
                    }
                    if (::dialog.isInitialized && dialog.isShowing) {
                        dialog.dismiss()
                    }
                    val errorMsg = resources.message ?: "Network or API error occurred"
                    Log.e("EmandateUploadError", "API Error: $errorMsg | Code: ${resources.data?.code()}")
                    Toast.makeText(this, "Upload failed: $errorMsg", Toast.LENGTH_SHORT).show()
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


        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: android.webkit.WebResourceError?) {
            super.onReceivedError(view, request, error)
            Log.e("AUTOUPI_WEBVIEW", "WebView Error: ${error?.description} (${error?.errorCode})")
        }


        override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: android.webkit.WebResourceResponse?) {
            super.onReceivedHttpError(view, request, errorResponse)
            Log.e("AUTOUPI_WEBVIEW", "WebView HTTP Error: ${errorResponse?.statusCode} - ${errorResponse?.reasonPhrase}")
        }


        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            val url = request.url.toString()
            Log.d("AUTOUPI", "URL Loading: $url")

            val uri = request.url
            val status = (uri.getQueryParameter("Status") ?: uri.getQueryParameter("status"))?.uppercase()

            if (status == "SUCCESS" || uri.path?.contains("/success", ignoreCase = true) == true ||
                status == "FAILED" || uri.path?.contains("/failure", ignoreCase = true) == true ||
                status == "CANCEL" || status == "CANCELLED" || uri.path?.contains("/cancel", ignoreCase = true) == true) {
                view.stopLoading()
                activity.handleUrlChange(url)
                return true
            }


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


    fun showingRejectioneMandatePopUp(emumrn: String,isEmandateVerified: String){
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
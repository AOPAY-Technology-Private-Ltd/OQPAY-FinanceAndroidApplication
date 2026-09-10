package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.SslErrorHandler
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature

import com.bosandroidapp.bosmobilefinance.ui.slideshow.ui.view.activity.retailer.cibilreportsfragment.BureauScore.Companion.userScore
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AadharVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.AccountType
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankIFSCCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BankName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BranchName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.BrandName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CheckOnlineOrOffline
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CibilResponse
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ClickOnCardLowCibilScore
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustAlternateMobileNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustAlternateMobileOTP
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustAlternateMobileVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustAreaSector
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustCityName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustCountry
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustCurrentAddress
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustFirstName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustFlatNo
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustLastName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustMiddleName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPinCode
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPrimaryMobileNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPrimaryMobileVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustPrimaryOTP
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustStateName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CusteMailID
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.CustomerCodeForEnach
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.EmiAmount
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumber1
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.ImeiNumber2
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoanEndDate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoanStartDate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanNumber
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanNumberVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.PanResponse
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefAadharTransactionIdNo
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefAddress
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefName
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefRelationShip
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RefmobileNo
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.RetailerCodeForEnach
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.eMandate
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.eMandatepending
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.iisAggrementVerified
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isMandate
import com.bosandroidapp.oqmobilefinance.data.enach.ENachStatusReq
import com.bosandroidapp.oqmobilefinance.data.enach.EnachDateUploadReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.repository.PanRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.PanViewModelFactory
import com.bosandroidapp.oqmobilefinance.databinding.ActivityRetailerEmandateVerifyPageBinding
import com.bosandroidapp.oqmobilefinance.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.AppScanInstallPage.Companion.LoanMode
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.CongratulationPage.Companion.loaneCode
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.QRCodePage.Companion.isEnachCancelled
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.PanViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.bosandroidapp.oqmobilefinance.data.repository.DikshifinsureRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.DikshifinsureOnlinePGModelFactory
import com.bosandroidapp.oqmobilefinance.data.upiautomandate.UpiAutoOrderStatusRequest
import com.bosandroidapp.oqmobilefinance.data.upiautomandate.UpiAutoTransactionRequest
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.DikshifinsureViewModel
import com.google.gson.Gson
import kotlin.math.roundToInt

class RetailerEMandateVerifyPage : BaseActivity() {
    lateinit var binding : ActivityRetailerEmandateVerifyPageBinding
    var isEmandateVerified : String= ""
    var isPannydropVerified : String= "Yes"
    var merchandId : String= ""
    var registrationId : String= ""
    private var isStatusCheckInProgress = false
    lateinit var viewModel: AuthenticationViewModel
    lateinit var panViewModel: PanViewModel
    lateinit var dikshifinsureViewModel: DikshifinsureViewModel
    lateinit var dialog: Dialog


    companion object{
        var webUrl: String? = ""
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRetailerEmandateVerifyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]
        panViewModel = ViewModelProvider(this, PanViewModelFactory(PanRepository(RetrofitClient.apiInterfacePAN)))[PanViewModel::class.java]
        dikshifinsureViewModel = ViewModelProvider(this, DikshifinsureOnlinePGModelFactory(DikshifinsureRepository(RetrofitClient.apiInterfaceOnlinePG)))[DikshifinsureViewModel::class.java]



        if(intent.hasExtra(ConstantClass.MarchentOrderID_UPIAUTOPAY)&& intent.hasExtra(ConstantClass.RegistrationID_UPIAUTOPAY))
        {
            merchandId = intent.getStringExtra(ConstantClass.MarchentOrderID_UPIAUTOPAY).toString()
            registrationId = intent.getStringExtra(ConstantClass.RegistrationID_UPIAUTOPAY).toString()
        }

        setDataInWebView()
        setonclicklistner()

    }



    fun setonclicklistner(){

        binding.back.setOnClickListener {
            onBackPressed()
        }

    }


    @SuppressLint("SetJavaScriptEnabled")
    fun setDataInWebView() {

        clearWebView(binding.eMandatewebview)

        binding.eMandatewebview.settings.javaScriptEnabled = true
        binding.eMandatewebview.settings.domStorageEnabled = true
        binding.eMandatewebview.settings.databaseEnabled = true
        binding.eMandatewebview.settings.loadsImagesAutomatically  = true
        binding.eMandatewebview.settings.javaScriptCanOpenWindowsAutomatically  = true
        binding.eMandatewebview.settings.setSupportMultipleWindows(true)
        binding.eMandatewebview.settings.allowFileAccess = true
        binding.eMandatewebview.settings.allowContentAccess = true
        binding.eMandatewebview.settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

        // Important for payment-related WebView flows
        if (WebViewFeature.isFeatureSupported(WebViewFeature.PAYMENT_REQUEST)) {

            WebSettingsCompat.setPaymentRequestEnabled(binding.eMandatewebview.settings, true)
            WebSettingsCompat.setHasEnrolledInstrumentEnabled(binding.eMandatewebview.settings, true)
        }

        Log.d("PHONEPE_WEBVIEW", "Payment Request supported = ${
                WebViewFeature.isFeatureSupported(
                    WebViewFeature.PAYMENT_REQUEST
                )
            }"
        )

        Log.d(
            "PHONEPE_WEBVIEW",
            "WebView version = ${
                WebView.getCurrentWebViewPackage()?.versionName
            }"
        )

        binding.eMandatewebview.settings.cacheMode = WebSettings.LOAD_DEFAULT

        binding.eMandatewebview.settings.userAgentString = WebSettings.getDefaultUserAgent(this)
        val cookieManager = CookieManager.getInstance()

        cookieManager.setAcceptCookie(true)
        cookieManager.setAcceptThirdPartyCookies(binding.eMandatewebview, true)


        binding.eMandatewebview.addJavascriptInterface(object {

            @JavascriptInterface
            fun onUrlChange(url: String) {
                Log.d("JS_URL", url)
                try {
                    val uri = Uri.parse(url)

                    if (!merchandId.isNullOrEmpty() && !registrationId.isNullOrEmpty()) {
                        doUpdateUpiAutoMandateStatus()
                    }
                    else {
                        // Get query parameter
                        val transactionId = uri.getQueryParameter("c")

                        Log.d("TRANSACTION_ID", transactionId ?: "null")

                        if (!transactionId.isNullOrEmpty()) {
                            // Call verify API here
                            doUpdateEMandateStatus(transactionId)
                        }
                    }

                }
                catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }, "Android")


        binding.eMandatewebview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.d("WEBVIEW_OVERRIDE", "URL: $url")
                if (url == null) return false

                if (url.startsWith("http://") || url.startsWith("https://")) {
                    return false // Let WebView load regular URLs
                }

                // Handle custom schemes (upi://, intent://, etc.)
                try {
                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    if (intent != null) {
                        view?.context?.startActivity(intent)
                        return true
                    }
                } catch (e: Exception) {
                    Log.e("WEBVIEW_ERROR", "Error parsing URI: $url", e)
                    try {
                        val fallbackIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        view?.context?.startActivity(fallbackIntent)
                        return true
                    } catch (anfe: ActivityNotFoundException) {
                        Log.e("WEBVIEW_ERROR", "No app found to handle URL: $url")
                        Toast.makeText(view?.context, "No app found to handle this action", Toast.LENGTH_SHORT).show()
                    }
                }
                return true
            }

            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                // IMPORTANT: For production, you should be more restrictive. 
                // Allowing mercury-t2 for UAT/Testing.
                if (error?.url?.contains("phonepe.com") == true) {
                    handler?.proceed()
                } else {
                    super.onReceivedSslError(view, handler, error)
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                Log.d("WEBVIEW", "Loaded URL: $url")

                injectJs(view)
            }
        }

        binding.eMandatewebview.webChromeClient = object : WebChromeClient() {
            override fun onCreateWindow(view: WebView?, isDialog: Boolean, isUserGesture: Boolean, resultMsg: android.os.Message?): Boolean {
                val newWebView = WebView(this@RetailerEMandateVerifyPage)
                newWebView.webViewClient = view?.webViewClient ?: WebViewClient()
                val transport = resultMsg?.obj as? WebView.WebViewTransport
                transport?.webView = newWebView
                resultMsg?.sendToTarget()
                return true
            }
        }

        binding.eMandatewebview.loadUrl(webUrl!!)


/*        val uri = Uri.parse(webUrl)

        val customTabsIntent = CustomTabsIntent.Builder()
            .build()

        customTabsIntent.launchUrl(this, uri)*/
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



    // hit api for  online emandate auto pay

    fun doUpdateUpiAutoMandateStatus() {
        if (isStatusCheckInProgress) return
        isStatusCheckInProgress = true
        (this@RetailerEMandateVerifyPage).runOnUiThread {
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
                    ConstantClass.dialog.dismiss()
                    val response = resources.data?.body()
                    Log.d("UpiAutoStatusRes", Gson().toJson(response))

                    if(response?.state!!.toLowerCase().equals("failed",ignoreCase = true)){
                        isEmandateVerified= "No"
                        showingRejectioneMandatePopUp(response.paymentDetails?.filterNotNull()?.firstOrNull()?.rail?.umn ?: "")
                        return@observe
                    }

                    if (response?.state?.toLowerCase().equals("completed", ignoreCase = true) == true) {

                        if (response!!.paymentDetails.isNullOrEmpty()) {
                            // First completed: mandate created, now trigger transaction
                            hitApiForUpiAutoMandateTransaction(registrationId)
                        } 
                        else {
                            // Second completed: transaction done
                            val umn = response.paymentDetails?.filterNotNull()?.firstOrNull()?.rail?.umn ?: ""

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
                    ConstantClass.dialog.dismiss()
                    Toast.makeText(this, resources.message ?: "Status Check Failed", Toast.LENGTH_SHORT).show()
                }
                ApiStatus.LOADING -> {
                    if (!ConstantClass.dialog.isShowing) {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }
                }
            }
        }

    }


    fun hitApiForUpiAutoMandateTransaction(registrationId: String) {
        val emiNumbers = "1" // Defaulting to 1 for mandate creation flow
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
                    ConstantClass.dialog.dismiss()
                    val response = resources.data?.body()
                    Log.d("UpiAutoTransRes", Gson().toJson(response))

                    if (!response?.intentUrl.isNullOrEmpty()) {
                        clearWebView(binding.eMandatewebview)
                        merchandId = response?.marchentOrderID ?: ""
                        binding.eMandatewebview.loadUrl(response?.intentUrl!!)
                    }
                    else {
                        Toast.makeText(this, response?.errorMessage ?: "Transaction trigger failed", Toast.LENGTH_SHORT).show()
                    }
                }
                ApiStatus.ERROR -> {
                    isStatusCheckInProgress = false
                    ConstantClass.dialog.dismiss()
                    Toast.makeText(this, resources.message ?: "Transaction Failed", Toast.LENGTH_SHORT).show()
                }
                ApiStatus.LOADING -> {
                    if (!ConstantClass.dialog.isShowing) {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }
                }
            }
        }
    }


    fun doUpdateEMandateStatus(eMandateID : String){

        (this@RetailerEMandateVerifyPage).runOnUiThread {

            var request = ENachStatusReq(
                registrationID = if (ConstantClass.CheckOnlineOrOffline == ConstantClass.online) {
                    ConstantClass.PAN_VERIFICATION_REGISTRATION_ID
                } else {
                    ConstantClass.PAN_VERIFICATION_REGISTRATION_ID_OFFLINE
                },
                eMandateID = eMandateID
            )

            hitApiForEMandateStatus(request)
        }

    }

    
    fun hitApiForEMandateStatus(request: ENachStatusReq) {
        Log.d("eManadateStatusReq", Gson().toJson(request))

        if(ConstantClass.CheckOnlineOrOffline.equals(ConstantClass.offline)){
            panViewModel.geteMandateSatusRequest(request).observe(this) { resources ->
                resources.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data.let { users ->
                                users!!.body().let { response ->
                                    Log.d("eMandateStatusRes", Gson().toJson(response))

                                    if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                        ConstantClass.dialog.dismiss()
                                    }

                                    var statusCode =  response!!.statusCode
                                    var eMandateStatus =""

                                    if(response.data!!.customer!=null){
                                        eMandateStatus = response.data.customer!!.accptd!!
                                    }

                                    if (response!!.statusCode.equals("NP000")&& eMandateStatus.equals(eMandate)) {
                                        isEmandateVerified= isMandate
                                        CheckOnlineOrOffline =""
                                        Toast.makeText(this, "ENach Mandate is Active", Toast.LENGTH_SHORT).show()
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
                                                emumrn = response.data.customer!!.umrn
                                            )

                                            hitApiForUploadEnachMandateDataResponse(request,isEmandateVerified)
                                        }

                                    }

                                    else {
                                        if(!eMandateStatus.equals(eMandatepending)){
                                            isEmandateVerified= "No"
                                            showingRejectioneMandatePopUp(response.data.customer!!.umrn!!)
                                        }
                                    }

                                }

                            }

                        }

                        ApiStatus.ERROR -> {
                            ConstantClass.dialog.dismiss()
                            // ✅ Print the full error details
                            Log.e("API_ERROR", "Status: ERROR")
                            Log.e("API_ERROR_CODE", resources.data?.code().toString())
                            Log.e("API_ERROR_MSG", resources.message ?: "Unknown Error")

                            Toast.makeText(this@RetailerEMandateVerifyPage, resources.message ?: "Server error occurred", Toast.LENGTH_LONG).show()

                            // Optional: Handle specific 500 error
                            if (resources.data?.code() == 500) {
                                Log.e("API_ERROR", "Internal Server Error from backend.")
                            }
                        }

                        ApiStatus.LOADING -> {

                        }

                    }

                }

            }
        }
        else {
            panViewModel.geteMandateOnlineSatusRequest(request).observe(this) { resources ->
                resources.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data.let { users ->
                                users!!.body().let { response ->
                                    Log.d("eMandateonlineStatusRes", Gson().toJson(response))

                                    if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                        ConstantClass.dialog.dismiss()
                                    }

                                    var statusCode =  response!!.statusCode
                                    var eMandateStatus =""

                                    if(response.data!!.customer!=null){
                                        eMandateStatus = response.data.customer!!.accptd!!
                                    }

                                    if (response!!.statusCode.equals("NP000")&& eMandateStatus.equals(eMandate)) {
                                        isEmandateVerified= isMandate
                                        CheckOnlineOrOffline =""
                                        Toast.makeText(this, "ENach Mandate is Active", Toast.LENGTH_SHORT).show()

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
                                                emumrn = response.data.customer!!.umrn
                                            )

                                            hitApiForUploadEnachMandateDataResponse(request,isEmandateVerified)

                                        }

                                    }

                                    else {
                                        if(!eMandateStatus.equals(eMandatepending)){
                                            isEmandateVerified= "No"
                                            showingRejectioneMandatePopUp(response.data.customer!!.umrn!!)
                                        }
                                    }

                                }

                            }

                        }

                        ApiStatus.ERROR -> {
                            ConstantClass.dialog.dismiss()
                            // ✅ Print the full error details
                            Log.e("API_ERROR", "Status: ERROR")
                            Log.e("API_ERROR_CODE", resources.data?.code().toString())
                            Log.e("API_ERROR_MSG", resources.message ?: "Unknown Error")

                            Toast.makeText(this@RetailerEMandateVerifyPage, resources.message ?: "Server error occurred", Toast.LENGTH_LONG).show()

                            // Optional: Handle specific 500 error
                            if (resources.data?.code() == 500) {
                                Log.e("API_ERROR", "Internal Server Error from backend.")
                            }
                        }

                        ApiStatus.LOADING -> {

                        }

                    }

                }

            }
        }

    }


    fun  hitApiForUploadEnachMandateDataResponse(request:EnachDateUploadReq,isMandate: String){

        Log.d("EmandateUploadreq", Gson().toJson(request))

        viewModel.UpdateEmandateDetails(request).observe(this){ resources ->
            resources.let {

                when(it.apiStatus){
                    ApiStatus.SUCCESS ->{
                        it.data.let { users ->
                            users!!.body().let { response ->
                                Log.d("EmandateUploadRes", Gson().toJson(response))
                                if(isMandate.equals(ConstantClass.isMandate)){
                                    // success response
                                   startActivity(Intent(this@RetailerEMandateVerifyPage, AppScanInstallPage::class.java))
                                }
                                else{
                                    isEnachCancelled = true
                                    finish()
                                }

                            }
                        }

                    }
                    ApiStatus.ERROR ->{
                        // ✅ Print the full error details
                        Log.e("API_ERROR", "Status: ERROR")
                        Log.e("API_ERROR_CODE", resources.data?.code().toString())
                        Log.e("API_ERROR_MSG", resources.message ?: "Unknown Error")

                        Toast.makeText(this, "Server error occurred (Code: ${resources.data?.code() ?: "Unknown"})", Toast.LENGTH_LONG).show()

                        // Optional: Handle specific 500 error
                        if (resources.data?.code() == 500) {
                            Log.e("API_ERROR", "Internal Server Error from backend.")
                        }
                    }



                    ApiStatus.LOADING -> {

                    }
                }
            }
        }

    }

    
    fun showingRejectioneMandatePopUp(emumrn: String){
        dialog = Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.enach_reject_alert)

        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
        }

        var Ok = dialog.findViewById<Button>(R.id.btnOk)

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

            dialog.dismiss()

        }

        dialog.setCanceledOnTouchOutside(false)

        dialog.show()

    }


    override fun onBackPressed() {
        isEmandateVerified= "No"
        showingRejectioneMandatePopUp("")
    }


}
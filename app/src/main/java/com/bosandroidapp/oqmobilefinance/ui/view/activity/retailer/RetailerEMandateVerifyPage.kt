package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer

import android.app.Dialog
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
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bos.payment.appName.network.RetrofitClient
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
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.AppScanInstallPage.Companion.LoanMode
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.CongratulationPage.Companion.loaneCode
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.QRCodePage.Companion.isEnachCancelled
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.PanViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson
import org.json.JSONObject

class RetailerEMandateVerifyPage : BaseActivity() {

    lateinit var binding : ActivityRetailerEmandateVerifyPageBinding
    var isEmandateVerified : String= ""
    var isPannydropVerified : String= "Yes"
    lateinit var viewModel: AuthenticationViewModel
    lateinit var panViewModel: PanViewModel

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
        panViewModel = ViewModelProvider(this,
            PanViewModelFactory(PanRepository(RetrofitClient.apiInterfacePAN))
        )[PanViewModel::class.java]

        setDataInWebView()

    }


    fun setDataInWebView() {

        binding.eMandatewebview.settings.javaScriptEnabled = true
        binding.eMandatewebview.settings.domStorageEnabled = true

        binding.eMandatewebview.addJavascriptInterface(object {

            @JavascriptInterface
            fun onUrlChange(url: String) {
                Log.d("JS_URL", url)
                try {
                    val uri = Uri.parse(url)

                    // Get query parameter
                    val transactionId = uri.getQueryParameter("c")

                    Log.d("TRANSACTION_ID", transactionId ?: "null")

                    if (!transactionId.isNullOrEmpty()) {
                        // Call verify API here
                        doUpdateEMandateStatus(transactionId)
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }

                
            }
        }, "Android")


        binding.eMandatewebview.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                Log.d("WEBVIEW", "Loaded URL: $url")


                 // ✅ Inject JS AFTER page load
                injectJs(view)
            }
        }

        clearWebView(binding.eMandatewebview)
        // ✅ Load URL AFTER setup
        binding.eMandatewebview.loadUrl(webUrl!!)
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

            notify(); // initial trigger
        })();
    """.trimIndent(), null)
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

        if(LoanMode.equals(ConstantClass.offline)){
            panViewModel.geteMandateSatusRequest(request).observe(this) { resources ->
                resources.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {
                            it.data.let { users ->
                                if(users!!.isSuccessful){
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
                                                    emIfscCode =BankIFSCCode
                                                )

                                                hitApiForUploadEnachMandateDataResponse(request,isEmandateVerified)
                                            }

                                        }

                                        else {
                                            if(!eMandateStatus.equals(eMandatepending)){
                                                isEmandateVerified= "No"
                                                showingRejectioneMandatePopUp()
                                            }
                                        }

                                    }
                                }
                                else{
                                        var error = resources.data.toString()
                                        Toast.makeText(this@RetailerEMandateVerifyPage,error, Toast.LENGTH_SHORT).show()
                                }
                            }
                            handleHttpError(it.data!!.code(), it.data!!.errorBody()?.string(), it.data.message())

                        }

                        ApiStatus.ERROR -> {
                            ConstantClass.dialog.dismiss()
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
        else {
            panViewModel.geteMandateOnlineSatusRequest(request).observe(this) { resources ->
                resources.let {
                    when (it.apiStatus) {
                        ApiStatus.SUCCESS -> {

                            it.data.let { users ->
                                if(users!!.isSuccessful){
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
                                                    emIfscCode =BankIFSCCode
                                                )

                                                hitApiForUploadEnachMandateDataResponse(request,isEmandateVerified)
                                            }

                                        }

                                        else {
                                            if(!eMandateStatus.equals(eMandatepending)){
                                                isEmandateVerified= "No"
                                                showingRejectioneMandatePopUp()
                                            }
                                        }

                                    }
                                }
                                else{
                                    var error = resources.data.toString()
                                    Toast.makeText(this@RetailerEMandateVerifyPage,error, Toast.LENGTH_SHORT).show()
                                }


                            }
                            handleHttpError(it.data!!.code(), it.data!!.errorBody()?.string(), it.data.message())
                        }

                        ApiStatus.ERROR -> {
                            ConstantClass.dialog.dismiss()
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
    }


    fun  hitApiForUploadEnachMandateDataResponse(request:EnachDateUploadReq,isMandate: String){

        Log.d("EmandateUploadreq", Gson().toJson(request))

        viewModel.UpdateEmandateDetails(request).observe(this){
                resources ->
            resources.let {

                when(it.apiStatus){
                    ApiStatus.SUCCESS ->{
                        it.data.let { users ->
                            if(users!!.isSuccessful){
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
                            } else{
                                var error = resources.data.toString()
                                Toast.makeText(this@RetailerEMandateVerifyPage,error, Toast.LENGTH_SHORT).show()
                            }

                        }

                        handleHttpError(it.data!!.code(), it.data!!.errorBody()?.string(), it.data.message())


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


    fun showingRejectioneMandatePopUp(){
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
                    emIfscCode =BankIFSCCode
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
        showingRejectioneMandatePopUp()
    }

    private fun handleHttpError(responseCode: Int, errorBody: String?, responseMessage: String?) {
        if (ConstantClass.dialog?.isShowing == true) {
            ConstantClass.dialog.dismiss()
        }


        val serverMessage = getServerErrorMessage(errorBody)
        val finalMessage = when {
            !serverMessage.isNullOrBlank() -> serverMessage
            responseCode == 400 -> "Bad request. Please check your data (400)."
            responseCode == 401 -> "Session expired. Please login again (401)."
            responseCode == 404 -> "Service not found. Please try again later (404)."
            responseCode == 500 -> "Server error. Please try again later (500)."
            !responseMessage.isNullOrBlank() -> responseMessage
            else -> "Something went wrong (Code: $responseCode)."
        }

        showErrorToast(finalMessage)
        Log.e("API_HTTP_ERROR", "Code: $responseCode | Body: $errorBody")
    }

    private fun showErrorToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun getServerErrorMessage(errorBody: String?): String? {
        if (errorBody.isNullOrBlank()) return null
        return try {
            val jsonObject = JSONObject(errorBody)
            when {
                jsonObject.has("message") -> jsonObject.getString("message")
                jsonObject.has("Message") -> jsonObject.getString("Message")
                jsonObject.has("statusMessage") -> jsonObject.getString("statusMessage")
                else -> null
            }
        } catch (e: Exception) {
            null
        }
    }

}
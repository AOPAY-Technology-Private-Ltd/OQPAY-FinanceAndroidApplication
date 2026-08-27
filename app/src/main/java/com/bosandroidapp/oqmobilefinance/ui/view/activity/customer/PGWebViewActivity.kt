package com.bosandroidapp.oqmobilefinance.ui.view.activity.customer

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.webkit.CookieManager
import android.webkit.WebStorage
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bos.payment.appName.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Customer
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.getCurrentUtcTimestamp
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isPgClosing
import com.bosandroidapp.oqmobilefinance.data.model.SessionOutReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.CustomerLoanEmiReceiveReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.databinding.ActivityPgwebViewBinding
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.slideshow.activity.DashBoard
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.EmiLoanDetailPage.Companion.customerCode
import com.bosandroidapp.oqmobilefinance.ui.view.activity.customer.EmiLoanDetailPage.EmiData
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson

class PGWebViewActivity : BaseActivity() {
    lateinit var binding : ActivityPgwebViewBinding
    lateinit var dialog: Dialog
    lateinit var preference : SharedPreference
    lateinit var viewModel: AuthenticationViewModel


    companion object{
        var emiList = mutableListOf<EmiData>()
        var EMIamountPG : String =""
        var LoanCodePG : String = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        super.onCreate(savedInstanceState)

        binding = ActivityPgwebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            view.setPadding(
                systemBarsInsets.left,
                systemBarsInsets.top,    // Top padding
                systemBarsInsets.right,
                systemBarsInsets.bottom
            )

            WindowInsetsCompat.CONSUMED
        }

        preference = SharedPreference(this)
        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]

        clearWebViewData(binding.pgwebview)

        launchPGOnWebView()
    }

    override fun onResume() {
        super.onResume()
        hitApiForLogin(preference.getStringValue(ConstantClass.CustomerCode,""))
    }

    fun launchPGOnWebView(){
        val pgUrl = intent.getStringExtra("pgurl")
        val finalHtml = """
    <html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    </head>
    <body>
        $pgUrl
    </body>
    </html>
""".trimIndent()

        binding.pgwebview.settings.javaScriptEnabled = true
        binding.pgwebview.settings.domStorageEnabled = true
        binding.pgwebview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {

                Log.d("URL", url.orEmpty())

                if (url.isNullOrEmpty()) {
                    return true
                }

                return if (url.startsWith("http://") || url.startsWith("https://")) {

                    // Handle PayU response URLs
                    when {
                        // Payment Success
                        url.contains("status=success", ignoreCase = true) || url.contains("/success", ignoreCase = true) -> {
                            val uri = Uri.parse(url)
                            val utrNumber = uri.getQueryParameter("utrNumber")
                            Log.d("UTR", utrNumber ?: "")
                            showingSuccessPopUp(utrNumber!!)
                            return true
                        }
                        // Payment Failed
                        url.contains("status=failure", ignoreCase = true) || url.contains("/failure", ignoreCase = true) -> {

                            Log.d("PAYU", "Payment Failed : $url")
                            showingRejectionePGPopUp()
                            return true
                        }
                        // Payment Cancelled
                        url.contains("/cancel", ignoreCase = true) || url.contains("status=cancel", ignoreCase = true) || url.contains("action=userCancel", ignoreCase = true) -> {
                            Log.d("PAYU", "Payment Cancelled : $url")
                            showingRejectionePGPopUp()
                            return true
                        }
                    }

                    false // Let WebView load the URL itself

                } else
                {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))

                        val activities = packageManager.queryIntentActivities(intent, 0)
                        Log.d("UPI", "Activities Count: ${activities.size}")

                        if (activities.isNotEmpty()) {
                            startActivity(Intent.createChooser(intent, "Pay with"))
                        }
                        else {
                            Toast.makeText(this@PGWebViewActivity, "No UPI app found", Toast.LENGTH_SHORT).show()
                        }

                    }
                    catch (e: Exception) {
                        Log.e("UPI", "Error launching app", e)
                        Toast.makeText(this@PGWebViewActivity, "No app found to handle this action", Toast.LENGTH_SHORT).show()
                    }

                    true
                }
            }
        }

        binding.pgwebview.loadDataWithBaseURL("https://secure.payu.in/", finalHtml, "text/html", "UTF-8", null)

        binding.pgwebview.loadUrl(pgUrl!!)
    }



    fun HitApiForPayEmiAmount(emicount:Int,loopcount :Int,emiamount : String,fine:String?/*,imageFile:File*/,loanCode:String,utrNumber: String){

        var createdBy = preference.getStringValue(ConstantClass.CustomerCode, "")
        var customercode =  preference.getStringValue(ConstantClass.CustomerCode, "")
        var retailercode =  preference.getStringValue(ConstantClass.RetailerCode, "")

        val request = CustomerLoanEmiReceiveReq(
            mode = "UPDATE",
            loanCode = loanCode,
            paymentDate = getCurrentUtcTimestamp(),
            paymentMode = "Online",
            utrNumber = utrNumber,
            remarks = "Payment",
            createdBy = createdBy,
            receiptNo = "",
            customerCode =customercode,
            retailerCode = retailercode,
            bankName = "PG",
            receiptImagePath = ""/*,
            imageFile = imageFile*/
        )

        Log.d("loanEmiReceiveReq", Gson().toJson(request))

        viewModel.getCustomerLoanDetailsReq(request).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let {
                                    response ->
                                Log.d("loanEmiReceiveResp", response.toString())
                                if(loopcount==emicount){
                                    if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                                        ConstantClass.dialog.dismiss()
                                    }
                                     emiList .clear()
                                     EMIamountPG  =""
                                     LoanCodePG  = ""
                                    Toast.makeText(this@PGWebViewActivity,response.message,Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@PGWebViewActivity, DashBoard::class.java)
                                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)
                                }
                            }
                        }

                    }

                    ApiStatus.ERROR -> {
                        if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                            ConstantClass.dialog.dismiss()
                        }

                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }
            }
        }

    }


    fun clearWebViewData(webView: WebView) {
        webView.clearCache(true)
        webView.clearHistory()
        webView.clearFormData()

        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
        WebStorage.getInstance().deleteAllData()
    }


    fun showingRejectionePGPopUp(){
        dialog = Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.payment_reject_alert)

        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
        }

        var Ok = dialog.findViewById<AppCompatButton>(R.id.btnOk)

        Ok.setOnClickListener {
            isPgClosing = true
            dialog.dismiss()
            closePg()
            window.decorView.post {
                finish()
            }
        }

        dialog.setCanceledOnTouchOutside(false)

        dialog.show()

    }

    private fun closePg() {
        binding.pgwebview.stopLoading()
        binding.pgwebview.loadUrl("about:blank")
        binding.pgwebview.clearHistory()
        binding.pgwebview.removeAllViews()
        binding.pgwebview.destroy()
    }


    fun showingSuccessPopUp(utrNumber: String){
        dialog = Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.payment_success_alert)

        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
        }

        var Ok = dialog.findViewById<AppCompatButton>(R.id.btnOk)
        var textmessage = dialog.findViewById<TextView>(R.id.loancodewithamount)

        val message = "Your EMI payment of ${EMIamountPG} for Loan Code ${LoanCodePG} has been successfully processed."
        textmessage.text = message

        Ok.setOnClickListener {
            if(emiList.size>0){
                dialog.dismiss()
                for(i in 0 until emiList.size){
                    HitApiForPayEmiAmount(emiList[i].selectedNoofEmi, emiList[i].emiNo, emiList[i].emiAmount,emiList[i].lateFine,emiList[i].loancode,utrNumber )
                }
            }
        }

        dialog.setCanceledOnTouchOutside(false)

        dialog.show()

    }



    override fun onBackPressed() {
        showingRejectionePGPopUp()
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d("PG", "onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.d("PG", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("PG", "onStop")
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
                                ConstantClass.checkActiveStatusAndLogout(this@PGWebViewActivity, response.status, preference)
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
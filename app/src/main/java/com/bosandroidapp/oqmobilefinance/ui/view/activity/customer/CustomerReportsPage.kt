package com.bosandroidapp.oqmobilefinance.ui.view.activity.customer

import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.CancellationSignal
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.os.ParcelFileDescriptor
import android.print.PageRange
import android.print.PrintAttributes
import android.print.PrintDocumentAdapter
import android.print.PrintDocumentAdapter.*
import android.print.PrintDocumentInfo
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider

import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.databinding.ActivityCustomerReportsPageBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.formatDateToFullMonth
import com.bosandroidapp.oqmobilefinance.data.model.SessionOutReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.GetReportsReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.ReportsDataItem
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.ui.slideshow.adapter.CustomerReportListAdapter
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.IMEIDetailsPage
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class CustomerReportsPage : BaseActivity() ,CustomerReportListAdapter.onClickListner{
    lateinit var binding: ActivityCustomerReportsPageBinding
    lateinit var preference: SharedPreference
    lateinit var viewModel: AuthenticationViewModel
    var ReportDataList: MutableList<com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.ReportsDataItem> = mutableListOf()
    lateinit var reportAdapter: CustomerReportListAdapter
    private lateinit var htmlTemplate: String
    var webViewContentbitmap: Bitmap ?= null

    private lateinit var logoBase64: String
    private lateinit var watermarkBase64: String
    private lateinit var stampBase64: String
    private lateinit var signatureBase64: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerReportsPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBarsInsets.left,
                0,
                systemBarsInsets.right,
                systemBarsInsets.bottom
            )
            WindowInsetsCompat.CONSUMED
        }

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]
        preference = SharedPreference(this)

        htmlTemplate = assets.open("noc.html")
            .bufferedReader()
            .use { it.readText() }

      /*  logoBase64 = drawableToBase64(this, R.drawable.aopaynewlogo)

        watermarkBase64 = drawableToBase64(this, R.drawable.aopay_logo)

        stampBase64 = drawableToBase64(this, R.drawable.stampicon)

        signatureBase64 = drawableToBase64(this, R.drawable.signuature)*/

        setview()
        setclicklistner()

    }


    override fun onResume() {
        super.onResume()
        hitApiForGetReports(binding.reporttype.selectedItem.toString())
        hitApiForLogin(preference.getStringValue(ConstantClass.CustomerCode,""))
    }



    fun setview() {
        val adapter = ArrayAdapter.createFromResource(this, R.array.customerreporttype, R.layout.mobilenamelayout)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.reporttype.adapter = adapter

        var isSpinnerFirstCall = true // declare outside the listener

        binding.reporttype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (isSpinnerFirstCall) {
                    isSpinnerFirstCall = false
                    return // skip the first auto-call
                }
                val selectedItem = parent.getItemAtPosition(position).toString()

                if (selectedItem.equals("Approved")) {
                    hitApiForGetReports("Disbursed")
                } else {
                    hitApiForGetReports(selectedItem)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // nothing
            }
        }


    }



    fun setclicklistner() {

        binding.back.setOnClickListener {
            finish()
        }

    }



    fun hitApiForGetReports(reporttype: String) {
        var customerCode = preference.getStringValue(ConstantClass.CustomerCode, "")
        var recordStatus = reporttype

        var reportreq = GetReportsReq(
            retailercode = "",
            recordStatus = recordStatus,
            customercode = customerCode,
            fromDate = null,
            toDate = null
        )
        Log.d("ReportReq", Gson().toJson(reportreq))
        viewModel.getReportsReq(reportreq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                ConstantClass.dialog.dismiss()
                                ReportDataList = response.data!!.toMutableList()
                                Log.d("ReportResponse", Gson().toJson(ReportDataList))
                                if (ReportDataList.size > 0) {
                                    binding.showreports.visibility = View.VISIBLE
                                    binding.notfoundimage.visibility = View.GONE
                                    reportAdapter = CustomerReportListAdapter(ReportDataList, this, ConstantClass.Customer,this)
                                    binding.showreports.adapter = reportAdapter
                                    reportAdapter.notifyDataSetChanged()

                                } else {
                                    binding.showreports.visibility = View.GONE
                                    binding.notfoundimage.visibility = View.VISIBLE
                                }
                            }
                        }
                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(this@CustomerReportsPage, resources.message ?: "Error occurred", Toast.LENGTH_SHORT).show()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }

            }

        }


    }


    fun drawableToBase64(context: Context, drawableId: Int): String {

        val bitmap = BitmapFactory.decodeResource(
            context.resources,
            drawableId
        )

        val outputStream = ByteArrayOutputStream()

        bitmap.compress(
            Bitmap.CompressFormat.PNG,
            100,
            outputStream
        )

        val base64 = Base64.encodeToString(
            outputStream.toByteArray(),
            Base64.NO_WRAP
        )

        return "data:image/png;base64,$base64"
    }

    override fun onClick(item: ReportsDataItem) {
        Log.d("Date Time", formatDateToFullMonth(item.endDate))
        val ref = "${ item.loanCode }${item.custerMob.takeLast(4)}${item.customerCode.takeLast(2)}"
        var date=""
        if(!item.endDate.isNullOrEmpty()){
            date = formatDateToFullMonth(item.endDate)
        }else{
            date = item.endDate
        }

        val finalHtml = htmlTemplate
            /*.replace("{{LOGO_IMAGE}}", logoBase64)
            .replace("{{WATERMARK_IMAGE}}", watermarkBase64)
            .replace("{{SIGNATURE_IMAGE}}", signatureBase64)
            .replace("{{STAMP_IMAGE}}", stampBase64)*/
            .replace("{{REF_NO}}", ref)
            .replace("{{DATE}}", date)
            .replace("{{CUSTOMER_NAME}}", item.customerName)
            .replace("{{MOBILE_NUMBER}}", item.custerMob)
            .replace("{{LOAN_NUMBER}}", item.loanCode)
            .replace("{{PRODUCT_NAME}}", item.productDetails)
            .replace("{{LOAN_CLOSURE_DATE}}", date)
            .replace("{{AUTH_NAME}}", "Arjun")
            .replace("{{DESIGNATION}}", "Director")

        showNocDialog(finalHtml,item.loanCode)


    }

    fun  showNocDialog(finalHtml: String,loanCode: String){
        val dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setContentView(R.layout.dialog_noc)

        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

       dialog.setCanceledOnTouchOutside(false)

        val webView = dialog.findViewById<WebView>(R.id.webView)
        val close = dialog.findViewById<ImageView>(R.id.ivClose)
        val download = dialog.findViewById<ImageView>(R.id.download)

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            builtInZoomControls = true
            displayZoomControls = false
            textZoom = 100
        }
        webView.setInitialScale(100)
        webView.settings.javaScriptEnabled = true

        webView.loadDataWithBaseURL(
            null,
            finalHtml,
            "text/html",
            "UTF-8",
            null
        )

        var loadWebPage = false

        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loadWebPage=true
                webView.postDelayed({

                    val fullHeight = (webView.contentHeight * webView.scale).toInt()

                    Log.d("WEBVIEW", "Height = $fullHeight")

                    webViewContentbitmap = captureWebView(webView)

                }, 1000)
            }
        }

        download.setOnClickListener {
            if(loadWebPage){
                webViewContentbitmap = captureWebView(webView)
            }
            if(webViewContentbitmap!=null)
            saveBitmapAsPdf(this, webViewContentbitmap!!,loanCode )
        }

        close.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    fun captureWebView(webView: WebView): Bitmap {

        val width = webView.width

        val contentHeight = (webView.contentHeight * webView.scale).toInt()

        val bitmap = Bitmap.createBitmap(
            width,
            contentHeight,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap)

        webView.draw(canvas)

        return bitmap
    }

    private fun saveBitmapAsPdf(context: Context, bitmap: Bitmap,fileName: String): File? {
        // Create a new PdfDocument
        val document = PdfDocument()

        // Create a page info with bitmap dimensions
        val pageInfo = PageInfo.Builder(bitmap.width, bitmap.height, 1).create()

        // Start a page
        val page = document.startPage(pageInfo)

        // Draw the bitmap on the page's canvas
        page.canvas.drawBitmap(bitmap, 0f, 0f, null)
        document.finishPage(page)

        // Save to external files dir
        val pdfDir = File(context.getExternalFilesDir(null), "pdfs")
        if (!pdfDir.exists()) {
            pdfDir.mkdirs()
        }

        val downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        if (!downloadsDir.exists()) downloadsDir.mkdirs()

        val pdfFile = File(downloadsDir, "$fileName.pdf")

        //val pdfFile = File(pdfDir, "${Calendar.getInstance().timeInMillis}.pdf")

        try {
            FileOutputStream(pdfFile).use { fos ->
                document.writeTo(fos)
            }
            val uri = Uri.fromFile(pdfFile)
            val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri)
            context.sendBroadcast(scanIntent)
            Toast.makeText(context, "PDF saved to Downloads", Toast.LENGTH_SHORT).show()

        } catch (e: IOException) {
            e.printStackTrace()
            return null
        }

        document.close()

        return pdfFile
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
                                ConstantClass.checkActiveStatusAndLogout(this@CustomerReportsPage, response.status, preference)
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
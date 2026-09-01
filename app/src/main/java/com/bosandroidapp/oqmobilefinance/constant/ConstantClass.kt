package com.bosandroidapp.oqmobilefinance.constant

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.LocationManager
import android.media.MediaScannerConnection
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.util.Patterns
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity.LOCATION_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.core.widget.NestedScrollView
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import  com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.internetchecker.NetworkMonitor
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.view.activity.ChooseYourRolePage
import com.bosandroidapp.oqmobilefinance.workmanager.LocationUploadWorker
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import org.json.JSONArray
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.NetworkInterface
import java.net.URL
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Collections
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit

object ConstantClass {

         // Production

        /* const val BASE_URL = "https://api.oqpay.in/"
         const val BASE_URL_IMAGE = "https://api.oqpay.in"


         // production merchant id online
         const val PAN_VERIFICATION_REGISTRATION_ID = "AOP-5039"
         const val PENNYDROP_REGISTRATION_ID = "AOP-5039"


         // production merchant id offline
         const val PAN_VERIFICATION_REGISTRATION_ID_OFFLINE = "AOP-5050"
         const val PENNYDROP_REGISTRATION_ID_OFFLINE = "AOP-5050"*/


        // for enach option using registration id always use for bot uat and production ......................



        // production merchant id online
        const val Enach_Option_Online_REGISTRATION_ID = "AOP-5039"

       // production merchant id offline
        const val Enach_Option_Offline_REGISTRATION_ID = "AOP-5050"


       // UAT
       const val BASE_URL = "https://api.oqpay.co.in/"
       const val BASE_URL_IMAGE = "https://api.oqpay.co.in"


     // UAT merchant id online
      const val PAN_VERIFICATION_REGISTRATION_ID = "AOP-554"
      const val PENNYDROP_REGISTRATION_ID = "AOP-554"


      //  UAT merchant id offline
      const val PAN_VERIFICATION_REGISTRATION_ID_OFFLINE = "AOP-554"
      const val PENNYDROP_REGISTRATION_ID_OFFLINE = "AOP-554"


     const val SMS_BASE_URL = "http://web.adcruxmedia.in/"
     const val PAN_BASE_URL = "https://api.aopay.in/"
     const val ONLINE_PG = "https://api.dikshifinsure.com/"

     const val SMS_API_KEY = "KBSxc26XqjoiR7SA"
     const val SMS_SENDER_ID = "BOSCNT"
     const val SMS_TEMPLATE_ID = "1207175396979758678"
     const val FRP_MAIL_ID = "116164541526712076874" // info@aopay.in
     const val CustomerCode = "customerCode"
     const val RetailerCode = "retailerCode"
     const val ForgotPasswordType = "Retailer forgot password"
     const val OTPTYPE = "VerifyUser"
     const val EMILIST = "EmiList"

     const val FCMTOKEN = "fcmtoken"
     const val UPIAUTOPAY = "UPI Autopay"

     const val DEVICEID = "deviceid"
     const val CHECKACCESSIBILITY = "checkAccessibility"
     const val LoanSuccessStatus = "success"
     const val DeviceType = "Android"
     const val ClientCode = "clientcode"
     const val DefaulterEmiDebitAutoApproved ="admin"
     const val DefaulterEmiDebitPending ="retailer"
     var LoanStatus ="Pending"
     const val SessionOutStatus = "inactive"
     const val SessionOutStatusRejected = "rejected"

     const val LoginMobileorMailid = "loginMobileorMail"

     const val Loginpassword = "loginPassword"
     var gpsSettingsOpened = false
     var internetSettingsOpened = false
     var CheckCompleteEmiStatus = false
     const val SETTINGS_PKG = "com.android.settings"
     const val CustomerMobileNumber = "mobileNumber"
     const val CustomerEmailID = "emailID"
     const val ServiceCharge = "serviceCharge"
     const val ServiceType = "serviceType"
     const val FirstName = "firstName"
     const val LastName = "lastName"
     const val LoggedIn = "logged"
     const val Retailer = "Retailer"
     const val Customer = "Customer"
     const val LoginType = "Logintype"
     const val online = "Online"
     const val offline = "Offline"
     const val editprofile = "Edit Profile"
     const val cancel = "Cancel"
     const val paymentMode = "Select Payment Mode"
     const val accountType = "Select Account Type"
     const val CibilReports = "Low Cibil Customer"
     const val CardType = "Low Cibil"
     const val CustomerAccessKey = "customeraccesskey"
     const val Exit = "Exit"
     const val Due = "TodayDue"
     const val Overdue = "OverDue"
     const val TODAYDUE = "Today Dues"

     const val OVERDUE = "Over Dues"
     const val CUREENTLAT = "CurrentLat"
     const val CUREENTLONGG = "CurrentLongg"

     const val SelectBank = "Select Bank"
     const val ModeOfPayment = "IMPS"

     const val SUCCESS = "SUCCESS"
     const val GENERATEKEY = "GenerateKey"
     const val GENERATE_KEY_COUNT = "generate_key_count"
     const val LAST_GENERATE_TIME = "last_generate_time"
     const val LOAN_REJECT = "LOAN_REJECTED"
     const val DISBURSMENT_REJECT = "DISBURSMENT_REJECTED"
     const val UNLOCK = "UNLOCK"

     var PanFirstName : String= ""
     var PanMiddleName : String= ""
     var PanLastName : String= ""
     var PanMobileNumber : String= ""
     var PanEmailId : String= ""
     var PanPinCode : String= ""
     var PanState : String= ""
     var PanCity : String= ""
     var PanCountry : String= ""
     var PanBuilding : String= ""
     var PanAddress : String= ""
     var PanDOB : String= ""
     var AadhaarDOB : String= ""
    var AadhaarName : String= ""
    var AadharHouse : String= ""
    var AadharStreet : String= ""
    var AadharLoc : String= ""
    var Aadhardist : String= ""
    var AadharPin : String= ""
    var AadharState : String= ""
    var AadharCountry : String= ""
    var AadharImage : String= ""
    var AadharDOB : String= ""
    var PanResponse : String = ""
    var AadhaarResponse : String = ""
    var CibilResponse : String = ""
    var CreatedByCustomerShortCut : String = ""
    var UPIMandate : String = ""
     var CustomerActiveStatus : String = "" // for shortcut loan process
     var ENTEREDCUSTOMERDOB : String = ""
     var WalletBalance : String = ""
     var HoldAmount : String = ""
     var MaxHoldingAmount : String = ""
     var MinHoldingAmount : String = ""
     var LoanSecurityHoldAmount : String = ""

     lateinit var  dialog : Dialog

     var PanNumber : String = ""
     var CheckOnlineOrOffline : String = "Offline"
     var PanNumberVerified : String = "yes"
     var BrandName : String = ""
     var ModelName : String = ""
     var ModelVarient : String = ""
     var ModelColor : String = ""
     var SellingPrice : String = ""
     var MRPPrice : String = ""
     var DownPayment : String = ""
     var Tenure : String = ""
     var LoanAmount : Double = 0.0
     var EmiAmount : String = ""
     var InterestRate : String = ""
     var ProcessingFees : String = ""
     var InterestAmt : String = ""
     var ToBePaidAmount : String = ""

     var CountryName : String ? = ""

     var ClickOnCardDashboard : String ? = ""
     var ClickOnCardLowCibilScore : String ? = ""

     var AadharNumber : String = ""
     var ReferenceAadharNumber : String = ""
     var ReferenceAddress : String = ""
    var ReferenceAadhaarName : String= ""
    var ReferenceAadharHouse : String= ""
    var ReferenceAadharStreet : String= ""
    var ReferenceAadharLoc : String= ""
    var ReferenceAadhardist : String= ""
    var ReferenceAadharPin : String= ""
    var ReferenceAadharState : String= ""
    var ReferenceAadharCountry : String= ""
    var ReferenceAadharImage : String= ""
    var ReferenceAadharVerified : String= ""

     var AadharFrontImageUri: Uri? = null
     var AadharBackImageUri: Uri? = null
     var PanFrontImageUri: Uri? = null
     var AadharVerified : String = ""
     var CustPhotoPath : Uri? = null
     var CustFirstName : String = ""
     var CustMiddleName : String = ""
     var CustLastName : String = ""

     var isLockTaskStarted = false
     var CustPrimaryMobileNumber : String = ""
     var CustPrimaryOTP : String = ""
     var CustPrimaryMobileVerified : String = "no"
     var CustAlternateMobileNumber : String = ""
     var CustAlternateMobileOTP : String = ""
     var CustAlternateMobileVerified : String = "no"
     var isAggrementVerified : String = ""
     var IsRetailerAggrementVerified : String = ""
     var CusteMailID : String = ""
     var CustFlatNo : String = ""
     var CustAreaSector : String = ""
     var CustCurrentAddress : String = ""
     var CustPinCode : String = ""
     var CustCountry : String ? = "India"
     var CustStateName : String = ""
     var CustCityName : String = ""
     var CustCode : String = ""
     var ImeiNumber1 : String = ""
     var ImeiNumber1SealPhotoPath : Uri? = null
     var ImeiNumber2 : String = ""
     var ImeiNumber2SealPhotoPath : Uri? = null
     var Invoive_Path : Uri? = null
     var ImeiNumberPhotoPath : Uri? = null
     var AccountNumber : String = ""
     var BankIFSCCode : String = ""
     var BankName : String = ""
     var AccountType : String = ""
     var CustomerCodeForEnach : String = ""
     var DefaultEmidebit : String = "" // loan related

     var LoanRID : Int = 0
     var RetailerCodeForEnach : String = ""
     var LoanCodeForEnach : String = ""
     var AccountHolderName = ""
     var BranchAddress = ""

     var isPannydropVerified : String= ""

     var isEmandateVerified : String= ""
     var isAccessKeyVerified : String= ""
     var LoanStartDate = ""
     var LoanEndDate = ""
     var BankID : Int =0
     var BranchName : String = ""
     var RefName : String = ""
     var RefRelationShip : String = ""
     var RefmobileNo : String = ""
     var RefAddress : String = ""
     var DebitOrCreditCard : String = ""
     var UpiMandate : String = "yes"
     var CreatedBy : String = ""
     var loginType : String = ""
     var iisAggrementVerified : Boolean = false
     var isCibilAggrementVerified : Boolean = false
     var AadharTransactionIdNo : String = ""
     var RefAadharTransactionIdNo : String = ""
     var clickMakePaymentPage: Boolean = true

    // customer device configuration details
     var deviceManufacturer : String = ""
     var deviceModel : String = ""
     var DeviceBrand : String = ""
     var DeviceOSVersion : String = ""
     var AppVersion : String = ""
     var IMEI : String = ""
     var DeviceName : String = ""

    var latitude : Double = 0.0
    var longitude : Double = 0.0

    var eMandate = "accepted"
    var eMandatepending = "pending"
    var isMandate = "Yes"

    var IsGSTVerified : String ="" // default active
    var IsPanVerified : String =""
    var IsAadhaarVerified : String =""

    // already customer exist
    var AlreadyCustomerCodeHaveEligiblity : String = ""
    var AlreadyCustomerImage : String = ""
    var AlreadyCustomerFirstName : String = ""
    var AlreadyCustomerMiddleName : String = ""
    var AlreadyCustomerLastName : String = ""
    var AlreadyCustomerPrimaryMobileNumber : String = ""
    var AlreadyCustomerAlternateMobileNumber: String = ""
    var AlreadyCustomerAlternateEmailID: String = ""
    var AlreadyCustomerFlatNo : String = ""
    var AlreadyCustomerAearSector : String = ""
    var AlreadyCustomerCurrentAddress : String = ""
    var AlreadyCustomerPinCode : String = ""
    var AlreadyCustomerCountry : String = ""
    var AlreadyCustomerStateName : String = ""
    var AlreadyCustomerCityName : String = ""
    var AlreadyCustomerCustPhotoPath : String = ""
    var AlreadyCustomerAccountNumber : String = ""
    var AlreadyCustomerBankIFSCCode : String = ""
    var AlreadyCustomerBankName : String = ""
    var AlreadyCustomerAccountType : String = ""
    var AlreadyCustomerBranchName : String = ""
    var AlreadyCustomerRefName : String = ""
    var AlreadyCustomerRefRelationShip : String = ""
    var AlreadyCustomerRefmobileNo : String = ""
    var AlreadyCustomerRefAddress : String = ""

    var isPgClosing = false

    private var noInternetDialog: AlertDialog? = null

     fun showNoInternetDialog(context: Context) {
        if (noInternetDialog?.isShowing == true) return

        noInternetDialog = AlertDialog.Builder(context)
            .setTitle("No Internet")
            .setMessage("Please check your Wi-Fi or mobile data connection.")
            .setCancelable(false)
            .setPositiveButton("Open Settings") { _, _ ->
                if (NetworkMonitor(context).isConnected()) noInternetDialog?.dismiss() else {
                    val intent = Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                }

            }
            .create()

        noInternetDialog?.show()
    }



    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
        else {
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
        }

    }


    fun OpenPopUpForVeryfyOTP(context: Context){
        dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.loader)

        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            statusBarColor = Color.TRANSPARENT
            navigationBarColor = Color.TRANSPARENT
            decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
        }

        dialog.setCanceledOnTouchOutside(false)

        dialog.show()

    }


    fun validateLoginInput(mobileOrEmailID: String, context: Context): Boolean {
        val isMobile = mobileOrEmailID.all { it.isDigit() } && mobileOrEmailID.length == 10

        val isEmail = Patterns.EMAIL_ADDRESS.matcher(mobileOrEmailID).matches()

        if (!isMobile && !isEmail) {
            Toast.makeText(context, "Enter a valid 10-digit mobile number or valid email address", Toast.LENGTH_SHORT).show()
            return false
        }

        return true // Input is valid
    }


    fun getCityStateFromPincode(context: Context, pincode: String, callback: (String?, String?, String?) -> Unit) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://api.postalpincode.in/pincode/$pincode")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                Handler(Looper.getMainLooper()).post {
                    callback(null, null, null)
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    try {
                        val jsonArray = JSONArray(jsonString)
                        val postOfficeArray = jsonArray.getJSONObject(0).getJSONArray("PostOffice")
                        if (postOfficeArray.length() > 0) {
                            val postOffice = postOfficeArray.getJSONObject(0)
                            val city = postOffice.getString("District")
                            val state = postOffice.getString("State")
                            val country = postOffice.getString("Country")

                            Handler(Looper.getMainLooper()).post {
                                callback(city, state, country)
                            }
                        } else {
                            Handler(Looper.getMainLooper()).post {
                                callback(null, null, null)
                            }
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Handler(Looper.getMainLooper()).post {
                            callback(null, null, null)
                        }
                    }
                }
            }
        })
    }


    fun isValidPinCode(pinCode: String): Boolean {
        return pinCode.matches(Regex("^[1-9][0-9]{5}$"))
    }


    fun cacheImageAndGetUri(context: Context, imageUrl: String): Uri? {
        return try {
            // 🧾 Extract filename from the URL
            val fileName = imageUrl.substringAfterLast("/")

            // 📁 Create sub-folder in cache
            val sharedDir = File(context.cacheDir, "shared_images")
            if (!sharedDir.exists()) sharedDir.mkdirs()

            // 🖼️ File path where image will be saved
            val cacheFile = File(sharedDir, fileName)

            // ⬇️ Download only if not already cached
            if (!cacheFile.exists()) {
                val url = URL(imageUrl)
                url.openStream().use { input ->
                    FileOutputStream(cacheFile).use { output ->
                        input.copyTo(output)
                    }
                }
            }


            FileProvider.getUriForFile(context, context.packageName + ".fileprovider",  // must match manifest
                cacheFile
            )

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun cacheImageAndGetUriForShortCutLoan(
        context: Context,
        imageUrl: String
    ): Uri? {
        return try {

            val fileName = imageUrl
                .substringAfterLast("/")
                .substringBefore("?")

            val sharedDir = File(context.cacheDir, "shared_images")

            if (!sharedDir.exists()) {
                sharedDir.mkdirs()
            }

            val cacheFile = File(sharedDir, fileName)

            if (!cacheFile.exists()) {

                val connection = URL(imageUrl).openConnection() as HttpURLConnection

                connection.connectTimeout = 15_000
                connection.readTimeout = 15_000
                connection.requestMethod = "GET"

                connection.connect()

                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    connection.disconnect()
                    return null
                }

                connection.inputStream.use { input ->
                    FileOutputStream(cacheFile).use { output ->
                        input.copyTo(output)
                    }
                }

                connection.disconnect()
            }

            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                cacheFile
            )

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun bitmapToUri(context: Context, bitmap: Bitmap): Uri? {
        return try {
            // Create sub-folder inside /cache/shared_images
            val sharedDir = File(context.cacheDir, "shared_images")
            if (!sharedDir.exists()) sharedDir.mkdirs()

            // Create file inside that folder
            val file = File(sharedDir, "customerimage_${System.currentTimeMillis()}.jpg")

            // Write bitmap to the file
            FileOutputStream(file).use { out ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }

            // Return FileProvider URI
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun uriToFile(uri: Uri, context: Context): File? {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val tempFile = File.createTempFile("upload_", ".jpg", context.cacheDir)
        tempFile.outputStream().use { fileOut ->
            inputStream.copyTo(fileOut)
        }
        return tempFile
    }


   /* fun saveImageToCache(context: Context, imageUri: Uri, filename: String): File? {

        return try {

            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }

            context.contentResolver.openInputStream(imageUri)?.use {
                BitmapFactory.decodeStream(it, null, options)
            }

            // Resize large images
            options.inSampleSize = calculateInSampleSize(options, 1080, 1080)

            options.inJustDecodeBounds = false

            val bitmap = context.contentResolver.openInputStream(imageUri)?.use {
                BitmapFactory.decodeStream(it, null, options)
            } ?: return null

            val fileName = "${filename}_${System.currentTimeMillis()}.jpg"

            val tempFile = File(context.cacheDir, fileName)

            val sizeInBytes = tempFile.length()
            val sizeInKB = sizeInBytes / 1024.0
            val sizeInMB = sizeInKB / 1024.0

            Log.d("FILE_SIZE", "Size: %.2f KB".format(sizeInKB))

            FileOutputStream(tempFile).use { output ->

                // 60% gives much faster upload
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, output)

                output.flush()
            }

            bitmap.recycle()

            tempFile

        } catch (e: Exception) {

            e.printStackTrace()
            null
        }
    }*/


    fun saveImageToCache(context: Context, imageUri: Uri, filename: String): File? {

        return try {

            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true
            }

            context.contentResolver.openInputStream(imageUri)?.use {
                BitmapFactory.decodeStream(it, null, options)
            }

            options.inSampleSize = calculateInSampleSize(options, 1080, 1080)
            options.inJustDecodeBounds = false

            val bitmap = context.contentResolver.openInputStream(imageUri)?.use {
                BitmapFactory.decodeStream(it, null, options)
            } ?: return null


            val tempFile = File(context.cacheDir, "${filename}_${System.currentTimeMillis()}.jpg")

            var quality = 90

            do {
                FileOutputStream(tempFile).use { output ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, output)
                    output.flush()
                }

                val fileSizeKB = tempFile.length() / 1024

                Log.d("FILE_SIZE", "Quality=$quality, Size=${fileSizeKB}KB")

                if (fileSizeKB <= 600) {
                    break
                }

                quality -= 5

            } while (quality >= 20)

            bitmap.recycle()
            Log.d("FILE_SIZE_FINAL", "Final Size=${tempFile.length() / 1024}KB")
            tempFile

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {

        val height = options.outHeight
        val width = options.outWidth

        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight = height / 2
            val halfWidth = width / 2

            while ((halfHeight / inSampleSize) >= reqHeight &&
                (halfWidth / inSampleSize) >= reqWidth) {

                inSampleSize *= 2
            }
        }

        return inSampleSize
    }


    fun downloadImageToTemp(context: Context, imageUrl: String): File? {
        return try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection

            connection.connectTimeout = 15_000
            connection.readTimeout = 15_000
            connection.requestMethod = "GET"
            connection.connect()

            if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                connection.disconnect()
                return null
            }

            val file = File(context.cacheDir, "customer_photo_${System.currentTimeMillis()}.jpg")

            connection.inputStream.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }

            connection.disconnect()

            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }



    fun createMultipartFromUri(context: Context, uri: Uri?, partName: String, filename:String): MultipartBody.Part? {
        return uri?.let {
            val cacheFile = saveImageToCache(context, it, filename )
            cacheFile?.let { file ->
                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                MultipartBody.Part.createFormData(partName, file.name, requestFile)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun calculateEmiEndDateFromNow(tenureMonths: Int): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val startDate = ZonedDateTime.now(ZoneOffset.UTC)
        val endDate = startDate.plusDays(1).plusMonths(tenureMonths.toLong())
        return endDate.format(formatter)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentStartDate(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val now = ZonedDateTime.now(ZoneOffset.UTC)
        return now.format(formatter)
    }


    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateToDDMMYYYY(inputDate: String?): String {
        return try {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val dateTime = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LocalDateTime.parse(inputDate, inputFormatter)
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            dateTime.format(outputFormatter)
        } catch (e: Exception) {
            "" // return empty string or handle error as needed
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDueDateGracePeriodDateToDDMMYYYY(inputDate: String, gracePeriod: Int, paidEmiCount: Int): String {
        return try {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

            // Parse input date
            val dateTime = LocalDateTime.parse(inputDate, inputFormatter)

            // Step 1: always move to next month
            var dueDate = dateTime.plusMonths(1)

            // Step 2: if grace period > 0, add days
            if (gracePeriod > 0) {
                dueDate = dueDate.plusDays(gracePeriod.toLong())
            }

            // Step 3: shift forward for already paid EMIs
            if (paidEmiCount > 0) {
                dueDate = dueDate.plusMonths(paidEmiCount.toLong())
            }

            dueDate.format(outputFormatter)
        }
        catch (e: Exception) {
            "" // return empty string or handle error as needed
        }

    }


    fun generateEMIOptions(maxEMI: Int): Array<String> {
        return (1..maxEMI).map { it.toString() }.toTypedArray()
    }


    fun getCurrentUtcTimestamp(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(Date())
    }


    fun EditText.disableCopyPaste() {
        this.isLongClickable = false
        this.setTextIsSelectable(false)
        this.customInsertionActionModeCallback = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?) = false
            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?) = false
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?) = false
            override fun onDestroyActionMode(mode: ActionMode?) {}
        }
        this.customSelectionActionModeCallback = this.customInsertionActionModeCallback
    }


    fun formatDateToFullMonth(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: return "")
        } catch (e: Exception) {
            ""
        }
    }

    fun formatDateToReport(dateString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val date = inputFormat.parse(dateString)
            outputFormat.format(date ?: return "")
        } catch (e: Exception) {
            ""
        }
    }

    fun getDeviceIpAddress(): String? {
        try {
            val interfaces = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (intf in interfaces) {
                val addrs = Collections.list(intf.inetAddresses)
                for (addr in addrs) {
                    if (!addr.isLoopbackAddress) {
                        val ip = addr.hostAddress
                        // Skip IPv6
                        if (ip.indexOf(':') < 0) return ip
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    suspend fun getPublicIpAddress(): String? {
        return withContext(Dispatchers.IO) {
            try {
                URL("https://api.ipify.org").readText().trim()
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    fun formatToMMDDYYYY(dateStr: String): String {
        val inputFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val date = inputFormat.parse(dateStr)
        return outputFormat.format(date!!)
    }


    fun checkActiveStatusAndLogout(context: Context, activeStatus: String?, preference: SharedPreference) {

        if (ConstantClass.SessionOutStatus.equals(activeStatus!!.toLowerCase())!! || ConstantClass.SessionOutStatusRejected.equals(activeStatus!!.toLowerCase())) {
            try {
                Toast.makeText(context, "Your account is inactive. Please contact support.", Toast.LENGTH_LONG).show()

                preference.setBooleanValue(ConstantClass.LoggedIn, false)
                preference.setStringValue(ConstantClass.LoginType, "")
                ConstantClass.ClickOnCardDashboard = ""
                val intent = Intent(context, ChooseYourRolePage::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context.startActivity(intent)

                // Optional: if inside Activity, finish it
                if (context is Activity) {
                    context.finish()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    fun base64ToBitmap(base64String: String): Bitmap? {
        return try {
            val decodedBytes = Base64.decode(base64String, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


  /*  fun convertToDDMMYYYY(isoDate: String ?): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SS", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val date = inputFormat.parse(isoDate)
        return outputFormat.format(date!!)
    }*/


    fun convertToDDMMYYYY(dateValue: String?): String {
        if (dateValue.isNullOrBlank()) return "-"
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).apply {
                isLenient = false
            }
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            inputFormat.parse(dateValue)?.let(outputFormat::format) ?: "-"
        } catch (e: Exception) {
            "-"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDateTime(input: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val outputFormatter = DateTimeFormatter.ofPattern(
            "dd MMM yyyy HH 'hr' mm 'min' ss 'sec'"
        )

        val dateTime = LocalDateTime.parse(input, inputFormatter)
        return dateTime.format(outputFormatter)
    }


    fun isGPSEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }


    /*   @RequiresApi(Build.VERSION_CODES.O)
    fun Context.scheduleLocationWorker(lat: Double, long: Double) {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        // 🔹 One-time work (immediate)
        val oneTimeWork = OneTimeWorkRequestBuilder<LocationUploadWorker>()
            .setInputData(workDataOf("LAT" to lat, "LONG" to long))
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(oneTimeWork)

        // 🔹 Periodic work (background sync)
        val periodicWork = PeriodicWorkRequestBuilder<LocationUploadWorker>(15, TimeUnit.MINUTES)
            .setInputData(workDataOf("LAT" to lat, "LONG" to long))
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "LocationUploadWork",
            ExistingPeriodicWorkPolicy.KEEP, // 🔥 IMPORTANT
             periodicWork)

       Log.d("calledworkmanagermethod","yes")

    }*/


    fun Context.scheduleOneTimeLocationWorker(lat: Double, long: Double) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val work = OneTimeWorkRequestBuilder<LocationUploadWorker>()
            .setInputData(workDataOf("LAT" to lat, "LONG" to long))
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueue(work)

        Log.d("LocationWorker", "One-time worker scheduled")
    }


    fun Context.scheduleLocationWorker(lat: Double, long: Double) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val periodicWork = PeriodicWorkRequestBuilder<LocationUploadWorker>(1, TimeUnit.HOURS)
            .setInputData(workDataOf("LAT" to lat, "LONG" to long))
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "LocationUploadWork",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWork
        )

        Log.d("LocationWorker", "Periodic worker scheduled")
    }


    fun decodeBase64(base64Value: String): String {
        val decodedBytes = Base64.decode(base64Value, Base64.DEFAULT)
        return String(decodedBytes, Charsets.UTF_8)
    }


    fun generateQRCode(text: String, size: Int = 500): Bitmap {
        val bitMatrix: BitMatrix = MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, size, size)

        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)

        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(
                    x,
                    y,
                    if (bitMatrix[x, y]) android.graphics.Color.BLACK
                    else android.graphics.Color.WHITE
                )
            }
        }
        return bitmap
    }


    fun getAddressFromLatLng(context: Context, latitude: Double, longitude: Double): String {
        return try {
            val geocoder = Geocoder(context, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)

            if (!addresses.isNullOrEmpty()) {
                val address = addresses[0]

                var  Address = address.getAddressLine(0) ?: ""
                var cityName = address.locality ?: ""
                var district = address.subAdminArea ?: ""
                val state = address.adminArea ?: ""
                var pincode = address.postalCode ?: ""

                // You can return full formatted string
                Log.d("Address", "Address: $Address\nCity: $cityName\nDistrict: $district\nState: $state\nPincode: $pincode")
                "Address: $Address\nCity: $cityName\nDistrict: $district\nState: $state\nPincode: $pincode"
                return "Address: $Address , $cityName , $district ($pincode)"
            }
            else {
                "Address not found"
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error: ${e.message}"
        }
    }


    fun Activity.showDevModeSnackbar(rootView: View) {
        Snackbar.make(
            rootView,
            "Developer Options are enabled. Please disable to continue.",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("OPEN SETTINGS") {
            openDeveloperOptions()
        }.show()
    }


    fun Activity.openDeveloperOptions() {
        try {
            startActivity(Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS))
        } catch (e: Exception) {
            // Fallback for some devices
            startActivity(Intent(Settings.ACTION_SETTINGS))
        }
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    fun isDevModeEnabled(context: Context): Boolean {
        return try {
            when {
                Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN -> {
                    Settings.Global.getInt(
                        context.contentResolver,
                        Settings.Global.DEVELOPMENT_SETTINGS_ENABLED,
                        0
                    ) != 0
                }
                Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN -> {
                    @Suppress("DEPRECATION")
                    Settings.Secure.getInt(
                        context.contentResolver,
                        Settings.Secure.DEVELOPMENT_SETTINGS_ENABLED,
                        0
                    ) != 0
                }
                else -> false
            }
        } catch (e: Exception) {
            false
        }
    }


    fun uploadDataOnFirebaseConsole(data:String, collectionPath:String/*,context: Context*/){
     /*   val context = context*/
        val db = Firebase.firestore

        val sdf = SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault())
        val currentDateTime = sdf.format(Date())


        // Create a new user with a first and last name
        val logData = hashMapOf(
            "filename" to "OQPAY.txt",
            "content" to data,
            "timestamp" to System.currentTimeMillis()
        )

        db.collection(collectionPath)
            .document(currentDateTime)
            .set(logData)
            .addOnSuccessListener {
                //Toast.makeText(context, "Log saved in Firestore", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Log.d("Error", " $e.message")
                //Toast.makeText(context, "Failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }


    fun String.convertDate(): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")

        val date = inputFormat.parse(this)
        return outputFormat.format(date!!)
    }


    fun Double.twoDecimal(): String = String.format("%.2f", this)


    fun formatRecordDateTime(recordDate: String, recordTime: String): String {
        return try {
            // Combine date + time
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM yyyy • h:mm a", Locale.getDefault())

            val dateTime = inputFormat.parse(
                recordDate.replace("T00:00:00", "T$recordTime")
            )

            outputFormat.format(dateTime!!)
        } catch (e: Exception) {
            ""
        }
    }


    fun eMandateformatDate(input: String): String {
        return try {
            val parsed = java.time.LocalDateTime.parse(input)
            parsed.toLocalDate().toString() // yyyy-MM-dd
        } catch (e: Exception) {
            ""
        }
    }


    fun scrollToView(scrollView: NestedScrollView, targetView: View) {
        scrollView.post {
            /*scrollView.smoothScrollTo(0, targetView.top - offset)*/

            val location = IntArray(2)
            targetView.getLocationOnScreen(location)

            val scrollLocation = IntArray(2)
            scrollView.getLocationOnScreen(scrollLocation)

            val y = location[1] - scrollLocation[1]
            scrollView.smoothScrollBy(0, y)
        }
    }


    fun formatIndianAmount(amount: String): String {

        return try {

            val number = amount.toDouble()

            val formatter = NumberFormat.getNumberInstance(Locale("en", "IN"))

            formatter.maximumFractionDigits = 0
            formatter.minimumFractionDigits = 0

            "₹${formatter.format(number)}"

        } catch (e: Exception) {
            "₹0"
        }
    }


    fun isValidGST(gstin: String): Boolean {
        val regex = Regex("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$")
        return regex.matches(gstin.uppercase())
    }

     fun saveRequestToFile(json: String,context: Context) {
        try {
            val values = ContentValues().apply {
                put(MediaStore.Downloads.DISPLAY_NAME, "OnlineCustomerReq.txt")
                put(MediaStore.Downloads.MIME_TYPE, "text/plain")
                put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(
                MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                values
            )

            if (uri != null) {
                resolver.openOutputStream(uri)?.use { output ->
                    output.write(json.toByteArray())
                }

                Log.d("FileSave", "Saved to Downloads: $uri")
            } else {
                Log.e("FileSave", "Could not create file")
            }

        } catch (e: Exception) {
            Log.e("FileSave", "Error saving request to file: ${e.message}")
        }
    }


// DPCAPPJSON

   /* {
        "android.app.extra.PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME":
        "com.afwsamples.testdpc/com.afwsamples.testdpc.DeviceAdminReceiver",

        "android.app.extra.PROVISIONING_DEVICE_ADMIN_SIGNATURE_CHECKSUM":
        "gJD2YwtOiWJHkSMkkIfLRlj-quNqG1fb6v100QmzM9w=",

        "android.app.extra.PROVISIONING_DEVICE_ADMIN_PACKAGE_DOWNLOAD_LOCATION":
        "https://api.oqpay.in/api/V1/OQFinance/download-DPC",

        "android.app.extra.PROVISIONING_SKIP_ENCRYPTION": true,

        "android.app.extra.PROVISIONING_LEAVE_ALL_SYSTEM_APPS_ENABLED": true
    }*/


}
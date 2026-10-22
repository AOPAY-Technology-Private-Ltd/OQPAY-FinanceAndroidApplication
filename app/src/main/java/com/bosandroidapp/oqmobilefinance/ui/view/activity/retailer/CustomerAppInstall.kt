package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer

import android.R
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.beastblocks.provisionerjattsdk.PairStatus
import com.beastblocks.provisionerjattsdk.PairingModeSwitch
import com.beastblocks.provisionerjattsdk.ProvisionerClient
import com.beastblocks.provisionerjattsdk.ProvisionerJatt
import com.beastblocks.provisionerjattsdk.ProvisionerJattFrp
import com.beastblocks.provisionerjattsdk.ProvisionerJattListener
import com.beastblocks.provisionerjattsdk.ProvisioningStatus
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass

import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.dialog
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.databinding.ActivityCustomerAppInstallBinding
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.ui.slideshow.activity.DashBoard
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomerAppInstall : BaseActivity() {
    lateinit var binding: ActivityCustomerAppInstallBinding
    lateinit var viewModel: AuthenticationViewModel
    lateinit var preference: SharedPreference
    private lateinit var client: ProvisionerClient
    private lateinit var callback : ProvisionerJattListener
    private var pairStatus: PairStatus? = null
    private var isDialogClosed: Boolean = false
    private var isModeSwitching: Boolean = false

    private val isPairConnected: Boolean
        get() = pairStatus != null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomerAppInstallBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        client = ProvisionerJatt.get()
        
        viewModel = ViewModelProvider(this,
            CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterfacePAN))
        )[AuthenticationViewModel::class.java]
        setonClickListner()

    }

    fun setonClickListner(){

        binding.home.setOnClickListener {
            val intent = Intent(this, DashBoard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            onBackPressed()
        }


        binding.back.setOnClickListener {
            OpenPopUpForVAlert()
        }


        binding.clicktoopenappqr.setOnClickListener {
            OpenPopUpForQRScanAlert()
        }


        binding.startProvisioning.setOnClickListener {
            startProvisioning()
        }



        callback = object : ProvisionerJattListener {


            override fun onProvisioningStatusUpdate(status: ProvisioningStatus) {
                super.onProvisioningStatusUpdate(status)

                when (status) {
                    ProvisioningStatus.SUCCEEDED -> {
                        Log.d("onProvisioningStatusUpdate", "${status}")
                        runOnUiThread {
                            showAppInstalledSuccessPopUp()
                        }
                    }
                    else -> {
                        Log.d("onProvisioningStatusUpdate", "${status}")
                    }

                }

            }


            override fun onQRDialogClosed() {
                super.onQRDialogClosed()
                Log.d("Dialog", "QR Closed")
                if (isModeSwitching) {
                    Log.d("Dialog", "QR Closed during mode switch: Not detaching")
                    isModeSwitching = false
                    return
                }
                isDialogClosed = true
                if (!isPairConnected) {
                    if (::client.isInitialized) client.detach(this@CustomerAppInstall)
                    pairStatus = null
                } else {
                    Log.d("Dialog", "QR Closed: Not detaching because pairStatus is $pairStatus")
                }
            }


            override fun onPairingDialogClosed() {
                super.onPairingDialogClosed()
                Log.d("Dialog", "Pair Closed")
                if (isModeSwitching) {
                    Log.d("Dialog", "Pair Closed during mode switch: Not detaching")
                    isModeSwitching = false
                    return
                }
                isDialogClosed = true
                if (!isPairConnected) {
                    if (::client.isInitialized) client.detach(this@CustomerAppInstall)
                    pairStatus = null
                } else {
                    Log.d("Dialog", "Pair Closed: Not detaching because pairStatus is $pairStatus")
                }
            }


            override fun onProvisioningDialogClosed() {
                super.onProvisioningDialogClosed()
                Log.d("Dialog", "Provisioning Closed")
            }


            override fun onQRDialogInvoked() {
                super.onQRDialogInvoked()
                Log.d("Dialog", "QRDialogInvoked")
                isDialogClosed = false
            }


            override fun onPairingDialogInvoked() {
                super.onPairingDialogInvoked()
                Log.d("Dialog", "PairingDialogInvoked")
                isDialogClosed = false
            }


            override fun onProvisioningDialogInvoked() {
                super.onProvisioningDialogInvoked()
                Log.d("Dialog", "ProvisioningDialogInvoked")
            }


            override fun onPairingModeSwitch(change: PairingModeSwitch) {
                super.onPairingModeSwitch(change)
                Log.d("Dialog", "PairingModeSwitch: $change")
                isModeSwitching = true
            }


            override fun onPairStatusUpdate(status: PairStatus) {
                super.onPairStatusUpdate(status)
                pairStatus = status
                Log.d("Dialog", "PairStatusUpdate: $status")
            }


            override fun onQRPairCodeStatusUpdate(status: PairStatus) {
                super.onQRPairCodeStatusUpdate(status)
                pairStatus = status
                Log.d("Dialog", "QRPairCodeStatusUpdate: $status")
            }


        }

    }


    @SuppressLint("SetTextI18n")
    fun showAppInstalledSuccessPopUp() {
        if (isFinishing || isDestroyed) return

        val successDialog = Dialog(
            this,
            android.R.style.Theme_Translucent_NoTitleBar
        )

        successDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        successDialog.setContentView(com.bosandroidapp.oqmobilefinance.R.layout.success_app_install)

        successDialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setDimAmount(0.5f)
            addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }

        successDialog.setCanceledOnTouchOutside(false)

        val btnOk = successDialog.findViewById<View>(com.bosandroidapp.oqmobilefinance.R.id.btnOk)
        val tvMessage = successDialog.findViewById<TextView>(com.bosandroidapp.oqmobilefinance.R.id.loancodewithamount)
        val tvTitle = successDialog.findViewById<TextView>(com.bosandroidapp.oqmobilefinance.R.id.tvTitle)

        tvTitle.text = "App Installed Successfully"
        tvMessage.text =
            "The customer app has been provisioned and installed successfully."

        btnOk.setOnClickListener {
            successDialog.dismiss()
            pairStatus = null
            if (::client.isInitialized) client.detach(this)
            val intent = Intent(this, DashBoard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        successDialog.show()

        successDialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

    }



    fun startProvisioning(){
        isDialogClosed = false
        isModeSwitching = false
        pairStatus = null
        ProvisionerJatt.resetSession()
        if (::client.isInitialized) client.detach(this)
        client.clearAutomationAndSerial()
        client.scanThenAutomateThenAttach(this, this, packageName, ConstantClass.CUSTOMERPPURLLINK, callback )

    }



    override fun onPause() {
        super.onPause()
        if (!isModeSwitching && (isDialogClosed || !isPairConnected)) {
            if (::client.isInitialized) client.detach(this)
            pairStatus = null
        }
    }



    override fun onDestroy() {
        pairStatus = null
        if (::client.isInitialized) client.detach(this)
        super.onDestroy()
    }



    @SuppressLint("SetTextI18n")
    fun OpenPopUpForVAlert() {
        dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(com.bosandroidapp.oqmobilefinance.R.layout.signoutalert)


        dialog!!.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        dialog!!.setCanceledOnTouchOutside(false)

        val cancel = dialog!!.findViewById<Button>(com.bosandroidapp.oqmobilefinance.R.id.btnCancel)
        val done = dialog!!.findViewById<Button>(com.bosandroidapp.oqmobilefinance.R.id.btnLogout)
        val txt = dialog!!.findViewById<TextView>(com.bosandroidapp.oqmobilefinance.R.id.dialog_message)
        val image = dialog!!.findViewById<ImageView>(com.bosandroidapp.oqmobilefinance.R.id.imageview)

        image.visibility = View.VISIBLE

        done.text = "OK"

        txt.text = "Are you sure you want to go back?"

        done.setOnClickListener {
            finish()


        }

        cancel.setOnClickListener {
            dialog!!.dismiss()
        }

        dialog!!.show()

    }



    @SuppressLint("SetTextI18n")
    fun OpenPopUpForQRScanAlert() {
        dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog!!.setContentView(com.bosandroidapp.oqmobilefinance.R.layout.appdownloadqrlayout)


        dialog!!.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        dialog!!.setCanceledOnTouchOutside(false)

        val cancel = dialog!!.findViewById<Button>(com.bosandroidapp.oqmobilefinance.R.id.btnClose)

        val provisioningQR = dialog!!.findViewById<ImageView>(com.bosandroidapp.oqmobilefinance.R.id.qr_code_provising)
        val progressbar = dialog!!.findViewById<ProgressBar>(com.bosandroidapp.oqmobilefinance.R.id.progressbar)

        hitApiForDownloadAppUrlLinkQR(provisioningQR,progressbar)

        cancel.setOnClickListener {
            dialog!!.dismiss()
        }

        dialog!!.show()

    }



    fun hitApiForDownloadAppUrlLinkQR(qrCodeProvising: ImageView, progressBar: ProgressBar) {
        lifecycleScope.launch {

            progressBar.visibility = View.VISIBLE
            qrCodeProvising.visibility = View.GONE

            try {

                val bitmap = withContext(Dispatchers.IO) {

                    val response = RetrofitClient.apiInterface.getApkUrlLink()

                    if (response!!.isSuccessful && response.body() != null) {

                        response.body()!!.byteStream().use { inputStream ->
                            BitmapFactory.decodeStream(inputStream)
                        }

                    } else {
                        null
                    }
                }

                bitmap?.let {
                    qrCodeProvising.setImageBitmap(it)
                }

            } catch (e: Exception) {

                e.printStackTrace()

            } finally {

                progressBar.visibility = View.GONE
                qrCodeProvising.visibility = View.VISIBLE
            }
        }


    }


}
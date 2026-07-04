package com.bosandroidapp.oqmobilefinance.ui.view.activity.customer

import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bos.payment.appName.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.isInternetAvailable
import com.bosandroidapp.oqmobilefinance.data.model.CustomerEMIDataItem
import com.bosandroidapp.oqmobilefinance.data.model.CustomerEmiStatusReq
import com.bosandroidapp.oqmobilefinance.data.model.SessionOutReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.CustomerDataItem
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetCustomerLoanDetailsReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.databinding.ActivityCustomerEmistatusReportBinding
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.slideshow.adapter.CustomerEMIDetailsAdapter
import com.bosandroidapp.oqmobilefinance.ui.view.adapter.CustomerEmiStatusAdapter
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson

class CustomerEMIStatusReport : AppCompatActivity() {

    lateinit var binding: ActivityCustomerEmistatusReportBinding
    lateinit var viewModel: AuthenticationViewModel
    lateinit var preference : SharedPreference
    var customerLoanEmiDetailsList : MutableList<CustomerEMIDataItem?>? = mutableListOf()
    lateinit var adapter : CustomerEmiStatusAdapter

    companion object{
        var loanCode : String = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerEmistatusReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        preference = SharedPreference(this)
        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]

        setonClickListner()

    }

    fun  setonClickListner(){

        binding.back.setOnClickListener {
            finish()
        }

    }

    override fun onResume() {
        super.onResume()
        if(isInternetAvailable(this@CustomerEMIStatusReport)) {
            HitApiForEmiList()
            hitApiForLogin(preference.getStringValue(ConstantClass.CustomerCode,""))
        }
    }


    fun HitApiForEmiList(){
        var loanemireq = CustomerEmiStatusReq(
            loanCode = loanCode
        )
        Log.d("customerloanEmireq",Gson().toJson(loanemireq))

        viewModel.LoanEmIScheduleWithStatusReq(loanemireq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let {
                                response ->
                                Log.d("customerLoanemiresp", Gson().toJson(response))

                                if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing) {
                                    ConstantClass.dialog.dismiss()
                                }

                                    if(response!=null){
                                        var LoanEmiList = response.data
                                        customerLoanEmiDetailsList = LoanEmiList as MutableList<CustomerEMIDataItem?>?
                                        if(!customerLoanEmiDetailsList.isNullOrEmpty() && customerLoanEmiDetailsList!!.size>0){
                                            binding.showingLoanList.visibility=View.VISIBLE
                                            binding.notfoundimage.visibility= View.GONE
                                            setDataOnView(customerLoanEmiDetailsList)
                                        }
                                        else{
                                            binding.showingLoanList.visibility=View.GONE
                                            binding.notfoundimage.visibility= View.VISIBLE
                                        }
                                    }
                                    else{
                                        HitApiForEmiList()
                                    }
                            }
                        }

                    }

                    ApiStatus.ERROR -> {
                        if(ConstantClass.dialog!=null && ConstantClass.dialog.isShowing){
                            ConstantClass.dialog.dismiss()
                        }
                        HitApiForEmiList()

                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }
            }
        }

    }


    fun setDataOnView(customerLoanEmiDetailsList : MutableList<CustomerEMIDataItem?>?){
        adapter = CustomerEmiStatusAdapter(this,customerLoanEmiDetailsList!!)
        binding.showingLoanList.adapter = adapter
        adapter.notifyDataSetChanged()
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
                                ConstantClass.checkActiveStatusAndLogout(this@CustomerEMIStatusReport, response.status, preference)
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
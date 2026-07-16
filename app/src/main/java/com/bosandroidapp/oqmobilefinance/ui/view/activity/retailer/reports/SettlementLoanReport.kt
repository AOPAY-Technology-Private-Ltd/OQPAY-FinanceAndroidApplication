package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.reports

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.bos.payment.appName.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.GetReportsReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.reports.LoanSettlementReportReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.databinding.ActivitySettlementLoanReportBinding
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.slideshow.adapter.RetailerReportListAdapter
import com.bosandroidapp.oqmobilefinance.ui.view.adapter.LoanSettlementAdapter
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson

class SettlementLoanReport : BaseActivity() {
    lateinit var binding:ActivitySettlementLoanReportBinding
    lateinit var adapter: LoanSettlementAdapter
    lateinit var viewModel: AuthenticationViewModel
    lateinit var preference : SharedPreference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettlementLoanReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]
        preference = SharedPreference(this)
        setOnClickListner()


    }

    fun setOnClickListner(){

        binding.back.setOnClickListener {
            finish()
        }

    }

    fun hitApiForLoanReport(){
        var retailerCode = preference.getStringValue(ConstantClass.RetailerCode,"")

        var reportreq = LoanSettlementReportReq(
            retailerCode = retailerCode
        )

        Log.d("ReportReq", Gson().toJson(reportreq))
        viewModel.loanSettlementReportReq(reportreq).observe(this){
                resources->resources.let {
            when(it.apiStatus){
                ApiStatus.SUCCESS -> {
                    it.data?.let { users ->
                        users.body()?.let { response ->
                            ConstantClass.dialog.dismiss()
                            Log.d("MobileRes", Gson().toJson(response) )
                            var ReportDataList = response.data!!.toMutableList()
                            if(ReportDataList.size>0){
                                binding.showloanSettlementReport.visibility= View.VISIBLE
                                binding.notfoundimage.visibility= View.GONE
                                adapter = LoanSettlementAdapter(this,ReportDataList)
                                binding.showloanSettlementReport.adapter = adapter
                                adapter.notifyDataSetChanged()

                            }else{
                                binding.showloanSettlementReport.visibility= View.GONE
                                binding.notfoundimage.visibility= View.VISIBLE
                            }
                        }
                    }
                }

                ApiStatus.ERROR -> {
                    ConstantClass.dialog.dismiss()
                }

                ApiStatus.LOADING -> {
                    ConstantClass.OpenPopUpForVeryfyOTP(this)
                }

            }

        }

        }
    }

    override fun onResume() {
        super.onResume()
        hitApiForLoanReport()
    }

}
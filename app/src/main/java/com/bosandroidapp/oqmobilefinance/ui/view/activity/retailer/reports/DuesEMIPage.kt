package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.reports

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bos.payment.appName.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.clickMakePaymentPage
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.formatDateToDDMMYYYY
import com.bosandroidapp.oqmobilefinance.data.model.DueOverdueRequest
import com.bosandroidapp.oqmobilefinance.data.model.OverdueDataItem
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.GetCustomerLoanDetailsReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.databinding.ActivityPendingEmisPageBinding
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.slideshow.adapter.DueOverdueCustomerReports
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.MobileSelectionActivity.Companion.FilterDataList
import com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.MobileSelectionActivity.Companion.MobileDataList
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.bosandroidapp.oqmobilefinance.utils.getCurrentLastPaidDueDate
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class DuesEMIPage : BaseActivity() {
    lateinit var binding : ActivityPendingEmisPageBinding
    lateinit var viewModel: AuthenticationViewModel
    lateinit var preference: SharedPreference
    lateinit var adapter: DueOverdueCustomerReports
    var dueOverdueDataList: MutableList<OverdueDataItem?>? = mutableListOf()
    var FilterdueOverdueDataList: MutableList<OverdueDataItem?>? = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPendingEmisPageBinding.inflate(layoutInflater)
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


    override fun onResume() {
        super.onResume()

        setSpinner()
    }


    private fun setOnClickListner(){
        binding.back.setOnClickListener {
            finish()
        }

        binding.duestatus.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
               var selected = binding.duestatus.selectedItem.toString()
                if(selected.equals(ConstantClass.TODAYDUE)){
                    hitApiForGettingDueOverDueDataList(ConstantClass.Due)
                }else{
                    hitApiForGettingDueOverDueDataList(ConstantClass.Overdue)
                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {


            }


        }

        binding.searcMobile.addTextChangedListener(
            object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val search = s.toString().lowercase().trim()
                    val result = dueOverdueDataList!!.filter {
                        it!!.primaryMobileNumber!!.lowercase().contains(search) || it.loanCode!!.lowercase().contains(search)||
                                it.customerCode!!.lowercase().contains(search)
                    }
                    FilterdueOverdueDataList!!.clear()
                    FilterdueOverdueDataList!!.addAll(result)
                    setDataonView(FilterdueOverdueDataList)
                    adapter.notifyDataSetChanged()
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            }
        )



    }


    private fun setSpinner(){
        val adapter = ArrayAdapter.createFromResource(this,  R.array.duesreporttype, R.layout.mobilenamelayout)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.duestatus.adapter = adapter

    }


    fun hitApiForGettingDueOverDueDataList(reporttype:String){
        var loanemireq = DueOverdueRequest(
            retailerCode = preference.getStringValue(ConstantClass.RetailerCode, ""),
            reportType = reporttype
        )

        Log.d("dueoverduereq", Gson().toJson(loanemireq))

        viewModel.getCustomerLoanEmiDetailsReq(loanemireq).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("dueoverduewresp", Gson().toJson(response))
                                ConstantClass.dialog.dismiss()
                                val dueOverdueList = response.data
                                dueOverdueDataList!!.clear()

                                if(dueOverdueList!!.isNotEmpty()){
                                    binding.notfoundimage.visibility=View.GONE
                                    binding.showreports.visibility=View.VISIBLE
                                    dueOverdueDataList = dueOverdueList
                                    setDataonView(dueOverdueDataList)
                                }
                                else{
                                    binding.notfoundimage.visibility=View.VISIBLE
                                    binding.showreports.visibility=View.GONE
                                }

                            }

                        }

                    }


                    ApiStatus.ERROR -> {
                        binding.notfoundimage.visibility=View.VISIBLE
                        binding.showreports.visibility=View.GONE
                        ConstantClass.dialog.dismiss()
                    }


                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(this)
                    }

                }
            }
        }

    }


    fun setDataonView(dueDataList:MutableList<OverdueDataItem?>?){
        adapter = DueOverdueCustomerReports(this@DuesEMIPage,dueDataList)
        binding.showreports.adapter = adapter
        adapter.notifyDataSetChanged()
    }


}
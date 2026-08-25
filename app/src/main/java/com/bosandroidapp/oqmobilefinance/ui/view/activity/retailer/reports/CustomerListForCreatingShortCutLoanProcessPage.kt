package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer.reports

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bos.payment.appName.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.data.model.CustomerListDataItem
import com.bosandroidapp.oqmobilefinance.data.model.RetailerPerCustomerListShortCutForLoanReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.databinding.ActivityCustomerListForCreatingShortCutLoanProcessPageBinding
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.view.adapter.CustomerShortcutLoanAdapter
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson

class CustomerListForCreatingShortCutLoanProcessPage : AppCompatActivity() {

    private lateinit var preference : SharedPreference
    private lateinit var viewModel: AuthenticationViewModel
    private var customerList : List<CustomerListDataItem> = mutableListOf()
    private lateinit var customerAdapter: CustomerShortcutLoanAdapter
    private lateinit var binding : ActivityCustomerListForCreatingShortCutLoanProcessPageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomerListForCreatingShortCutLoanProcessPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]
        preference = SharedPreference(this)

        setupRecyclerView()
        setview()

        binding.back.setOnClickListener {
            finish()
        }
    }



    fun setview(){
        val adapter = ArrayAdapter.createFromResource(this,  R.array.customershortcutlist, R.layout.mobilenamelayout)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.reporttype.adapter = adapter

        binding.reporttype.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                hitApiForGetReports(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // nothing
            }
        }


    }


    private fun setupRecyclerView() {
        customerAdapter = CustomerShortcutLoanAdapter(customerList, this) { item ->
            // Handle item click if needed
        }
        binding.showCustomerreports.layoutManager = LinearLayoutManager(this)
        binding.showCustomerreports.adapter = customerAdapter
    }


    fun hitApiForGetReports(reporttype: String) {
        val retailerCode = preference.getStringValue(ConstantClass.RetailerCode, "")
        
        val reportreq = RetailerPerCustomerListShortCutForLoanReq(
            retailerCode = retailerCode,
            searchText = "",
            recordStatus = reporttype
        )

        Log.d("RetailerCustomerListReq", Gson().toJson(reportreq))

        viewModel.getCustomerListForShortCutLoanCreateProcess(reportreq).observe(this) { resources ->
            when (resources.apiStatus) {
                ApiStatus.SUCCESS -> {
                    ConstantClass.dialog.dismiss()
                    resources.data?.let { users ->
                        users.body()?.let { response ->
                            Log.d("RetailerCustomerListResponse", Gson().toJson(response))
                            customerList = (response.data ?: mutableListOf()) as List<CustomerListDataItem>

                            if (customerList.isNotEmpty()) {
                                binding.showCustomerreports.visibility = View.VISIBLE
                                binding.notfoundimage.visibility = View.GONE
                                customerAdapter.updateData(customerList)
                            } else {
                                binding.showCustomerreports.visibility = View.GONE
                                binding.notfoundimage.visibility = View.VISIBLE
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

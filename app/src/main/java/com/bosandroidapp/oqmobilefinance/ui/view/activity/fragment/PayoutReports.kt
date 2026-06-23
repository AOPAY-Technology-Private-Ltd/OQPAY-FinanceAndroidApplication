package com.bosandroidapp.oqmobilefinance.ui.view.activity.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import com.bos.payment.appName.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.databinding.FragmentPayoutReportsBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.data.model.DataItem
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletReportReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.view.adapter.RetailerWalletAdapter
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson
import java.util.Locale


class PayoutReports : Fragment() {
    lateinit var binding: FragmentPayoutReportsBinding
    lateinit var preference : SharedPreference
    lateinit var viewModel: AuthenticationViewModel
    var reportDataList: List<DataItem?>? = arrayListOf()


    companion object{
        var reportType : String = ""
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentPayoutReportsBinding.inflate(layoutInflater, container, false)

        preference = SharedPreference(requireContext())
        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]
        hitApiForReports(reportType)
        return binding.root
    }


    fun setview( ){
        // for report.........................................................................................
        val adapter = ArrayAdapter.createFromResource(requireContext(),  R.array.reporttype, R.layout.mobilenamelayout)
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
                setDataOnView(selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // nothing
            }
        }

        setDataOnView(binding.reporttype.selectedItem.toString().trim())

    }

    fun setDataOnView(status: String){

        if(!reportDataList.isNullOrEmpty()){

            val filteredList = if (status.equals("All", ignoreCase = true)) {
                reportDataList ?: emptyList()
            }
            else {
                reportDataList?.filter {
                    it?.transactionStatus.equals(status, ignoreCase = true)
                } ?: emptyList()
            }

            if(!filteredList.isNullOrEmpty()){
                binding.notfoundimage.visibility= View.GONE
                binding.showreports.visibility = View.VISIBLE
                var adapter = RetailerWalletAdapter(requireContext(), filteredList)
                binding.showreports.adapter = adapter
                adapter.notifyDataSetChanged()
            }
            else{
                binding.notfoundimage.visibility= View.VISIBLE
                binding.showreports.visibility = View.GONE
            }

        }
        else{
            binding.notfoundimage.visibility= View.VISIBLE
            binding.showreports.visibility = View.GONE
        }

    }

    fun hitApiForReports(reportType:String){

        var request = RetailerWalletReportReq(
            retailerID = preference.getStringValue(ConstantClass.RetailerCode,""),
            reportType = reportType,
            fromDate = null,
            toDate = null
        )

        Log.d("payoutreportreq", Gson().toJson(request))

        viewModel.getRetailerWalletReport(request).observe(requireActivity()) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data.let { users ->
                            users!!.body().let {
                                    response ->
                                ConstantClass.dialog.dismiss()
                                Log.d("payoutreportres",Gson().toJson(response))
                                if(response!!.status.equals("True")){
                                    if(!response.data.isNullOrEmpty()){
                                        reportDataList = response.data
                                    }
                                    setview()
                                }
                                else{
                                    binding.notfoundimage.visibility= View.VISIBLE
                                    binding.showreports.visibility = View.GONE
                                    setview()
                                }

                            }

                        }

                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(requireContext())
                    }

                }
            }
        }

    }


}
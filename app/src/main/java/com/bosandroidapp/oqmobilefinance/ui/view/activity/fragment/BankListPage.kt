package com.bosandroidapp.oqmobilefinance.ui.view.activity.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.databinding.FragmentBankListBinding
import com.bosandroidapp.oqmobilefinance.databinding.FragmentPayoutReportsBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.data.model.AddBankAccountReq
import com.bosandroidapp.oqmobilefinance.data.model.BankDataItem
import com.bosandroidapp.oqmobilefinance.data.model.RetailerWalletReportReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.ui.view.adapter.BankDetailsListAdapter
import com.bosandroidapp.oqmobilefinance.ui.view.adapter.RetailerWalletAdapter
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.google.gson.Gson
import java.util.Collections
import java.util.Locale


class BankListPage : Fragment() {
    lateinit var binding: FragmentBankListBinding
    lateinit var preference : SharedPreference
    lateinit var viewModel: AuthenticationViewModel


    companion object{
        var bankDataList: List<BankDataItem?>? = arrayListOf()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentBankListBinding.inflate(layoutInflater, container, false)

        preference = SharedPreference(requireContext())
        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]

        hitApiForReports()
        return binding.root
    }


    override fun onResume() {
        super.onResume()
        if(!bankDataList.isNullOrEmpty()){
            var adapter = BankDetailsListAdapter(requireContext(), bankDataList)
            binding.banklistview.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }


    fun hitApiForReports(){

        var req = com.bosandroidapp.oqmobilefinance.data.model.AddBankAccountReq(
            action = "GET",
            retailerID = preference.getStringValue(ConstantClass.RetailerCode, ""),
            accountNumber = "",
            accountName = "",
            bankName = "",
            ifscCode = "",
            branchName = "",
            branchAddress = "",
            mobilenumber = "",
            emailID = ""
        )

        Log.d("GetBankListReq", Gson().toJson(req))

        viewModel.getAddBankAccountReq(req).observe(requireActivity()) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data.let { users ->
                            users!!.body().let {
                                    response ->
                                ConstantClass.dialog.dismiss()
                                if(response!!.statuss.equals("True")){
                                    Log.d("BankListRes",Gson().toJson(response))
                                    bankDataList = response?.data!!
                                    if(!bankDataList.isNullOrEmpty()){
                                        var adapter = BankDetailsListAdapter(requireContext(), bankDataList)
                                        binding.banklistview.adapter = adapter
                                        adapter.notifyDataSetChanged()
                                        binding.notfoundimage.visibility= View.GONE
                                        binding.banklistview.visibility = View.VISIBLE
                                    }else{
                                        binding.notfoundimage.visibility= View.VISIBLE
                                        binding.banklistview.visibility = View.GONE
                                    }

                                }
                                else{
                                    binding.notfoundimage.visibility= View.VISIBLE
                                    binding.banklistview.visibility = View.GONE
                                }

                            }

                        }

                    }

                    ApiStatus.ERROR -> {
                        ConstantClass.dialog.dismiss()
                        Toast.makeText(requireContext(), resources.message ?: "Error occurred", Toast.LENGTH_SHORT).show()
                    }

                    ApiStatus.LOADING -> {
                        ConstantClass.OpenPopUpForVeryfyOTP(requireContext())
                    }

                }
            }
        }

    }




}
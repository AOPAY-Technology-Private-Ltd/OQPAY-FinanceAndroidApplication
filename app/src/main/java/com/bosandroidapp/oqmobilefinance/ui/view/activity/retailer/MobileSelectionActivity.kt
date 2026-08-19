package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bos.payment.appName.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.internetchecker.BaseActivity
import com.bosandroidapp.oqmobilefinance.R
import com.bosandroidapp.oqmobilefinance.databinding.ActivityMobileselectionBinding
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.LoginMobileorMailid
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.Loginpassword
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass.loginType
import com.bosandroidapp.oqmobilefinance.data.model.SessionOutReq
import com.bosandroidapp.oqmobilefinance.data.model.ValidateSessionRequest
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.DataItem
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LoginReq
import com.bosandroidapp.oqmobilefinance.data.model.loginsignup.LogoutReq
import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.localdb.SharedPreference
import com.bosandroidapp.oqmobilefinance.ui.slideshow.activity.DashBoard
import com.bosandroidapp.oqmobilefinance.ui.slideshow.adapter.MobileListAdapter
import com.bosandroidapp.oqmobilefinance.ui.view.activity.ChooseYourRolePage
import com.bosandroidapp.oqmobilefinance.ui.view.model.MobileListModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import com.bosandroidapp.oqmobilefinance.utils.ApiStatus
import com.bosandroidapp.oqmobilefinance.utils.GridSpacingItemDecoration
import com.google.gson.Gson

class MobileSelectionActivity : BaseActivity() {

    lateinit var binding : ActivityMobileselectionBinding
    lateinit var adapter : MobileListAdapter

    lateinit var viewModel: AuthenticationViewModel
    lateinit var preference : SharedPreference
    lateinit var dialog: Dialog

    companion object{
        var MobileList : MutableList<com.bosandroidapp.oqmobilefinance.ui.view.model.MobileListModel> = mutableListOf()
        var MobileDataList : MutableList<DataItem> = mutableListOf()
        var FilterDataList : MutableList<DataItem> = mutableListOf()

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMobileselectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]
        preference = SharedPreference(this)

        val spacingInPixels = 5   // or just use `5` in dp
        binding.showingMobileList.layoutManager = GridLayoutManager(this, 2)
        binding.showingMobileList.addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels, true))
        //addMobileList()
        setClickListner()
        hitApiForGetMobileDataList()
    }



    override fun onResume() {
        super.onResume()

        hitApiForLogin()
    }


    fun setClickListner(){

        binding.home.setOnClickListener {
            val intent = Intent(this, DashBoard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            onBackPressed()
        }

        binding.back.setOnClickListener {
            OpenPopUpForVAlert()
        }

        binding.searchIcon.setOnClickListener {
            binding.searcMobile.requestFocus()
            binding.searcMobile.text.clear()
            // Show the keyboard
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.searcMobile, InputMethodManager.SHOW_IMPLICIT)

        }


        binding.searcMobile.addTextChangedListener(
            object :TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val search = s.toString().lowercase().trim()
                    val result = MobileDataList.filter {
                        it.brandName.lowercase().contains(search) || it.modelName.lowercase().contains(search)
                    }
                    FilterDataList.clear()
                    FilterDataList.addAll(result)
                    setViewData(FilterDataList)
                    adapter.notifyDataSetChanged()
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            }
        )

        binding.synchicon.setOnClickListener {
            hitApiForGetMobileDataList()
        }

    }


    fun setViewData(MobileDataList : MutableList<DataItem>){
        adapter = MobileListAdapter(MobileDataList,this@MobileSelectionActivity)
        binding.showingMobileList.adapter = adapter
        adapter.notifyDataSetChanged()

    }


    fun hitApiForGetMobileDataList(){
        viewModel.getMobileList().observe(this){ resources->resources.let {
            when(it.apiStatus){
                ApiStatus.SUCCESS -> {
                    it.data?.let { users ->
                        users.body()?.let { response ->
                            ConstantClass.dialog.dismiss()
                            val response = users?.body()
                            if (response != null) {
                                Log.d("MobileRes", response.message)
                                if(response.status.equals("True")){
                                    MobileDataList = response.data!!
                                    Log.d("List",Gson().toJson(MobileDataList))
                                    if(MobileDataList.size>0){
                                        ConstantClass.dialog.dismiss()
                                        setViewData(MobileDataList)
                                    }
                                    else {
                                        ConstantClass.dialog.dismiss()
                                        Toast.makeText(this@MobileSelectionActivity,response.message, Toast.LENGTH_SHORT).show()
                                    }

                                }
                            }
                            else{
                                ConstantClass.dialog.dismiss()
                                hitApiForGetMobileDataList()
                            }

                        }
                    }
                }

                ApiStatus.ERROR -> {
                    ConstantClass.dialog.dismiss()
                    Toast.makeText(this@MobileSelectionActivity, resources.message ?: "Error getting mobile data", Toast.LENGTH_SHORT).show()
                }

                ApiStatus.LOADING -> {
                    ConstantClass.OpenPopUpForVeryfyOTP(this)
                }

            }

          }

        }
    }



    @SuppressLint("SetTextI18n")
    fun OpenPopUpForVAlert() {
        dialog = Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.signoutalert)


        dialog.window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }


        dialog.setCanceledOnTouchOutside(false)

        val cancel = dialog.findViewById<Button>(R.id.btnCancel)
        val done = dialog.findViewById<Button>(R.id.btnLogout)
        val txt = dialog.findViewById<TextView>(R.id.dialog_message)
        val image = dialog.findViewById<ImageView>(R.id.imageview)

        image.visibility = View.VISIBLE

        done.text = "OK"

        txt.text = "Are you sure you want to go back?"

        done.setOnClickListener {
            finish()


        }

        cancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


    fun hitApiForLogin() {

        var sessionOutReq = SessionOutReq(
            retailerCode = preference.getStringValue(ConstantClass.RetailerCode, ""),
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
                                ConstantClass.checkActiveStatusAndLogout(this@MobileSelectionActivity, response.status, preference)
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

        var request = ValidateSessionRequest(
            preference.getStringValue(ConstantClass.RetailerCode, ""),
            preference.getStringValue(ConstantClass.DEVICEID, ""),
            preference.getStringValue(ConstantClass.FCMTOKEN, "")
        )

        Log.d("validaterequest", Gson().toJson(request))
        viewModel.getSessionExpiredReq(request).observe(this){resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("validateresp", Gson().toJson(response))
                                if(response.status==0){
                                    hitApiForRetailerLogout()
                                }
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


    fun hitApiForRetailerLogout() {
        var loginRequest = LogoutReq(
            retailerCode = preference.getStringValue(ConstantClass.RetailerCode, ""),
        )

        Log.d("LogoutReq", Gson().toJson(loginRequest))

        viewModel.getLogout(loginRequest).observe(this) { resources ->
            resources.let {
                when (it.apiStatus) {
                    ApiStatus.SUCCESS -> {
                        it.data?.let { users ->
                            users.body()?.let { response ->
                                Log.d("LogoutResponse", Gson().toJson(response))
                                preference.setBooleanValue(ConstantClass.LoggedIn, false)
                                preference.setStringValue(ConstantClass.LoginType, "")
                                ConstantClass.ClickOnCardDashboard = ""
                                val intent = Intent(this@MobileSelectionActivity, ChooseYourRolePage::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
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
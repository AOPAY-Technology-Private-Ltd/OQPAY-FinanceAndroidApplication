package com.bosandroidapp.oqmobilefinance.ui.view.activity.retailer

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bos.payment.appName.network.ApiInterface
import com.bos.payment.appName.network.RetrofitClient
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.databinding.ActivityCongratulationPageBinding

import com.bosandroidapp.oqmobilefinance.data.repository.AuthRepository
import com.bosandroidapp.oqmobilefinance.data.viewModelFactory.CommonViewModelFactory
import com.bosandroidapp.oqmobilefinance.ui.slideshow.activity.DashBoard
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.AuthenticationViewModel
import kotlinx.coroutines.launch

class CongratulationPage : AppCompatActivity() {
    lateinit var binding: ActivityCongratulationPageBinding
    lateinit var viewModel: AuthenticationViewModel
    lateinit var api: ApiInterface

    companion object{
        var loaneCode:String = ""
        var FirstName:String = ""
        var MiddleName:String = ""
        var LastName:String = ""
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityCongratulationPageBinding.inflate(layoutInflater)
         setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBarsInsets.left, 0, systemBarsInsets.right, systemBarsInsets.bottom)
            WindowInsetsCompat.CONSUMED
        }

        setView()
        setOnClickListner()

    }


    fun setView(){
        binding.LoanCode.text = loaneCode
        binding.customerName.text =  FirstName .plus(" ").plus(MiddleName).plus(" ").plus(LastName) . plus("")
        binding.downloadtxt.text=ConstantClass.Exit

        api = RetrofitClient.apiInterface
        viewModel = ViewModelProvider(this, CommonViewModelFactory(AuthRepository(RetrofitClient.apiInterface)))[AuthenticationViewModel::class.java]

    }


    fun setOnClickListner(){

        binding.nextlayout.setOnClickListener {
            loaneCode=""
            val intent = Intent(this@CongratulationPage, DashBoard::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

    }


    override fun onBackPressed() {
        val intent = Intent(this@CongratulationPage, DashBoard::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}
package com.bosandroidapp.oqmobilefinance.internetchecker

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bosandroidapp.oqmobilefinance.constant.ConstantClass
import com.bosandroidapp.oqmobilefinance.utils.ApplicationClass
import kotlinx.coroutines.launch

open class BaseActivity : AppCompatActivity() {
    private var dialog: AlertDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                ApplicationClass.isNetworkAvailable.collect { connected ->
                    if (connected) {
                        dialog?.dismiss()
                    }
                    else {
                        ConstantClass.showNoInternetDialog(this@BaseActivity)
                    }
                }
            }
        }

    }


}
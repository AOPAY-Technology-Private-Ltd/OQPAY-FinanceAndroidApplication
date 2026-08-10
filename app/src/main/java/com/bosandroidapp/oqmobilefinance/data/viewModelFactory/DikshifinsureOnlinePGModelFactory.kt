package com.bosandroidapp.oqmobilefinance.data.viewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bosandroidapp.oqmobilefinance.data.repository.DikshifinsureRepository
import com.bosandroidapp.oqmobilefinance.data.repository.PanRepository
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.DikshifinsureViewModel
import com.bosandroidapp.oqmobilefinance.ui.viewmodel.PanViewModel

class DikshifinsureOnlinePGModelFactory (private val repository: DikshifinsureRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when{
            modelClass.isAssignableFrom(DikshifinsureViewModel::class.java) -> DikshifinsureViewModel(repository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }

    }

}
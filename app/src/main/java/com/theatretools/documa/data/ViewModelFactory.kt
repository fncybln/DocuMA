package com.theatretools.documa.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory (private val dataSource: DataRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom((AppViewModel::class.java))){
            return AppViewModel(dataSource) as T
        }
        throw IllegalArgumentException ("Unknown ViewModel class")
    }

}
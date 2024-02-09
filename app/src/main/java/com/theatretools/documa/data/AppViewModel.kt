package com.theatretools.documa.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.PresetItem
import io.reactivex.Flowable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception

class AppViewModel(private val repository: DataRepository): ViewModel() {
    fun insertPreset(preset: PresetItem) = viewModelScope.launch {
        repository.insertPreset(preset)
    }

    fun getPresetByID(id: Int): PresetItem {
        return repository.getPresetByID(id)
    }


    fun updatePreset(preset: PresetItem): Int =  repository.updatePreset(preset)

    val AllPresetItems: LiveData<List<PresetItem>> = repository.loadAllPresetItems().asLiveData()

    val AllDeviceItems: LiveData<List<Device>> = repository.loadAllDeviceItems().asLiveData()

}

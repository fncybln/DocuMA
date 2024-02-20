package com.theatretools.documa.data

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.DeviceInPreset
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
import java.lang.reflect.Array.setInt

class AppViewModel(private val repository: DataRepository): ViewModel() {
    fun insertPreset(preset: PresetItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertPreset(preset)
    }
    fun insertPresetAndReferences(preset: PresetItem, devices: List<Device?>): Job {
        var presetID : Int?
        var devID : Int?
        var listOfDevID = mutableListOf<Int?>()
        var result = viewModelScope.launch(Dispatchers.IO) {

            // Add the Preset Item into the database

            presetID = repository.insertPreset(preset).toInt()

            // Check if the devices are in the database. if not, add them and get their ids.
            devices.forEach {
                devID = it?.let { it1 ->
                    repository.insertUniqueDevice(it1)
                        .toInt()
                }
                //if device is already in the database, get the items ID
                if (devID == 0) {
                    devID = repository.getDevice(it?.chan ?: 0, it?.fix?: 0)
                }
                listOfDevID.add(devID)
            }

            //Update the reference database
            listOfDevID.forEach {
                repository.insertDeviceInPreset(DeviceInPreset(null, presetID, it, null))
            }
        }
        return result
    }

    fun readout (it : List<Uri>, contentResolver: ContentResolver, appViewModel: AppViewModel): Job?{

        val supervisorJob = viewModelScope.launch(Dispatchers.IO) {
            com.theatretools.documa.xmlTools.readout(it, contentResolver)?.forEach{ item ->
                item.toDatabase(appViewModel)
            }
        }
        return supervisorJob
    }
    fun insertDeviceInPreset(deviceInPreset: DeviceInPreset) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertDeviceInPreset(deviceInPreset)
    }

    fun getPresetByID(id: Int): PresetItem {
        return repository.getPresetByID(id)
    }

    fun clearoutDatabase() : Job  {
        return viewModelScope.launch(Dispatchers.IO){
            repository.clearoutDatabase()
        }
    }


    fun updatePreset(preset: PresetItem): Int =  repository.updatePreset(preset)

    val AllPresetItems: LiveData<List<PresetItem>> = repository.loadAllPresetItems().asLiveData()

    val AllDeviceItems: LiveData<List<Device>> = repository.loadAllDeviceItems().asLiveData()

}

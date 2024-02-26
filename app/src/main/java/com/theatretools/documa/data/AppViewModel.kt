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
import com.theatretools.documa.telnet.TelnetClient
import com.theatretools.documa.telnet.TelnetConnection
import com.theatretools.documa.xmlTools.Readout
import com.theatretools.documa.xmlTools.ReadoutMaExport
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException

class AppViewModel(private val repository: DataRepository): ViewModel() {

// READOUT HANDLING
    fun urisToReadout (uriList: List<Uri>, contentResolver: ContentResolver, ) : Job {

        return viewModelScope.launch(Dispatchers.IO) {
            val readout = mutableListOf<ReadoutMaExport>()
            Log.v("urisToReadout()", ("Size of uriList:" + uriList.size) )
            for (i in uriList.indices) {
                val item = ReadoutMaExport()
                Log.v ("urisToReadout()", " == Now parsing: == File ${uriList[i]}")
                contentResolver.openInputStream(uriList[i])?.let { item.parse(it) }
                readout.add(item)
            }

            readout.forEach {
                it.deviceList?.distinctBy { it!!.chan }
                insertPresetAndReferences(it)
            }
        }
    }

    private suspend fun insertPresetAndReferences(readout: Readout) {
        val devices = readout.deviceList
        val preset = readout.getPreset()
        val presetID : Int?
        var devID : Int?
                // Add the Preset Item into the database

        presetID = repository.insertPreset(preset).toInt()

        // Check if the devices are in the database. if not, add them and get their ids.
        // then update the reference table
        Log.v("insertPresetsAndReferences", "devices: $devices")
        devices?.forEach {
            devID = it?.let { it1 ->
                repository.insertUniqueDevice(it1)
            }
            //Log.v(" insertPresetAndRefs()", "found DevID: $devID\nfound PresetID: $presetID")

            if (devID != null ) repository.insertDeviceInPreset(DeviceInPreset(
                id = null,
                presetID,
                devID,
                null))
        }
        Result.success(true) // TODO: return indexes of Devices and Presets that got imported
    }

// TELNET TOOLS

    var outputText: String = ""
    var outputTextLiveData: MutableLiveData<String> = MutableLiveData("")
    val telnetConnectionStatus: MutableLiveData<Int> by lazy { MutableLiveData<Int>() }

    val telnetClient by lazy {
        com.theatretools.documa.telnet.TelnetClient("null", 30000)
    }
    fun updateTelnetIP(ip: String){
        telnetClient.close()
        repository.updateTelnetIP(ip)

    }
    fun getTelnetIP(): String? {
        return repository.getTelnetIP()
    }

    fun telnetConnect(onResult: (  ) -> Unit, onError: (Throwable) -> Unit){
        viewModelScope.launch  (Dispatchers.IO) {
            repository.getTelnetIP()?.let {
                telnetClient.connect(it,
                    30000,
                    onResult = {
                        outputTextLiveData.postValue(it)
                        Log.i("AppViewModel", "connect Message: \n$it")
                        onResult()
                    },
                    onError = {onError(it)},
                    onStatusChange = {} )
            }
        }
    }



    fun telnetCheckConnectivity():Job{
        return viewModelScope.launch(Dispatchers.IO){
            var status = TelnetClient.STATUS_DISCONNECTED
            while (true) {
                if (status != telnetClient.isConnected()){
                    Log.v("appViewModel.telnetCheckConnectivity", "connection status has changed!")
                    status = telnetClient.status
                    telnetConnectionStatus.postValue(status)
                }
            }
        }
    }


    fun telnetGetResponse(cmd: String, onError: (Throwable) -> Unit, onSuccess: (String) -> Unit, onNotConnected:() -> Unit) {
        viewModelScope.launch  (Dispatchers.IO)  {
            try {
                if (telnetClient.status == TelnetClient.STATUS_DISCONNECTED) onNotConnected()
                telnetClient.getResponse(cmd,) {
                    onSuccess(it)
                    outputText = it
                    outputTextLiveData.postValue(it)
                }
            } catch (e: IOException) {
                onError(e)
                outputText = e.toString()
                outputTextLiveData.postValue (e.toString())
                Log.e("AppViewModel.telnetGetResponse" , "Unexpected IOException: \n ${e.printStackTrace()} \n$e")
            }
        }
    }




// DEPRECATED?
    fun insertPresetAndReferences(preset: PresetItem, devices: List<Device?>, parentJob: Job): Job {
        var presetID : Int?
        var devID : Int?
        var listOfDevID = mutableListOf<Int?>()
        var result = viewModelScope.launch(Dispatchers.IO + parentJob) {

            // Add the Preset Item into the database

            presetID = repository.insertPreset(preset).toInt()

            // Check if the devices are in the database. if not, add them and get their ids.
            devices.forEach {
                devID = it?.let { it1 ->
                    repository.insertUniqueDevice(it1)
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
            Result.success(true) // TODO: return indexes of Devices and Presets that got imported
        }
        return result
    }

// VAR. DATABASE TOOLS
    fun insertPreset(preset: PresetItem) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertPreset(preset)
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

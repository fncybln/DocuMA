package com.theatretools.documa.data

import android.util.Log
import androidx.annotation.WorkerThread
import com.theatretools.documa.dataobjects.Device
import com.theatretools.documa.dataobjects.DeviceInPreset
import com.theatretools.documa.dataobjects.PresetItem
import kotlinx.coroutines.flow.Flow

class DataRepository(private val appDAO: AppDAO) {
    fun loadAllPresetItems(): Flow<List<PresetItem>> {
        return appDAO.loadAllPresetItems()
    }

    fun loadAllDeviceItems(): Flow<List<Device>> {
        return appDAO.loadAllDevices()
    }

    fun getAllDevInPresetOrdered(): List<DeviceInPreset> {
        return appDAO.AllDevInPresetsOrdered()
    }

        @WorkerThread
    fun getPresetByID(id: Int): PresetItem{
        return appDAO.getPresetItemByID(id)
    }

    @WorkerThread
    fun getDevice ( chan : Int? , fix : Int? ): Int?{
        return appDAO.getDeviceWithSameChanAndFix(fix, chan)?.id
    }
    @WorkerThread
    fun getDevice ( id: Int){
        appDAO.getDeviceByID(id)
    }

    fun getDeviceInPreset (id: Int): DeviceInPreset {
        return appDAO.getDeviceInPresetByID(id)[0]
    }

    fun getDeviceFromDevInPreset(id: Int): Device{
        return appDAO.getDeviceFromDevInPreset(id)[0]
    }
    fun getPresetFromDevInPreset(id: Int): PresetItem{
        return appDAO.getPresetFromDevInPreset(id)[0]
    }

    @WorkerThread
    fun updatePreset(preset: PresetItem):Int{
        return appDAO.updatePreset(preset)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertPreset( preset: PresetItem) : Long{
        return appDAO.insertPreset(preset)[0]
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertUniqueDevice( device: Device? ): Int? { // TODO: DOES NOT WORK!!!!
        val ret = appDAO.getDeviceWithSameChanAndFix(device?.fix, device?.chan)
        //Log.v("rep.insertUniqueDevice", "getDevWithSame: $ret")
//        Log.v (this::class.toString(), "insertUniqueDevice: getDev($device) returns: $ret")
        return if ( ret  == null && device != null){
//            Log.v (this::class.toString(), "insertUniqueDevice: getDev ($device) is null: $ret")
            appDAO.insertDevice(device)[0].toInt()
        } else ret?.id
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertDeviceInPreset( deviceInPreset: DeviceInPreset) {
        appDAO.insertDeviceInPreset(deviceInPreset)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun clearoutDatabase() {
        appDAO.clearoutDevice()
        appDAO.clearoutPresets()
        appDAO.clearoutDevInPreset()
    }

    fun getTelnetIP(): String? {
        return appDAO.getPreference("telnet_IP")?.get(0)?.content
    }

    fun updateTelnetIP(ip: String) {
        if ( appDAO.getPreference("telnet_IP")?.size != 0) {
            Log.v("repository.updateTelnetIP", "current Preferences with 'telnet_IP' Tag: ${appDAO.getPreference("telnet_IP")}")
            Log.v("repository.updateTelnetIP", "updating Preference")
            appDAO.setPreference("telnet_IP", ip)
        } else {
            Log.v("repository.updateTelnetIP", "current Preferences with 'telnet_IP' Tag: ${appDAO.getPreference("telnet_IP")}")
            Log.v("repository.updateTelnetIP", "inserting new Preference")
            appDAO.insertPreference("telnet_IP", ip)
        }
    }
    fun updatePreference(tag: String, content: String) {
        if(appDAO.getPreference((tag))?.size != 0) {
            appDAO.setPreference(tag, content)
        } else {
            appDAO.insertPreference(tag, content)
        }
    }

    fun getPreference(tag: String)  : String? {
        try {
            return appDAO.getPreference(tag)?.get(0)?.content
        } catch (e: IndexOutOfBoundsException) {
            return null
        }
    }
}